package com.heliumv.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.jboss.tm.JBossRollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.BaseApi.HvErrorCode;
import com.heliumv.factory.IGlobalInfo;
import com.lp.client.frame.ExceptionLP;
import com.lp.client.pc.LPMain;
import com.lp.client.pc.LPMessages;
import com.lp.util.EJBExceptionLP;

public class HvEJBExceptionLPExceptionMapper implements
		ExceptionMapper<EJBExceptionLP> {
	private static Logger log = LoggerFactory.getLogger(HvRemoteExceptionMapper.class) ;

	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Override
	public Response toResponse(EJBExceptionLP e) {
		log.info("default-log", e);
		if(e.getCode() == EJBExceptionLP.FEHLER_FALSCHER_MANDANT) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		ResponseBuilderImpl r = new ResponseBuilderImpl() ;
		r.header(BaseApi.X_HV_ERROR_CODE, HvErrorCode.EJB_EXCEPTION.toString())
			.header(BaseApi.X_HV_ERROR_CODE_EXTENDED,  new Integer(e.getCode()).toString())
			.header(BaseApi.X_HV_ERROR_CODE_DESCRIPTION, e.getCause().getMessage())
			.status(Response.Status.BAD_REQUEST) ;
		try {
			ExceptionLP elp = handleThrowable(e);
			LPMain.getInstance().setUISprLocale(globalInfo.getTheClientDto().getLocUi());
			String clientErrorMessage = new LPMessages().getMsg(elp) ;
			if(clientErrorMessage != null) {
				r.header(BaseApi.X_HV_ERROR_CODE_TRANSLATED, clientErrorMessage);
			}
		} catch(Throwable t) {
			log.error("Throwable on handleThrowable", t);
		}
		
		return r.build() ;
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
