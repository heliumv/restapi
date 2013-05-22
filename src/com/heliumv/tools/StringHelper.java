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
}
