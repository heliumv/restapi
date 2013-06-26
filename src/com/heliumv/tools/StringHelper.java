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
}
