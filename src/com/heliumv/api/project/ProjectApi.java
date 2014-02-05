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
package com.heliumv.api.project;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.query.ProjectQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

@Service("hvProject")
@Path("/api/v1/project/")
public class ProjectApi extends BaseApi implements IProjectApi {

//	@Autowired
//	private IParameterCall parameterCall ;
	
	@Autowired
	private ProjectQuery projectQuery ;
	
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<ProjectEntry> getProjects(
			@QueryParam("userid") String userId,
			@QueryParam("limit") Integer limit, 
			@QueryParam("startIndex") Integer startIndex,
			@QueryParam("filter_cnr") String filterCnr, 
			@QueryParam("filter_company") String filterCompany,
			@QueryParam("filter_withCancelled") Boolean filterWithHidden) {
		List<ProjectEntry> projects = new ArrayList<ProjectEntry>() ;

		try {
			if(null == connectClient(userId)) {
				return projects ;
			}
			
			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(buildFilterCnr(filterCnr)) ;
			collector.add(buildFilterCompanyName(filterCompany)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;

//			ProjectQuery query = new ProjectQuery(parameterCall) ;			
//			QueryParameters params = query.getDefaultQueryParameters(filterCrits) ;
			QueryParameters params = projectQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
			
			QueryResult result = projectQuery.setQuery(params) ;
			projects = projectQuery.getResultList(result) ;
//		} catch(NamingException e) {
//			e.printStackTrace() ;
//		} catch(RemoteException e) {
//			e.printStackTrace() ;
		} finally {
			
		}
	
		return projects ;
	}
	
	private FilterKriterium buildFilterCnr(String cnr) {
		if(cnr == null || cnr.trim().length() == 0) return null ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt("c_nr", StringHelper.removeSqlDelimiters(cnr), 
				FilterKriterium.OPERATOR_LIKE, "", 
				FilterKriteriumDirekt.PROZENT_LEADING, 
				true, false, Facade.MAX_UNBESCHRAENKT) ;
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	
	private FilterKriterium buildFilterCompanyName(String companyName) {
		if(companyName == null || companyName.trim().length() == 0) return null ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"flrpartner.c_name1nachnamefirmazeile1",StringHelper.removeSqlDelimiters(companyName),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING,
				true, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
}
