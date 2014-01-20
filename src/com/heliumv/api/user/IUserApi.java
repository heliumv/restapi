package com.heliumv.api.user;

import javax.ws.rs.core.Response;


public interface IUserApi {
	/**
	 * Erm&ouml;glicht das Anmelden.
	 * 
	 * 
	 * @param logonEntry Pflichtfelder sind username und password.
	 * Fehlt logonEntry.client wird der Hauptmandant verwendet
	 * Fehlt logonEntry.localeString wird die Sprache des Hauptmandanten verwendet
	 * @return null wenn die Anmeldung nicht erfolgreich war, ansonsten die Anmeldung
	 */
	LoggedOnEntry logon(LogonEntry logonEntry) ;
	
	/**
	 * Erm&ouml;glicht das Abmelden.</br>
	 * 
	 * @param token wurde zuvor von einem "logon" ermittelt.
	 * 
	 * @return
	 */
	Response logout(String token) ;
}
