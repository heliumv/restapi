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

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.staff.StaffEntry;
import com.heliumv.api.staff.StaffEntryTransformer;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.IPersonalCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.personal.service.PersonalDto;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class StaffQuery extends BaseQuery<StaffEntry> {

	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Autowired
	private IJudgeCall judgeCall ;
	@Autowired
	private IPersonalCall personalCall ;
	@Autowired
	private StaffEntryTransformer staffEntryTransformer ;
	
	public StaffQuery() {
		super(QueryParameters.UC_ID_PERSONAL) ;
		setTransformer(staffEntryTransformer);
	}
	
	@Override
	protected List<FilterKriterium> getRequiredFilters() throws NamingException {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		filters.add(getMandantFilter()) ;
		filters.addAll(getPersonalFilter()) ;
		return filters ;
	}


	private FilterKriterium getMandantFilter() {
		return new FilterKriterium("mandant_c_nr", true,
				StringHelper.asSqlString(globalInfo.getTheClientDto().getMandant()),
				FilterKriterium.OPERATOR_EQUAL, false) ;
	}
	
	private FilterKriterium getOnlyMeFilter() {
		FilterKriterium fk = new FilterKriterium("i_id", true,
					globalInfo.getTheClientDto().getIDPersonal() + "", FilterKriterium.OPERATOR_EQUAL, false);
		return fk ;		
	}
	
	private FilterKriterium getAbteilungFilter(Integer abteilung) {
		FilterKriterium fk = new FilterKriterium(
				"flrkostenstelleabteilung.i_id", true,
				abteilung + "", FilterKriterium.OPERATOR_EQUAL, false) ;
		return fk ;
	}
	
	private List<FilterKriterium> getPersonalFilter() throws NamingException {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;

 		if(judgeCall.hasPersSichtbarkeitAlle()) return filters ;
 		
 		Integer abteilung = null ; 		
 		if(judgeCall.hasPersSichtbarkeitAbteilung()) { 			
 			PersonalDto personalDto = personalCall.byPrimaryKeySmall(globalInfo.getTheClientDto().getIDPersonal()) ;
 			abteilung = personalDto.getKostenstelleIIdAbteilung() ;
 		}

 		filters.add(abteilung != null ? getAbteilungFilter(abteilung) : getOnlyMeFilter()) ;

 		return filters ;
	}
}
