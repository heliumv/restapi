package com.heliumv.api.internal;

import java.rmi.RemoteException;

import javax.naming.NamingException;

public interface ICruisecontrolApi {

	/**
	 * Die Build-Nummer aus dem Cruisecontrol build in das Projekt schreiben
	 * 
	 * @param userid die (optionale) HELIUM V userid/token
	 * @param message die Commit-Message
	 * @param buildLabel die Buildnummer
	 */
	void setBuildNumber(String userid, String message, String buildLabel) ;
	
	/**
	 * Die Build-Nummer aus dem Cruisecontrol deploy in das Projekt schreiben
	 * 
	 * @param userid die (optionale) HELIUM V userid/token
	 * @param message die Commit-Message
	 * @param buildLabel die Buildnummer
	 */
	void setDeployNumber(String userid, String message, String buildLabel) ;
	
	/**
	 * Die Build-Nummer aus dem Jenkins build in das Projekt schreiben
	 * 
	 * @param userid die (optionale) HELIUM V userid/token
	 * @param message die Commit-Message
	 * @param buildLabel die Buildnummer
	 */
	void setBuildNumberJenkins(String userid, String buildLabel) throws NamingException ;
	
	/**
	 * Die Build-Nummer aus dem Jenkins deploy in das Projekt schreiben
	 * 
	 * @param userid die (optionale) HELIUM V userid/token
	 * @param message die Commit-Message
	 * @param buildLabel die Buildnummer
	 */
	void setDeployNumberJenkins(String userid, String buildLabel) throws NamingException ;

	void callJCR(String userid, Integer count) throws RemoteException, NamingException, Exception ; 
}
