package com.heliumv.api.benutzer;

import javax.ws.rs.core.Response;


public interface IUserApi {
	/**
	 * Ermoeglicht das Anmelden.
	 * 
	 * 
	 * @param logonEntry Es muessen gefuellt werden logonEntry.username und logonEntry.password
	 * Fehlt logonEntry.client wird der Hauptmandant verwendet
	 * Fehlt logonEntry.localeString wird die Sprache des Hauptmandanten verwendet
	 * @return null wenn die Anmeldung nicht erfolgreich war, ansonsten die Anmeldung
	 */
	LoggedOnEntry logon(LogonEntry logonEntry) ;
	
	Response logout(String token) ;
}
