package com.heliumv.api.internal;

import java.rmi.RemoteException;
import java.util.Locale;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.BaseApi;
import com.heliumv.api.internal.CruisecontrolApi.ApiParam;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.ILogonCall;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IProjektCall;
import com.heliumv.tools.StringHelper;
import com.lp.client.frame.HelperClient;
import com.lp.server.projekt.service.ProjektDto;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;
import com.lp.util.Helper;

public abstract class AbstractProjectWorker {
	private static Logger log = LoggerFactory.getLogger(AbstractProjectWorker.class) ;

	@Autowired
	private IProjektCall projektCall ;
	@Autowired
	private IParameterCall parameterCall ;
	@Autowired
	private ILogonCall logonCall ;
	@Autowired
	private IMandantCall mandantCall ;
	@Autowired
	private IJudgeCall judgeCall ;
	
	private ProjectNrParser parser = new ProjectNrParser() ;
	private BaseApi api ;

	
	public AbstractProjectWorker() {		
	}
	
	public AbstractProjectWorker(BaseApi api) {
		this.api = api ;
	}
	
	public void setApi(BaseApi api) {
		this.api = api ;
	}
	
	public void setParser(ProjectNrParser parser) {
		this.parser = parser ;
	}
	
	public void workOnMessage(String message, String buildLabel) {
		workOnMessage(null, message, buildLabel);
	}
	
	public void workOnMessage(String hvToken, String message, String buildLabel) {
		if(!validParams(message, buildLabel)) return ;
		
		if(!parser.parse(message)) {
			api.respondNotFound() ;
			return ;
		}

		TheClientDto theClientDto = null ;
		Integer lockedId = null ;
		try {
			if(hvToken == null) {
				theClientDto = logon() ;
				hvToken = theClientDto.getIDUser() ;
			}
			theClientDto = api.connectClient(hvToken) ;
			if(null == theClientDto) return ;
			
			ProjektDto projektDto = findProjekt(parser.getProjectNumber()) ;
			if(projektDto == null) {
				api.respondNotFound("projectnr", parser.getProjectNumber()) ;
				return ;
			}
			
			try {				
				lockedId = lock(projektDto.getIId()) ;
				setup(projektDto, buildLabel) ;
				projektCall.updateProjekt(projektDto) ;
//				unlock(lockedId) ;
//				lockedId = null ;
			} catch(EJBExceptionLP e) {
				if(e.getCode() == EJBExceptionLP.FEHLER_BEIM_ANLEGEN) {
					System.out.println("Der Datensatz " + projektDto.getIId() + "(" + projektDto.getCNr() +")" + " war gesperrt") ;
					log.info("Projekt Id '" + projektDto.getIId() + "' (" + projektDto.getCNr() + ") war gesperrt.");
					api.respondLocked() ;
					return ;
				}
			}
			
			info(projektDto) ;
		} catch(RemoteException e) {
			log.error("RemoteException", e);
			api.respondUnavailable(e) ;
		} catch(NamingException e) {
			log.error("NamingException", e);
			api.respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			log.error("EJBExceptionLP", e);
			api.respondBadRequest(e) ;
		} finally {
			unlock(lockedId) ;
			logout(theClientDto) ;
		}		
	}
	
	protected abstract void setup(ProjektDto projectDto, String buildLabel) ;
	
	protected abstract void info(ProjektDto projectDto) ;
	
	private boolean validParams(String message, String buildLabel) {
		if(StringHelper.isEmpty(message)) {
			api.respondBadRequestValueMissing(ApiParam.MESSAGE) ;
			return false ;
		}
		
		if(StringHelper.isEmpty(buildLabel)) {
			api.respondBadRequestValueMissing(ApiParam.BUILDLABEL);
			return false ;
		}
		
		return true ;
	}		
	
	private ProjektDto findProjekt(String baseNr) throws NamingException, RemoteException, EJBExceptionLP {
		Integer projectNr = Integer.parseInt(baseNr) ;
		Integer geschaeftsjahr = parameterCall.getGeschaeftsjahr() ;
		int maxYears = 8 ;
		ProjektDto projektDto = null ;
	
		do {
			String s = formatProjectNr(geschaeftsjahr, projectNr) ;
			projektDto = projektCall.projektFindByCNrOhneExc(s) ;
			
			--geschaeftsjahr ;
			--maxYears ;
		} while(projektDto == null && maxYears >= 0) ;

		return projektDto ;
	}
	
	private String formatProjectNr(Integer year, Integer projectNr) throws NamingException, RemoteException {
		return projektCall.getBelegnr(projectNr, year) ;
	}

	private TheClientDto logon() throws RemoteException, NamingException {
		String logonUser = "lpwebappzemecs" ;
		Locale theLocale = mandantCall.getLocaleDesHauptmandanten() ;

		TheClientDto theClientDto = logonCall
				.programmedLogon( Helper.getFullUsername(logonUser), 
						new String(logonUser).toCharArray(),
						theLocale, null);
		return theClientDto ;
	}
	
	private void logout(TheClientDto theClientDto) {
		if(theClientDto != null) {
			try {
				logonCall.logout(theClientDto) ;
			} catch(NamingException e) {
				log.info("Ignored NamingException:", e) ;
			} catch(RemoteException e) {			
				log.info("Ignored RemoteException:", e) ;
			}
		}		
	}
	
	private Integer lock(Integer projectId) throws RemoteException, EJBExceptionLP, NamingException {
		judgeCall.addLock(HelperClient.LOCKME_PROJEKT, projectId);
		return projectId ;
	}
	
	private void unlock(Integer projectId) {
		if(projectId != null) {
			try {
				judgeCall.removeLock(HelperClient.LOCKME_PROJEKT, projectId);
			} catch (RemoteException e) {
				log.info("Ignored RemoteException:", e);
			} catch (EJBExceptionLP e) {
				log.info("Ignored EJBExceptionLP:", e);
			} catch (NamingException e) {
				log.info("Ignored NamingException:", e);
			}			
		}
	}
}
