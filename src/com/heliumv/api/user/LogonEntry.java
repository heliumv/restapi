/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
package com.heliumv.api.user;

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
