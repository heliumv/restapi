package com.heliumv.api.benutzer;

import javax.ws.rs.core.Response;


public interface IUserApi {	
	/**
	 * Ermoeglicht das Anmelden als Helium V Benutzer an einem Helium V Server
	 * 
	 * @param cnrUser ist die Benutzerkennung ("werner")
	 * @param password ist das dem Benutzer zugeordnete Kennwort ("kennwort")
	 * @param mandant ist der zu verwendende Helium Mandant ("001")
	 * @param locale ist die zu benutzende Lokale ("deAT", "deCH", "enDE")
	 * @return ein Token das fuer weiterfuehrende Aufrufe verwendbar ist.
	 */
	Response logon(String cnrUser, String password, String mandant, String locale) ;
	
	Response logout(String token) ;
	
	String logonId(String username, String password, String mandant, String localeString) ;
}
