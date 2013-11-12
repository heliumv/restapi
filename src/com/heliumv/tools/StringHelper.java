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
