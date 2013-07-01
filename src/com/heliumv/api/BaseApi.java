package com.heliumv.api;

import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.Globals;
import com.heliumv.factory.IClientCall;
import com.heliumv.factory.IGlobalInfo;
// import com.heliumv.factory.IServerCall;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class BaseApi {
	@Context
	private HttpServletResponse response ;
	
//	@Autowired
//	private IServerCall serverCall ;

	@Autowired
	private IClientCall clientCall ;
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
	private ResponseBuilder getResponseBuilder() {
		return new ResponseBuilderImpl() ;
	}
	
	public TheClientDto connectClient(String userId) {
		globalInfo.setTheClientDto(null) ;
		Globals.setTheClientDto(null) ;
		
		if(null == userId || 0 == userId.trim().length()) {
			respondBadRequest("userid", "{empty}") ;
			return null ;
		}

		TheClientDto theClientDto = clientCall.theClientFindByUserLoggedIn(userId) ;
		if (null == theClientDto || null == theClientDto.getIDPersonal()) {
			respondUnauthorized() ; 
		} else {
			Globals.setTheClientDto(theClientDto) ;			
			globalInfo.setTheClientDto(theClientDto) ;
		}
		
		return theClientDto ;
	}

	/**
	 * Den Servlet Response auf "UNAUTHORIZED" setzen
	 */
	public void respondUnauthorized() {
		getServletResponse().setStatus(Response.Status.UNAUTHORIZED.getStatusCode()) ;		
	}
	
	public Response getUnauthorized() {
		return getResponseBuilder().status(Response.Status.UNAUTHORIZED).build() ;
	}
	
	public Response getNoContent() {
		return getResponseBuilder().status(Response.Status.NO_CONTENT).build() ;
	}

	public Response getInternalServerError() {
		return getResponseBuilder().status(Response.Status.INTERNAL_SERVER_ERROR)
			.build() ;
	}

	public Response getInternalServerError(String cause) {
		return getResponseBuilder().status(Response.Status.INTERNAL_SERVER_ERROR)
			.header("x-hv-cause", cause).build() ;
	}
	
	public Response getInternalServerError(EJBExceptionLP e) {
		return getResponseBuilder().status(Response.Status.INTERNAL_SERVER_ERROR)
				.header("x-hv-error-code", 10000 + e.getCode())
				.header("x-hv-error-description", e.getMessage()).build() ;		
	}

	public Response getUnavailable(RemoteException e) {
		return getResponseBuilder().status(Response.Status.INTERNAL_SERVER_ERROR)
				.header("x-hv-error-code", 1)
				.header("x-hv-error-description", e.getMessage()).build() ;		
	}
	
	public Response getUnavailable(NamingException e) {
		return getResponseBuilder().status(Response.Status.INTERNAL_SERVER_ERROR)
				.header("x-hv-error-code", 2)
				.header("x-hv-error-description", e.getMessage()).build() ;		
	}

	public void respondUnavailable(NamingException e) {
		getServletResponse().setHeader("x-hv-error-code", "2") ;
		getServletResponse().setHeader("x-hv-error-description", e.getMessage()) ;		
		getServletResponse().setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) ;		
	}
	
	public void respondUnavailable(RemoteException e) {
		getServletResponse().setHeader("x-hv-error-code", "1") ;
		getServletResponse().setHeader("x-hv-error-description", e.getMessage()) ;		
		getServletResponse().setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) ;		
	}

	public void respondBadRequest(EJBExceptionLP e) {
		getServletResponse().setHeader("x-hv-error-code", "5") ;
		getServletResponse().setHeader("x-hv-error-code-extended", new Integer(e.getCode()).toString()) ;
		getServletResponse().setHeader("x-hv-error-description", e.getCause().getMessage()) ;		
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;		
	}

	public void respondBadRequest(Integer hvErrorCode) {
		getServletResponse().setHeader("x-hv-error-code", "5") ;
		getServletResponse().setHeader("x-hv-error-code-extended", hvErrorCode.toString()) ;
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;				
	}
	
	public void appendBadRequestData(String key, String value) {
		getServletResponse().setHeader("x-hv-error-additional-data-key", key) ;				
		getServletResponse().setHeader("x-hv-error-additional-data-value", value) ;				
	}
	
	public Response getBadRequest(String key, Object value) {
		return getResponseBuilder().status(Response.Status.BAD_REQUEST)
				.header("x-hv-error-code", 3)
				.header("x-hv-error-key", key)
				.header("x-hv-error-value", value).build() ;
	}
	
	public Response getBadRequest(EJBExceptionLP e) {
		return getResponseBuilder().status(Response.Status.BAD_REQUEST)
				.header("x-hv-error-code", "5")
				.header("x-hv-error-code-extended", new Integer(e.getCode()).toString())
				.header("x-hv-error-description", e.getCause().getMessage()).build() ;
	}
	
	/**
	 * Den Servlet Response auf "UNAUTHORIZED" setzen
	 */
	public void respondBadRequest(String key, String value) {
		getServletResponse().setHeader("x-hv-error-code", "3") ;
		getServletResponse().setHeader("x-hv-error-key", key) ;
		getServletResponse().setHeader("x-hv-error-value", value) ;
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;		
	}
	
	public void respondNotFound(String key, String value) {
		getServletResponse().setHeader("x-hv-error-code", "4") ;
		getServletResponse().setHeader("x-hv-error-key", key) ;
		getServletResponse().setHeader("x-hv-error-value", value) ;
		getServletResponse().setStatus(Response.Status.NOT_FOUND.getStatusCode()) ;				
	}
	
	public void respondNotFound() {
		getServletResponse().setStatus(Response.Status.NOT_FOUND.getStatusCode()) ;		
	}
	
	public void setHttpServletResponse(HttpServletResponse theResponse) {
		response = theResponse ;
	}
	
	protected HttpServletResponse getServletResponse() {
		return response ;
	}	
}
