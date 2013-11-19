package com.heliumv.api.benutzer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogonEntry {
	private String username ;
	private String password ;
	private String client ;
	private String localeString ;
	
	/**
	 * Das Kennwort f&uuml;r den Benutzer
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Der (optionale) gew&uuml;nschte Mandant.</br>
	 * <p>Wird der Mandant nicht angegeben, wird
	 * der innerhalb HELIUM V festgelegte Default-Mandant f&uuml;r diesen Benutzer
	 * verwendet</p>
	 * @return
	 */
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	
	/**
	 * Die (optionale) Landes/Sprachdefinition</br>
	 * <p>Die ersten beiden Zeichen entsprechen der Sprache ("de", "en", ...),
	 * die folgenden beiden Zeichen entsprechen dem Land ("AT", "DE", "IT, ...)
	 * </p>
	 * <p>Es k&ouml;nnen nur Sprachen bzw. L&auml;nder ausgew&auml;hlt werden, die
	 * auf dem HELIUM V System zur Verf&uuml;gung stehen</p> 
	 * @return
	 */
	public String getLocaleString() {
		return localeString;
	}
	public void setLocaleString(String localeString) {
		this.localeString = localeString;
	}
	
	/**
	 * Der Benutzername</br>
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
}
