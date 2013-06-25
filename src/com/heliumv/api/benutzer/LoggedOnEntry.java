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
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getLocaleString() {
		return localeString;
	}

	public void setLocaleString(String localeString) {
		this.localeString = localeString;
	}	
}
