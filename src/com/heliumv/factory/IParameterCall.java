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
package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.util.EJBExceptionLP;

public interface IParameterCall {
	
	boolean isZeitdatenAufErledigteBuchbar() throws NamingException, RemoteException ;
	boolean isZeitdatenAufAngelegteLoseBuchbar() throws NamingException, RemoteException ;

	boolean isPartnerSucheWildcardBeidseitig() throws NamingException, RemoteException ;

	/**
	 * Wird die Materialbuchung automatisch durchgefuehrt?
	 * 
	 * @return true wenn keine automatische Materialbuchung durchgefuehrt wird
	 */
	boolean isKeineAutomatischeMaterialbuchung() throws NamingException, RemoteException ;

	boolean isBeiLosErledigenMaterialNachbuchen() throws NamingException, RemoteException ;

	/**
	 * Die maximal erlaubte Laenge einer Artikelnummer
	 * 
	 * @return die maximal erlaubte Laenge einer Artikelnummer
	 * @throws RemoteException
	 */
	int getMaximaleLaengeArtikelnummer()  throws NamingException, RemoteException ;
	
	/**
	 * Im Direktfilter nach Artikelgruppe/Klasse anstatt Referenznummer suchen
	 * 
	 * @return true wenn die Gruppe bzw. Klasse anstatt der Referenznummer verwendet wird
	 * @throws NamingException
	 * @throws RemoteException
	 */
	boolean isArtikelDirektfilterGruppeKlasseStattReferenznummer() throws NamingException, RemoteException ;
	
	Integer getGeschaeftsjahr() throws NamingException, RemoteException, EJBExceptionLP ;
	
	Integer getGeschaeftsjahr(String mandantCNr) throws NamingException, RemoteException, EJBExceptionLP ;

	String getMailAdresseAdmin() throws NamingException, RemoteException, EJBExceptionLP ;

}
