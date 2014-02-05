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
