package com.heliumv.api.project;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IClientCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.impl.ProjectQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.lp.server.system.service.TheClientDto;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

@Service("hvProject")
@Path("/api/v1/project/")
public class ProjectApi extends BaseApi {

	@Autowired
	private IClientCall clientCall ;
	@Autowired
	private IParameterCall parameterCall ;

	@GET
	@Path("/{userid}")
	@Produces({"application/json", "application/xml"})	
	public List<ProjectEntry> getProjects(
			@PathParam("userid") String userId,
			@QueryParam("limit") Integer limit, 
			@QueryParam("startIndex") Integer startIndex,
			@QueryParam("filter_cnr") String filterCnr, 
			@QueryParam("filter_company") String filterCompany,
			@QueryParam("filter_withCancelled") Boolean filterWithHidden) {
		List<ProjectEntry> projects = new ArrayList<ProjectEntry>() ;

		if(null == userId || 0 == userId.trim().length()) {
			respondBadRequest("userid", "{empty}") ;
			return projects ;
		}
		
		try {
			TheClientDto theClientDto = clientCall.theClientFindByUserLoggedIn(userId) ;
			if(null == theClientDto || null == theClientDto.getIDPersonal()) {
				respondUnauthorized() ;
				return projects ;
			}

			Globals.setTheClientDto(theClientDto) ;	
			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(buildFilterCnr(filterCnr)) ;
			collector.add(buildFilterCompanyName(filterCompany)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;

			ProjectQuery query = new ProjectQuery(parameterCall) ;			
			QueryParameters params = query.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
			
			QueryResult result = query.setQuery(params) ;
			projects = query.getResultList(result) ;
		} catch(NamingException e) {
			e.printStackTrace() ;
//		} catch(RemoteException e) {
//			e.printStackTrace() ;
		} finally {
			
		}
	
		return projects ;
	}
	
	private FilterKriterium buildFilterCnr(String cnr) {
		if(cnr == null || cnr.trim().length() == 0) return null ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt("c_nr", cnr.trim().replace("'", ""), 
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
				"flrpartner.c_name1nachnamefirmazeile1", companyName.trim().replace("'", ""),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING,
				true, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
}
