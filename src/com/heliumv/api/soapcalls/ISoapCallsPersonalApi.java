package com.heliumv.api.soapcalls;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.util.EJBExceptionLP;

public interface ISoapCallsPersonalApi {
	/**
	 * Eine Losgr&ouml;&szlig;en&auml;nderung durchf&uuml;hren
	 * 
	 * @param userId die Id vom Logon
	 * @param serialNrReader die (optionale) Seriennummer des (Barcode)Lesers
	 * @param station die (optionale) Station des (Barcode)Lesers
	 * @param productionCnr die Losnummer
	 * @param amount die neue Gr&ouml;&szlig;e des Loses
	 * @param idCard die Ausweisnummer jenes Mitarbeiters der die Buchung durchf&uuml;hrt
	 * 
	 * @return resultCode kann </br>
	 * -5 ... Ablieferungen sind schon mehr als die angegebene Menge,</br> 
	 * -4 ... der Ausweis ist unbekannt,</br>
	 * -2 ... das Los ist storniert/bereits erledigt/noch nicht ausgegeben,</br>
	 * -1 ... das Los ist nicht vorhanden,</br>
	 * 1 ... Programmfehler 
	 * enthalten
	 * 
	 * @throws NamingException
	 * @throws RemoteException
	 * @throws EJBExceptionLP
	 */
	SoapCallPersonalResult bucheLosGroessenAenderung(String userId,
			String serialNrReader,
			String station,
			String productionCnr,
			Integer amount,
			String idCard) throws NamingException, RemoteException, EJBExceptionLP ;

	/**
	 * Eine Losablieferung durchf&uuml;hren
	 *  
	 * @param idUser die Id vom Logon
	 * @param serialNrReader die (optionale) Seriennummer des (Barcode)Lesers
	 * @param station die (optionale) Station des (Barcode)Lesers
	 * @param productionCnr die Losnummer
	 * @param menge die Menge der Ablieferung
	 * @param cAusweis die Ausweisnummer jenes Mitarbeiters der die Buchung durchf&uuml;hrt
	 * @return resultCode kann die Werte </br>
	 * -4 ... Ausweis unbekannt/keine Bereichtigung, 
	 * -3 die Sollsatzgr&ouml;&szlig;e ist &uuml;berschritten, </br>
	 * -2 auf das Los ist keine Buchung erlaubt, </br> 
	 * -1 ... Los nicht vorhanden,</br> 
	 * 1 ... Programmfehler </br> 
	 * enthalten
	 * @throws NamingException
	 * @throws RemoteException
	 */
	SoapCallPersonalResult bucheLosAblieferung(String idUser, String serialNrReader, 
			String station, String productionCnr, Integer menge, String cAusweis)  throws NamingException, RemoteException;
	
	/** 
	 * Eine Losablieferung f&uuml;r einen Artikel mit Seriennummer durchf&uuml;hren
	 * 
	 * @param idUser die Id vom Logon
	 * @param station  die (optionale) Station des (Barcode)Lesers
	 * @param productionCnr die Losnummer
	 * @param itemCnr die Artikelnummer
	 * @param identity die Seriennumer
	 * @param version die Zusatzinformation (Version) zur Seriennummer
	 * @return resultCode kann die Werte </br>
	 * -6 ... Die ermittelte St&uuml;ckliste ist im Los unbekannt
	 * -5 ... Es gibt keine St&uuml;ckliste f&uuml;r den Artikel 
	 * -4 ... Ausweis unbekannt/keine Bereichtigung, 
	 * -3 die Sollsatzgr&ouml;&szlig;e ist unterschritten, </br>
	 * -2 auf das Los ist keine Buchung erlaubt, </br> 
	 * -1 ... Los nicht vorhanden,</br> 
	 * 1 ... Programmfehler </br> 
	 * enthalten
	 * @throws NamingException
	 * @throws RemoteException
	 */
	SoapCallPersonalResult bucheLosAblieferungSeriennummer(String idUser,
			String station, String productionCnr, String itemCnr, String identity, String version)  throws NamingException, RemoteException;	
}