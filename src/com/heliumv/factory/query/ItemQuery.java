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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.api.item.ItemEntryTransformer;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.ISystemCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class ItemQuery extends BaseQuery<ItemEntry> {
	@Autowired
	private IParameterCall parameterCall ;
	
	@Autowired
	private IMandantCall mandantCall ;
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Autowired
	private ISystemCall systemCall ;
	
	public ItemQuery() {
		super(QueryParameters.UC_ID_ARTIKELLISTE) ;
		setTransformer(new ItemEntryTransformer()) ;
	}
	
	public ItemQuery(IParameterCall parameterCall) throws NamingException {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_ARTIKELLISTE) ;
		setTransformer(new ItemEntryTransformer()) ;
		this.parameterCall = parameterCall ;
	}

//	public List<ItemEntry> getResultList(QueryResult result) {
//		try {
//			getTableInfo() ; 
//		} catch(NamingException e) {
//
//		} catch(RemoteException e) {
//		}
//
//		return super.getResultList(result) ;
//	}
	
	@Override
	protected List<FilterKriterium> getRequiredFilters() {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		filters.add(getFilterKeineHandeingabe()) ;
		filters.add(getFilterMandant()) ;		
		return filters ;
	}
	
	private FilterKriterium getFilterKeineHandeingabe() {
		return new FilterKriterium(
				"artikelliste."
				+ ArtikelFac.FLR_ARTIKEL_ARTIKELART_C_NR, true, "('"
				+ ArtikelFac.ARTIKELART_HANDARTIKEL + "')",
				FilterKriterium.OPERATOR_NOT_IN, false) ;
	}
	
	
	private FilterKriterium getFilterMandant() {
		String mandant = globalInfo.getMandant() ;
		try {
			if (mandantCall.hasFunctionZentralerArtikelstamm()) {
				mandant = systemCall.getHauptmandant() ;
			}

			return new FilterKriterium("artikelliste.mandant_c_nr",
						true, StringHelper.asSqlString(mandant),
						FilterKriterium.OPERATOR_EQUAL, false) ;
		} catch(NamingException e) {
			// TODO: getDefaultFilters auf throws NamingException umstellen
			return null ;
		}
	}
}
