package com.heliumv.api.system;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocalPingResult {
	/**
	 * Zu diesem Zeitpunkt wurde der Aufruf vom API Server registriert (Zeitstempel des API Servers)
	 */
	private long apiTime ;
	
	/**
	 * Die Build-Nummer des HELIUM V Servers
	 */
	private Integer apiBuildNumber ;
	
	/**
	 * Die HELIUM V Server Versionsnummer
	 */
	private String apiVersionNumber ;

	/**
	 * Liefert den Zeitstempel des API Servers
	 * @return
	 */
	public long getApiTime() {
		return apiTime;
	}

	public void setApiTime(long apiTime) {
		this.apiTime = apiTime;
	}

	/**
	 * Liefert die Build-Nummer des API Servers
	 * @return
	 */
	public Integer getApiBuildNumber() {
		return apiBuildNumber;
	}

	public void setApiBuildNumber(Integer apiBuildNumber) {
		this.apiBuildNumber = apiBuildNumber;
	}

	/**
	 * Liefert die Versionsnummer des API Servers
	 * @return
	 */
	public String getApiVersionNumber() {
		return apiVersionNumber;
	}

	public void setApiVersionNumber(String apiVersionNumber) {
		this.apiVersionNumber = apiVersionNumber;
	}
	
	public String toString() {
		return 
				"apiTime:" + getApiTime() + ", " +
				"apiBuildNumber:" + getApiBuildNumber() + ", " +
				"apiVersionNumber:" + getApiVersionNumber() + "." ;
	}
}
