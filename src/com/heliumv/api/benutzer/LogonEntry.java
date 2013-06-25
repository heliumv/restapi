package com.heliumv.api.benutzer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogonEntry {
	private String username ;
	private String password ;
	private String client ;
	private String localeString ;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
}
