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
package com.heliumv.tools;

public class StringHelper {

	/**
	 * Ist der String null oder leer (trim() liefert Laenge 0
	 * 
	 * @param value ist der zu pruefende String
	 * @return true wenn value entweder null oder ein leer-String ist
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0 ;
	}

	/**
	 * Hat der String einen Inhalt (nicht null und trim() liefert Laenge > 0)
	 * 
	 * @param value ist der zu pruefende String
	 * @return true wenn value einen Inhalt hat
	 */
	public static boolean hasContent(String value) {
		return value != null && value.trim().length() > 0 ;
	}
	
	/**
	 * Poor Mans SQL Delimiters entfernen
	 * 
	 * Einfache Anfuehrungszeichen entfernen
	 * 
	 * @param value
	 * @return
	 */
	public static String removeSqlDelimiters(String value) {
		return value.trim().replace("'", "") ;
	}
	
	
	public static String removeXssDelimiters(String value) {
		if(value == null) return null ;

		String s = value.trim().replace("<", "") ;
		s = s.replace(">", "") ;
		s = s.replace("&", "") ;
		return s ;
	}
	
	/**
	 * Poor Mans SQL "String" Darstellung erzeugen
	 * 
	 * @param value
	 * @return den um Hochkomma erweiterten String
	 */
	public static String asSqlString(String value) {
		return "'" + value + "'" ;
	}

	/**
	 * Einen String trim(), null-String wird dabei ignoriert 
	 * @param value
	 * @return null wenn null, ansonsten value.trim()
	 */
	public static String trim(String value) {
		return value != null ? value.trim() : null ;
	}
}
