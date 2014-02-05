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
package com.heliumv.api;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.Globals;
import com.heliumv.factory.IClientCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.tools.StringHelper;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class BaseApi {
	@Context
	private HttpServletResponse response ;
	
	@Context
	private HttpServletRequest request ;

	@Autowired
	private IClientCall clientCall ;
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public final static int  UNPROCESSABLE_ENTITY  = 422 ;  /* RFC 4918 */
	
	public final static String FORMAT_JSON = "application/json;charset=UTF-8" ;
	public final static String FORMAT_XML = "application/xml;charset=UTF-8" ;
	
	public final static String[] FORMAT_JSON_XML = {FORMAT_JSON, FORMAT_XML} ;
	

	public static class HvErrorCode {
		public final static Integer REMOTE_EXCEPTION         = 1 ;
		public final static Integer NAMING_EXCEPTION         = 2 ;
		public final static Integer UNPROCESSABLE_ENTITY     = 3 ;
		public final static Integer UNKNOWN_ENTITY           = 4 ;
		public final static Integer EJB_EXCEPTION            = 5 ;
		public final static Integer CLIENTPROTOCOL_EXCEPTION = 6 ;
		public final static Integer IO_EXCEPTION             = 7 ;
		public final static Integer EXPECTATION_FAILED       = 8 ;
		public final static Integer VALIDATION_FAILED        = 9 ;
	}
	
	public static class Param {
		public final static String USERID = "userid" ;
		public final static String LIMIT = "limit" ;
		public final static String STARTINDEX = "startIndex" ;	
		
		public final static String ITEMCNR = "itemCnr" ;
		public final static String ITEMID  = "itemid" ;

		public final static String DELIVERYCNR = "deliveryCnr" ;
		public final static String DELIVERYID  = "deliveryid" ;
	
		public final static String ORDERCNR    = "orderCnr" ;
		public final static String ORDERID     = "orderid" ;
		
		public final static String CUSTOMERID = "customerid" ;
	}
	
	public static class ParamInHeader {
		public final static String TOKEN = "hvtoken" ;
	}
	
	public class Filter {
		private final static String BASE = "filter_" ;
		public final static String CNR    = BASE + "cnr" ;
  		public final static String HIDDEN = BASE + "withHidden" ;
	}
	
	private ResponseBuilder getResponseBuilder() {
		return new ResponseBuilderImpl() ;
	}
	
	protected String getUuidForValue(String value) {
		return value ;
	}
	
	protected String getValueForUuid(String uuid) {
		return uuid ;
	}
	
	public TheClientDto connectClient(String headerUserId, String paramUserId) {
		if(!StringHelper.isEmpty(headerUserId)) return connectClient(headerUserId) ;
		return connectClient(paramUserId) ;
	}
	
	public TheClientDto connectClient(String userId) {
		globalInfo.setTheClientDto(null) ;
		Globals.setTheClientDto(null) ;
		
		if(StringHelper.isEmpty(userId)) {
			respondBadRequestValueMissing("userid") ;
			return null ;
		}

		if(getServletRequest().getContentLength() >1000) {
			respondBadRequestValueMissing("userid") ;
			return null ;			
		}
		
		String hvId = getValueForUuid(userId) ;
		TheClientDto theClientDto = clientCall.theClientFindByUserLoggedIn(hvId) ;
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
				.header("x-hv-error-code", HvErrorCode.EJB_EXCEPTION.toString())
				.header("x-hv-error-code-extended", new Integer(e.getCode()).toString())
				.header("x-hv-error-description", e.getCause().getMessage()).build() ;		
	}

	public Response getUnavailable(RemoteException e) {
		return getResponseBuilder().status(Response.Status.INTERNAL_SERVER_ERROR)
				.header("x-hv-error-code", HvErrorCode.REMOTE_EXCEPTION.toString())
				.header("x-hv-error-description", e.getMessage()).build() ;		
	}
	
	public Response getUnavailable(NamingException e) {
		return getResponseBuilder().status(Response.Status.INTERNAL_SERVER_ERROR)
				.header("x-hv-error-code", HvErrorCode.NAMING_EXCEPTION.toString())
				.header("x-hv-error-description", e.getMessage()).build() ;		
	}


	public void respondForbidden() {
		getServletResponse().setStatus(Response.Status.FORBIDDEN.getStatusCode()) ;
	}

	public void respondExpectationFailed() {
//      Enunciate kennt EXPECTATION_FAILED nicht? Obwohl das im jaxws-api-m10.jar enthalten ist?
//		getServletResponse().setStatus(Response.Status.EXPECTATION_FAILED.getStatusCode()) ;
		getServletResponse().setStatus(417) ;
	}
	
	public void respondExpectationFailed(Integer hvErrorCode) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.EXPECTATION_FAILED.toString()) ;
		getServletResponse().setHeader("x-hv-error-code-extended", hvErrorCode.toString()) ;
