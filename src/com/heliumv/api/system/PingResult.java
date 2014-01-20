package com.heliumv.api.system;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/**
 * Die Eigenschaften eines Ping-Ergebnisses</br>
 * 
 * @author Gerold
 */
public class PingResult {
	/**
	 * Zu diesem Zeitpunkt wurde der Aufruf vom HELIUM V Server registriert (Zeitstempel des Helium Servers)
	 */
	private long serverTime ;

	private long serverDuration ;
	
	/**
	 * Zu diesem Zeitpunkt wurde der Aufruf vom API Server registriert (Zeitstempel des API Servers)
	 */
	private long apiTime ;
	
	/**
	 * Die Build-Nummer des HELIUM V Servers
	 */
	private Integer serverBuildNumber ;
	
	/**
	 * Die HELIUM V Server Versionsnummer
	 */
	private String serverVersionNumber ;
	
	
	/**
	 * Zeitpunkt im ms (seit 1.1.1970) am API Server
	 * 
	 * @return den Zeitpunkt zu dem der Aufruf vom API Server registriert wurde (Zeitstempel des API Servers)
	 */
	public long getApiTime() {
		return apiTime;
	}
	public void setApiTime(long apiTime) {
		this.apiTime = apiTime;
	}
	
	/**
	 * Zeitpunkt im ms (seit 1.1.1970) am HELIUMV Server
	 * 
	 * @return den Zeitpunkt zu dem der Aufruf vom HELIUM V Server registriert wurde (Zeitstempel des HELIUM V Servers)
	 */
	public long getServerTime() {
		return serverTime;
	}
	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}
	
	/**
	 * HELIUM V Server Build Nummer
	 * @return die Server Build-Nummer (Beispiel: "7641")
	 */
	public Integer getServerBuildNumber() {
		return serverBuildNumber;
	}
	public void setServerBuildNumber(Integer serverBuildNumber) {
		this.serverBuildNumber = serverBuildNumber;
	}

	/**
	 * HELIUM V Server Version Nummer
	 * @return die Server Version Nummer
	 */
	public String getServerVersionNumber() {
		return serverVersionNumber;
	}

	public void setServerVersionNumber(String serverVersionNumber) {
		this.serverVersionNumber = serverVersionNumber;
	}
	
	/**
	 * Die Zeitspanne in ms die ben&ouml;tigt wurde, um eine Verbindung zum HELIUM V Server aufzunehmen und
	 * die Serverinformationen zu erhalten
	 * @return
	 */
	public long getServerDuration() {
		return serverDuration;
	}
	public void setServerDuration(long serverDuration) {
		this.serverDuration = serverDuration;
	}
	
	public String toString() {
		return 
				"apiTime:" + getApiTime() + ", " +
				"serverTime:" + getServerTime() + ", " +
				"serverBuildNumber:" + getServerBuildNumber() + ", " +
				"serverVersionNumber:" + getServerVersionNumber() + ", " + 
				"serverDuration:" + getServerDuration() + "." ;		
	}
}
