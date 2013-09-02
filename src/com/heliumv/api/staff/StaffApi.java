package com.heliumv.api.staff;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.query.StaffQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

@Service("hvStaff")
@Path("/api/v1/staff/")
public class StaffApi extends BaseApi {

	@Autowired
	private IMandantCall mandantCall ;
	@Autowired
	private StaffQuery staffQuery ;
	
	@GET
	@Path("/{userid}")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<StaffEntry> getStaff(
			@PathParam("userid") String userId,
			@QueryParam("limit") Integer limit,
			@QueryParam("startIndex") Integer startIndex) {
		List<StaffEntry> entries = new ArrayList<StaffEntry>() ;

		if(connectClient(userId) == null) return entries ;
	
		FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
//		collector.add(buildFilterCnr(filterCnr)) ;
//		collector.add(buildFilterCompanyName(filterCompany)) ;
		FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
		
		try {
			QueryParameters params = staffQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
		
			QueryResult result = staffQuery.setQuery(params) ;
			entries = staffQuery.getResultList(result) ;			
//		} catch(NamingException e) {
//			respondUnavailable(e) ;
		} finally {
		}
		return entries ;
	}
}
