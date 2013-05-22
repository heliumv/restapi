package com.heliumv.api;

import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;

import com.heliumv.factory.IServerCall;
import com.heliumv.factory.impl.ServerCall;
import com.lp.util.EJBExceptionLP;

public class BaseApi {
	@Context
	private HttpServletResponse response ;
	
	private IServerCall server ;

	private ResponseBuilder getResponseBuilder() {
		return new ResponseBuilderImpl() ;
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

	public Response getBadRequest(String key, Object value) {
		return getResponseBuilder().status(Response.Status.BAD_GATEWAY)
				.header("x-hv-error-code", 3)
				.header("x-hv-error-key", key)
				.header("x-hv-error-value", value).build() ;
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
	
	public void setHttpServletResponse(HttpServletResponse theResponse) {
		response = theResponse ;
	}
	
	protected HttpServletResponse getServletResponse() {
		return response ;
	}


	protected IServerCall getServer() {
		if(server == null) {
			server = new ServerCall() ; 
		}
		return server ;
	}
}
