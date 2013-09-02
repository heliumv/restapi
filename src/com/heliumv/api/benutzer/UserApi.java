package com.heliumv.api.benutzer;

import java.rmi.RemoteException;
import java.util.Locale;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IClientCall;
import com.heliumv.factory.ILogonCall;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.ISystemCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

@Service("hvUser")
@Path("/api/v1/")
public class UserApi extends BaseApi implements IUserApi {

	@Autowired
	private ILogonCall logonCall ;
	
	@Autowired
	private IClientCall clientCall ;
	
	@Autowired
	private ISystemCall systemCall ;
	
	@Autowired
	private IMandantCall mandantCall ;
	
	@Override
	@POST
	@Path("/logon/")
	@Consumes({"application/json", "application/xml"})
	@Produces({FORMAT_JSON, FORMAT_XML})
	public LoggedOnEntry logon(LogonEntry logonEntry) {
		if(logonEntry == null) {
			respondBadRequest("logonEntry", "null") ;
			return null ;
		}
		if(StringHelper.isEmpty(logonEntry.getUsername())) {
			respondBadRequest("username", "null") ;
			return null ;
		}
		if(StringHelper.isEmpty(logonEntry.getPassword())) {
			respondBadRequest("password", "null") ;
			return null ;
		}
		
		try {
			String mandant = logonEntry.getClient() ;
			if(StringHelper.isEmpty(mandant)) {
				mandant = systemCall.getHauptmandant() ;
			}

			Locale theLocale = null ;
			if(StringHelper.isEmpty(logonEntry.getLocaleString())) {
				theLocale = mandantCall.getLocaleDesHauptmandanten() ;
			} else {
				theLocale = getLocale(logonEntry.getLocaleString()) ;
			}

			TheClientDto theClientDto = logonCall.logon(
					logonEntry.getUsername(), logonEntry.getPassword().toCharArray(), theLocale, mandant) ;
			if(theClientDto == null) {
				respondUnauthorized() ;
				return null ;
			}
			
			LoggedOnEntry entry = new LoggedOnEntry() ;
			entry.setToken(theClientDto.getIDUser()) ;
			entry.setClient(theClientDto.getMandant()) ;
			entry.setLocaleString(theClientDto.getLocMandantAsString().trim()) ;
			return entry ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			// respondBadRequest(e) ;
			// ABSICHTLICH Unauthorized um einem Angreifer keine Hinweise zu geben
			respondUnauthorized() ;
		}
		
		return null ;
	}
	
//	@GET
//	@Path("/logon/{username}/{password}/{mandant}/{locale}")
//	@Produces({"application/json", "text/plain", "application/xml"})
//	public Response logon(
//			@PathParam("username") String username, 
//			@PathParam("password") String password, 
//			@PathParam("mandant") String mandant, 
//			@PathParam("locale") String localeString) {
//		ResponseBuilderImpl builder = new ResponseBuilderImpl() ;
//
//		if(StringHelper.isEmpty(username)) return builder.status(Response.Status.BAD_REQUEST).header("username", "").build() ;
//		if(StringHelper.isEmpty(password)) return builder.status(Response.Status.BAD_REQUEST).header("password", "").build() ;
//		if(StringHelper.isEmpty(mandant)) return builder.status(Response.Status.BAD_REQUEST).header("mandant", "").build() ;
//		if(StringHelper.isEmpty(localeString)) return builder.status(Response.Status.BAD_REQUEST).header("locale", "").build() ;
//		
//		Locale l = getLocale(localeString) ;
//		if(l == null) return builder.status(Response.Status.BAD_REQUEST).header("locale", localeString).build() ;
//		
//		TheClientDto theClientDto = null ;
//		try {
////			theClientDto = getServer().getLogonCall().logon(username, password.toCharArray(), l, mandant) ;
//			theClientDto = logonCall.logon(username, password.toCharArray(), l, mandant) ;
//			if(theClientDto != null) {
//				ResponseBuilder rBuild = Response.ok(theClientDto.getIDUser()) ;
//				return rBuild.build();
//			} 
//		} catch(NamingException e) {
//			return builder.status(Response.Status.SERVICE_UNAVAILABLE).build() ;
//		} catch(RemoteException e) {
//			return builder.status(Response.Status.SERVICE_UNAVAILABLE).build() ;			
//		}
//		
//		return builder.status(Response.Status.SERVICE_UNAVAILABLE).build() ;
//	}
	
//	@GET
//	@Path("/logonId/{username}/{password}/{mandant}/{locale}")
//	@Produces({"application/json", "text/plain", "application/xml"})
//	public String logonId(
//			@PathParam("username")String username, 
//			@PathParam("password") String password, 
//			@PathParam("mandant") String mandant,
//			@PathParam("locale") String localeString) {
//
//		getServletResponse().setStatus(Response.Status.NOT_ACCEPTABLE.getStatusCode()) ;
//		
//		if(StringHelper.isEmpty(username)) return "" ;
//		if(StringHelper.isEmpty(password)) return "" ;
//		if(StringHelper.isEmpty(mandant)) return "" ;
//		if(StringHelper.isEmpty(localeString)) return "" ;
//		
//		Locale l = getLocale(localeString) ;
//		if(l == null) return "" ;
//		
//		TheClientDto theClientDto = null ;
//		try {
//			theClientDto = logonCall.logon(username, password.toCharArray(), l, mandant) ;
//			if(theClientDto != null) {
//				getServletResponse().setStatus(Response.Status.OK.getStatusCode()) ;
//				String id = theClientDto.getIDUser() ;
//				return id ;
//			} 
//		} catch(NamingException e) {
//			return "" ;
//		} catch(RemoteException e) {
//			return "" ;
//		}
//		
//		return "" ;
//	}
//
	
	@GET
	@Path("/logout/{token}")
	public Response logout(
			@PathParam("token") String token) {
		if(StringHelper.isEmpty(token)) return Response.status(Status.BAD_REQUEST).build() ;
		
		try {
			TheClientDto theClientDto = clientCall.theClientFindByUserLoggedIn(token) ;
			if(theClientDto != null) {
				logonCall.logout(theClientDto) ;
			}
		} catch(NamingException e) {
			return Response.status(Status.SERVICE_UNAVAILABLE.getStatusCode()).build() ;
		} catch(RemoteException e) {
			return Response.status(Status.SERVICE_UNAVAILABLE.getStatusCode()).build() ;
		}

		return Response.ok().build();
	}
	
	
	private Locale getLocale(String localeString) {
		if(localeString.length() != 4) return null ;
		Locale locale = new Locale(localeString.substring(0, 2), localeString.substring(2, 4)) ;
		return locale ;
	}	
}