//		getServletResponse().setStatus(Response.Status.EXPECTATION_FAILED.getStatusCode()) ;
		getServletResponse().setStatus(417) ;
	}

	public void respondUnavailable(NamingException e) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.NAMING_EXCEPTION.toString()) ;
		getServletResponse().setHeader("x-hv-error-description", e.getMessage()) ;		
		getServletResponse().setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) ;		
	}
	
	public void respondUnavailable(RemoteException e) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.REMOTE_EXCEPTION.toString()) ;
		getServletResponse().setHeader("x-hv-error-description", e.getMessage()) ;		
		getServletResponse().setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) ;		
	}

	public void respondUnavailable(ClientProtocolException e) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.CLIENTPROTOCOL_EXCEPTION.toString()) ;
		getServletResponse().setHeader("x-hv-error-description", e.getMessage()) ;		
// TODO: Enunciate kennt "BAD_GATEWAY" nicht??
//		getServletResponse().setStatus(Response.Status.BAD_GATEWAY.getStatusCode()) ;			
		getServletResponse().setStatus(502) ;
	}

	public void respondUnavailable(IOException e) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.IO_EXCEPTION.toString()) ;
		getServletResponse().setHeader("x-hv-error-description", e.getMessage()) ;		
// TODO: Enunciate kennt "BAD_GATEWAY" nicht??
//		getServletResponse().setStatus(Response.Status.BAD_GATEWAY.getStatusCode()) ;			
		getServletResponse().setStatus(502) ;			
	}
	
	public void respondBadRequest(EJBExceptionLP e) {
		if(e.getCode() == EJBExceptionLP.FEHLER_FALSCHER_MANDANT) {
			respondNotFound() ;
			return ;
		}
		
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.EJB_EXCEPTION.toString()) ;
		getServletResponse().setHeader("x-hv-error-code-extended", new Integer(e.getCode()).toString()) ;
		getServletResponse().setHeader("x-hv-error-description", e.getCause().getMessage()) ;		
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;		
	}

	public void respondBadRequest(Integer hvErrorCode) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.EXPECTATION_FAILED.toString()) ;
		getServletResponse().setHeader("x-hv-error-code-extended", hvErrorCode.toString()) ;
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;				
	}
	
	public void appendBadRequestData(String key, String value) {
		getServletResponse().setHeader("x-hv-error-additional-data-key", key) ;				
		getServletResponse().setHeader("x-hv-error-additional-data-value", value) ;				
	}
	
	public Response getBadRequest(String key, Object value) {
		return getResponseBuilder().status(Response.Status.BAD_REQUEST)
				.header("x-hv-error-code", HvErrorCode.VALIDATION_FAILED)
				.header("x-hv-error-key", key)
				.header("x-hv-error-value", value).build() ;
	}
	
	public Response getBadRequest(EJBExceptionLP e) {
		return getResponseBuilder().status(Response.Status.BAD_REQUEST)
				.header("x-hv-error-code", HvErrorCode.EJB_EXCEPTION)
				.header("x-hv-error-code-extended", new Integer(e.getCode()).toString())
				.header("x-hv-error-description", e.getCause().getMessage()).build() ;
	}
	
	/**
	 * Den Servlet Response auf "UNAUTHORIZED" setzen
	 */
	public void respondBadRequest(String key, String value) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.VALIDATION_FAILED.toString()) ;
		getServletResponse().setHeader("x-hv-error-key", key) ;
		getServletResponse().setHeader("x-hv-error-value", value) ;
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;		
	}
	
	public void respondBadRequestValueMissing(String key) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.VALIDATION_FAILED.toString()) ;
		getServletResponse().setHeader("x-hv-error-key", key) ;
		getServletResponse().setHeader("x-hv-error-value", "{empty}") ;
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;		
	}
	
	public void respondNotFound(String key, String value) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.UNKNOWN_ENTITY.toString()) ;
		getServletResponse().setHeader("x-hv-error-key", key) ;
		getServletResponse().setHeader("x-hv-error-value", value) ;
		getServletResponse().setStatus(Response.Status.NOT_FOUND.getStatusCode()) ;				
	}
	
	public void respondNotFound() {
		getServletResponse().setStatus(Response.Status.NOT_FOUND.getStatusCode()) ;		
	}
	
	public void respondOkay() {
		getServletResponse().setStatus(Response.Status.OK.getStatusCode()) ;				
	}
	
	public void respondUnprocessableEntity(String key, String value) {
		getServletResponse().setHeader("x-hv-error-code", HvErrorCode.UNPROCESSABLE_ENTITY.toString()) ;
		getServletResponse().setHeader("x-hv-error-key", key) ;
		getServletResponse().setHeader("x-hv-error-value", value) ;
		getServletResponse().setStatus(UNPROCESSABLE_ENTITY) ;
	}
	
	public void setHttpServletResponse(HttpServletResponse theResponse) {
		response = theResponse ;
	}
	
	protected HttpServletResponse getServletResponse() {
		return response ;
	}	
	
	protected HttpServletRequest getServletRequest() {
		return request ;
	}
}
