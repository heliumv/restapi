package com.heliumv.api.project;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IClientCall;
import com.lp.server.system.service.TheClientDto;

@Service("hvProject")
@Path("/api/v1/project/")
public class ProjectApi extends BaseApi {

	@Autowired
	private IClientCall clientCall ;
	
	@GET
	@Path("/{userid}")
	@Produces({"application/json", "application/xml"})	
	public List<ProjectEntry> getProjects(
			@PathParam("userid") String userId) {
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
//		} catch(NamingException e) {
//			e.printStackTrace() ;
//		} catch(RemoteException e) {
//			e.printStackTrace() ;
		} finally {
			
		}
	
		return projects ;
	}
}
