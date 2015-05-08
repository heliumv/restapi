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
import org.jboss.tm.JBossRollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.Globals;
import com.heliumv.factory.IClientCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.tools.StringHelper;
import com.lp.client.frame.ExceptionLP;
import com.lp.client.pc.LPMain;
import com.lp.client.pc.LPMessages;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class BaseApi implements IBaseApiHeaderConstants {
	private static Logger log = LoggerFactory.getLogger(BaseApi.class) ;

	@Context
	private HttpServletResponse response ;
	
	@Context
	private HttpServletRequest request ;

	@Autowired
	private IClientCall clientCall ;
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
//	@Context
//	protected MessageContext mc ;
	
	
	public final static int  UNPROCESSABLE_ENTITY  = 422 ;  /* RFC 4918 */
	public final static int  LOCKED                = 423 ;
	public final static int  TOO_MANY_REQUESTS     = 429 ;
	
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
		
		public final static String POSITIONID  = "positionid" ;
		public final static String PARTLISTID  = "partlistid" ;
		
		public final static String MACHINEID   = "machineid" ;
		
		public final static String ORDERBY     = "$orderby" ;
	}
	
	public static class ParamInHeader {
		public final static String TOKEN = "hvtoken" ;
	}
	
	public class Filter {
		private final static String BASE = "filter_" ;
		public final static String CNR    = BASE + "cnr" ;
  		public final static String HIDDEN = BASE + "withHidden" ;
	}
	
	
	public class With {
		private final static String BASE = "with_" ;
		public final static String DESCRIPTION = BASE + "description" ;
	}

	
	private ResponseBuilder getResponseBuilder() {
		return new ResponseBuilderImpl() ;
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

		if(getServletRequest() != null && getServletRequest().getContentLength() >1000) {
			respondBadRequestValueMissing("userid") ;
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
	
	public void respondTooManyRequests() {
		getServletResponse().setStatus(TOO_MANY_REQUESTS);
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
				.header(X_HV_ERROR_CODE, HvErrorCode.EJB_EXCEPTION.toString())
				.header(X_HV_ERROR_CODE_EXTENDED, new Integer(e.getCode()).toString())
				.header(X_HV_ERROR_CODE_DESCRIPTION, e.getCause().getMessage()).build() ;		
	}

	public Response getUnavailable(RemoteException e) {
		return getResponseBuilder().status(Response.Status.INTERNAL_SERVER_ERROR)
				.header(X_HV_ERROR_CODE, HvErrorCode.REMOTE_EXCEPTION.toString())
				.header(X_HV_ERROR_CODE_DESCRIPTION, e.getMessage()).build() ;		
	}
	
	public Response getUnavailable(NamingException e) {
		return getResponseBuilder().status(Response.Status.INTERNAL_SERVER_ERROR)
				.header(X_HV_ERROR_CODE, HvErrorCode.NAMING_EXCEPTION.toString())
				.header(X_HV_ERROR_CODE_DESCRIPTION, e.getMessage()).build() ;		
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
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.EXPECTATION_FAILED.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_CODE_EXTENDED, hvErrorCode.toString()) ;
//		getServletResponse().setStatus(Response.Status.EXPECTATION_FAILED.getStatusCode()) ;
		getServletResponse().setStatus(417) ;
	}

	public void respondUnavailable(NamingException e) {
		log.info("default-log", e);
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.NAMING_EXCEPTION.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_CODE_DESCRIPTION, e.getMessage()) ;		
		getServletResponse().setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) ;		
	}
	
	public void respondUnavailable(RemoteException e) {
		log.info("default-log", e);
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.REMOTE_EXCEPTION.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_CODE_DESCRIPTION, e.getMessage()) ;		
		getServletResponse().setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) ;		
	}

	public void respondUnavailable(ClientProtocolException e) {
		log.info("default-log", e);
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.CLIENTPROTOCOL_EXCEPTION.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_CODE_DESCRIPTION, e.getMessage()) ;		
// TODO: Enunciate kennt "BAD_GATEWAY" nicht??
//		getServletResponse().setStatus(Response.Status.BAD_GATEWAY.getStatusCode()) ;			
		getServletResponse().setStatus(502) ;
	}

	public void respondUnavailable(IOException e) {
		log.info("default-log", e);
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.IO_EXCEPTION.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_CODE_DESCRIPTION, e.getMessage()) ;		
// TODO: Enunciate kennt "BAD_GATEWAY" nicht??
//		getServletResponse().setStatus(Response.Status.BAD_GATEWAY.getStatusCode()) ;			
		getServletResponse().setStatus(502) ;			
	}
	
	public void respondBadRequest(EJBExceptionLP e) {
		log.info("default-log", e);
		if(e.getCode() == EJBExceptionLP.FEHLER_FALSCHER_MANDANT) {
			respondNotFound() ;
			return ;
		}

		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.EJB_EXCEPTION.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_CODE_EXTENDED, new Integer(e.getCode()).toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_CODE_DESCRIPTION, e.getCause().getMessage()) ;	

		try {
			ExceptionLP elp = handleThrowable(e);
			LPMain.getInstance().setUISprLocale(globalInfo.getTheClientDto().getLocUi());
			String clientErrorMessage = new LPMessages().getMsg(elp) ;
			if(clientErrorMessage != null) {
				getServletResponse().setHeader(X_HV_ERROR_CODE_TRANSLATED, clientErrorMessage);
			}
		} catch(Throwable t) {
			log.error("Throwable on handleThrowable", t);
		}
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;		
	}

	public void respondBadRequest(Integer hvErrorCode) {
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.EXPECTATION_FAILED.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_CODE_EXTENDED, hvErrorCode.toString()) ;
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;				
	}
	
	public void appendBadRequestData(String key, String value) {
		getServletResponse().addHeader(X_HV_ADDITIONAL_ERROR_KEY, key) ;				
		getServletResponse().addHeader(X_HV_ADDITIONAL_ERROR_VALUE, value) ;				
	}
	
	public Response getBadRequest(String key, Object value) {
		return getResponseBuilder().status(Response.Status.BAD_REQUEST)
				.header(X_HV_ERROR_CODE, HvErrorCode.VALIDATION_FAILED)
				.header(X_HV_ERROR_KEY, key)
				.header(X_HV_ERROR_VALUE, value == null ? "null" : value).build() ;
	}

	public Response getBadRequestValueMissing(String key) {
		return getResponseBuilder().status(Response.Status.BAD_REQUEST)
				.header(X_HV_ERROR_CODE, HvErrorCode.VALIDATION_FAILED.toString())
				.header(X_HV_ERROR_KEY, key)
				.header(X_HV_ERROR_VALUE, "{empty}").build() ;	
	}

	public Response getBadRequest(EJBExceptionLP e) {
		return getResponseBuilder().status(Response.Status.BAD_REQUEST)
				.header(X_HV_ERROR_CODE, HvErrorCode.EJB_EXCEPTION)
				.header(X_HV_ERROR_CODE_EXTENDED, new Integer(e.getCode()).toString())
				.header(X_HV_ERROR_CODE_DESCRIPTION, e.getCause().getMessage()).build() ;
	}
	
	/**
	 * Den Servlet Response auf "BAD_REQUEST" setzen
	 */
	public void respondBadRequest(String key, String value) {
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.VALIDATION_FAILED.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_KEY, key) ;
		getServletResponse().setHeader(X_HV_ERROR_VALUE, value) ;
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;		
	}
	
	public void respondBadRequestValueMissing(String key) {
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.VALIDATION_FAILED.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_KEY, key) ;
		getServletResponse().setHeader(X_HV_ERROR_VALUE, "{empty}") ;
		getServletResponse().setStatus(Response.Status.BAD_REQUEST.getStatusCode()) ;		
	}
	
	public void respondNotFound(String key, String value) {
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.UNKNOWN_ENTITY.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_KEY, key) ;
		getServletResponse().setHeader(X_HV_ERROR_VALUE, value) ;
		getServletResponse().setStatus(Response.Status.NOT_FOUND.getStatusCode()) ;				
	}

	public void respondModified() {
		getServletResponse().setStatus(Response.Status.CONFLICT.getStatusCode());
	}

	public void respondGone() {
		getServletResponse().setStatus(Response.Status.GONE.getStatusCode());		
	}
	
	public void respondLocked() {
//		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.UNKNOWN_ENTITY.toString()) ;
//		getServletResponse().setHeader(X_HV_ERROR_KEY, key) ;
//		getServletResponse().setHeader(X_HV_ERROR_VALUE, value) ;
		getServletResponse().setStatus(LOCKED) ;				
	}
	
	public void respondNotFound() {
		getServletResponse().setStatus(Response.Status.NOT_FOUND.getStatusCode()) ;		
	}
	
	public void respondOkay() {
		getServletResponse().setStatus(Response.Status.OK.getStatusCode()) ;				
	}
	
	public void respondUnprocessableEntity(String key, String value) {
		getServletResponse().setHeader(X_HV_ERROR_CODE, HvErrorCode.UNPROCESSABLE_ENTITY.toString()) ;
		getServletResponse().setHeader(X_HV_ERROR_KEY, key) ;
		getServletResponse().setHeader(X_HV_ERROR_VALUE, value) ;
		getServletResponse().setStatus(UNPROCESSABLE_ENTITY) ;
	}
	
	public void setHttpServletResponse(HttpServletResponse theResponse) {
		response = theResponse ;
	}
	
	public HttpServletResponse getServletResponse() {
		return response ;
	}	
	
	protected HttpServletRequest getServletRequest() {
		return request ;
	}
	
	/**
	 * Ein bewusstes Duplikat von Delegate.handleThrowable im LPClientPC</br>
	 * <p>Traue mich momentan noch nicht dr&uuml;ber das zu refaktoren</p>
	 * 
	 * @param t
	 * @return
	 */
	protected ExceptionLP handleThrowable(Throwable t) {
		if(t instanceof ExceptionLP) return (ExceptionLP) t ;
		
		if(t instanceof RuntimeException) {
			RuntimeException reI = (RuntimeException) t;
			// Throwable t2 = reI.getCause();
			// if (t2 != null && t2 instanceof ServerException) {
			Throwable t3 = reI.getCause();
			if (t3 instanceof EJBExceptionLP) {
				EJBExceptionLP ejbt4 = (EJBExceptionLP) t3;
				if (ejbt4 instanceof EJBExceptionLP) {
					Throwable ejbt5 = ejbt4.getCause();
					if (ejbt5 instanceof EJBExceptionLP) {
						// wegen zB. unique key knaller
						EJBExceptionLP ejbt6 = (EJBExceptionLP) ejbt5;
						return new ExceptionLP(ejbt6.getCode(),
								ejbt6.getMessage(),
								ejbt6.getAlInfoForTheClient(), ejbt6.getCause());
					} else if (ejbt5 != null) {
						Throwable ejbt7 = ejbt5.getCause();
						if (ejbt7 instanceof EJBExceptionLP) {
							// zB. fuer WARNUNG_KTO_BESETZT
							EJBExceptionLP ejbt8 = (EJBExceptionLP) ejbt7;
							return new ExceptionLP(ejbt8.getCode(),
									ejbt8.getMessage(),
									ejbt8.getAlInfoForTheClient(),
									ejbt8.getCause());
						} else {
							return new ExceptionLP(ejbt4.getCode(),
									ejbt4.getMessage(),
									ejbt4.getAlInfoForTheClient(),
									ejbt4.getCause());
						}
					} else {
						return new ExceptionLP(ejbt4.getCode(),
								ejbt4.getMessage(),
								ejbt4.getAlInfoForTheClient(), ejbt4.getCause());
					}
				}
			} else if (reI instanceof EJBExceptionLP) {
				EJBExceptionLP exc = (EJBExceptionLP) reI;
				return new ExceptionLP(exc.getCode(), exc.getMessage(),
						exc.getAlInfoForTheClient(), exc.getCause());
			} else if (t3 instanceof JBossRollbackException) {
				// zB. unique key knaller.
				JBossRollbackException ejb = (JBossRollbackException) t3;
				return new ExceptionLP(EJBExceptionLP.FEHLER_DUPLICATE_UNIQUE,
						ejb.getMessage(), null, ejb.getCause());
			} else if (t3 instanceof EJBExceptionLP) {
				// MB 13. 03. 06 wird ausgeloest, wenn belegnummern ausserhalb
				// des gueltigen bereichs generiert werden
				// (liegt vermutlich am localen interface des BN-Generators)
				EJBExceptionLP ejbt6 = (EJBExceptionLP) t3;
				return new ExceptionLP(ejbt6.getCode(), ejbt6.getMessage(),
						ejbt6.getAlInfoForTheClient(), ejbt6.getCause());
			} else if (t3 instanceof java.io.InvalidClassException) {
				// zB. unique key knaller.
				java.io.InvalidClassException ejb = (java.io.InvalidClassException) t3;
				return new ExceptionLP(EJBExceptionLP.FEHLER_BUILD_CLIENT,
						ejb.getMessage(), null, ejb.getCause());
			} else if (t3 instanceof java.lang.NoClassDefFoundError) {
				// zB. unique key knaller.
				java.lang.NoClassDefFoundError ejb = (java.lang.NoClassDefFoundError) t3;
				return new ExceptionLP(
						EJBExceptionLP.FEHLER_NOCLASSDEFFOUNDERROR,
						ejb.getMessage(), null, ejb.getCause());
			}
		}
		
		if (t instanceof java.lang.IllegalStateException) {
			return new ExceptionLP(EJBExceptionLP.FEHLER_TRANSACTION_TIMEOUT, t);
		}
		
		if (t != null && t.getCause() != null) {
			return new ExceptionLP(EJBExceptionLP.FEHLER, t.getMessage(), null,
					t.getCause());
		}
		
		if(t != null) {
			return new ExceptionLP(
					EJBExceptionLP.FEHLER, t.getMessage(), null, t);
		} else {
			return new ExceptionLP(EJBExceptionLP.FEHLER, "null exception", new Exception()) ;
		}
	}
}
