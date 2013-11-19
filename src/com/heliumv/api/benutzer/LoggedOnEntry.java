package com.heliumv.api.benutzer;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Repraesentiert die Antwort eines erfolgreichen Logons
 * @author Gerold
 */
@XmlRootElement
public class LoggedOnEntry {
	private String token ;
	private String client ;
	private String localeString ;
	
	/**
	 * Der vom HELIUM V System ermittelte "token". Sp&auml;ter auch als "userId"
	 * referenziert.
	 * @return
	 */
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Der Mandant in dem sich der Benutzer angemeldet hat, bzw. vom System
	 * angemeldet wurde.
	 * @return
	 */
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * Die Locale ("deAT", "deDE", ...) die der Anmeldung zugewiesen worden ist
	 * @return
	 */
	public String getLocaleString() {
		return localeString;
	}

	public void setLocaleString(String localeString) {
		this.localeString = localeString;
	}	
}
