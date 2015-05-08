/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
package com.heliumv.factory.query;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.order.OrderEntry;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IParameterCall;
import com.heliumv.tools.FilterHelper;
import com.heliumv.tools.StringHelper;
import com.lp.server.auftrag.service.AuftragFac;
import com.lp.server.auftrag.service.AuftragServiceFac;
import com.lp.server.partner.service.KundeFac;
import com.lp.server.partner.service.PartnerFac;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class AuftragQuery extends BaseQuery<OrderEntry> {
	@Autowired
	private IParameterCall parameterCall ;
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public AuftragQuery() {
		super(QueryParameters.UC_ID_AUFTRAG) ;
	}
	
	public FilterKriterium getFilterCnr(String cnr) {
		if(cnr == null || cnr.trim().length() == 0) return null ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt("c_nr", StringHelper.removeSqlDelimiters(cnr), 
				FilterKriterium.OPERATOR_LIKE, "", 
				FilterKriteriumDirekt.PROZENT_LEADING, 
				true, false, Facade.MAX_UNBESCHRAENKT) ;
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	public FilterKriterium getFilterProject(String project) {
		if(null == project || project.trim().length() == 0) return null ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt("c_bez", StringHelper.removeSqlDelimiters(project), 
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING, 
				true, true, Facade.MAX_UNBESCHRAENKT) ;
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}

	private FilterKriterium getFilterCustomerAdresstyp(String customer, String adressTyp) throws NamingException, RemoteException {
		if(null == customer || customer.trim().length() == 0) return null ;

		int percentType = parameterCall
				.isPartnerSucheWildcardBeidseitig() ? FilterKriteriumDirekt.PROZENT_BOTH : FilterKriteriumDirekt.PROZENT_TRAILING ;

		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(adressTyp
				+ "." + KundeFac.FLR_PARTNER + "."
				+ PartnerFac.FLR_PARTNER_NAME1NACHNAMEFIRMAZEILE1, StringHelper.removeSqlDelimiters(customer),
				FilterKriterium.OPERATOR_LIKE, "",
				percentType, true, true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	public FilterKriterium getFilterCustomer(String customer) throws NamingException, RemoteException {
		return getFilterCustomerAdresstyp(customer, AuftragFac.FLR_AUFTRAG_FLRKUNDE) ;
//		
//		if(null == customer || customer.trim().length() == 0) return null ;
//
//		int percentType = parameterCall
//				.isPartnerSucheWildcardBeidseitig() ? FilterKriteriumDirekt.PROZENT_BOTH : FilterKriteriumDirekt.PROZENT_TRAILING ;
//
//		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(AuftragFac.FLR_AUFTRAG_FLRKUNDE
//				+ "." + KundeFac.FLR_PARTNER + "."
//				+ PartnerFac.FLR_PARTNER_NAME1NACHNAMEFIRMAZEILE1, StringHelper.removeSqlDelimiters(customer),
//				FilterKriterium.OPERATOR_LIKE, "",
//				percentType, true, true, Facade.MAX_UNBESCHRAENKT); 
//		fk.wrapWithProzent() ;
//		fk.wrapWithSingleQuotes() ;
//		return fk ;
	}
	
	public FilterKriterium getFilterDeliveryCustomer(String customer) throws NamingException, RemoteException {
		return getFilterCustomerAdresstyp(customer, AuftragFac.FLR_AUFTRAG_FLRKUNDELIEFERADRESSE) ;
	}
	
	public FilterKriterium getFilterWithHidden(Boolean withHidden) {
		return FilterHelper.createWithHidden(withHidden, AuftragFac.FLR_AUFTRAG_B_VERSTECKT) ;
	}
	
	
	protected List<FilterKriterium> getRequiredFilters() throws NamingException, RemoteException {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;

		filters.add(getMandantFilter()) ;
		
		if(parameterCall.isZeitdatenAufErledigteBuchbar()) {
			filters.add(getFilterErledigteBuchbar()) ;
		} else {
			filters.add(getFiltersErledigteNichtBuchbar()) ;
		}
		
		return filters ;		
	}
	
	private FilterKriterium getMandantFilter() {
		return new FilterKriterium(
				AuftragFac.FLR_AUFTRAG_MANDANT_C_NR, true, 
				StringHelper.asSqlString(globalInfo.getMandant()),
				FilterKriterium.OPERATOR_EQUAL, false);		
	}
	
	private FilterKriterium getFilterErledigteBuchbar() {
		return new FilterKriterium(
				AuftragFac.FLR_AUFTRAG_AUFTRAGSTATUS_C_NR, true, "(" +
						StringHelper.asSqlString(AuftragServiceFac.AUFTRAGSTATUS_STORNIERT) + ")",
				FilterKriterium.OPERATOR_NOT_IN, false);
	}

	private FilterKriterium getFiltersErledigteNichtBuchbar() {
		return new FilterKriterium(
				AuftragFac.FLR_AUFTRAG_AUFTRAGSTATUS_C_NR, true, "(" +
						StringHelper.asSqlString(AuftragServiceFac.AUFTRAGSTATUS_ERLEDIGT) + "," +
						StringHelper.asSqlString(AuftragServiceFac.AUFTRAGSTATUS_STORNIERT) + ")",
				FilterKriterium.OPERATOR_NOT_IN, false);
	}
}
