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
package com.heliumv.api.staff;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.query.StaffQuery;
import com.heliumv.tools.FilterHelper;
import com.heliumv.tools.FilterKriteriumCollector;
import com.lp.server.personal.service.PersonalFac;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

/**
 * Funktionalit&auml;t rund um das Personal</br>
 * 
 * @author Gerold
 */
@Service("hvStaff")
@Path("/api/v1/staff/")
public class StaffApi extends BaseApi implements IStaffApi {

	@Autowired
	private IMandantCall mandantCall ;
	@Autowired
	private StaffQuery staffQuery ;
	
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<StaffEntry> getStaff(
			@QueryParam("userid") String userId,
			@QueryParam("limit") Integer limit,
			@QueryParam("startIndex") Integer startIndex) {
		List<StaffEntry> entries = new ArrayList<StaffEntry>() ;

		if(connectClient(userId) == null) return entries ;
	
		FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
		collector.add(buildFilterWithHidden(false)) ;
		FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
		
		try {
			QueryParameters params = staffQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
		
			QueryResult result = staffQuery.setQuery(params) ;
			entries = staffQuery.getResultList(result) ;			
		} catch(RemoteException e) {
			respondUnavailable(e);
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} finally {
		}
		return entries ;
	}
	
	private FilterKriterium buildFilterWithHidden(Boolean withHidden) {
		return FilterHelper.createWithHidden(withHidden, PersonalFac.FLR_PERSONAL_B_VERSTECKT) ;
	}
		
}
