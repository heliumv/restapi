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
