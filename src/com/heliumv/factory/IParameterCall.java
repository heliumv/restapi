package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

public interface IParameterCall {
	
	boolean isZeitdatenAufErledigteBuchbar() throws NamingException, RemoteException ;
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
	 * @return
	 * @throws NamingException
	 * @throws RemoteException
	 */
	boolean isArtikelDirektfilterGruppeKlasseStattReferenznummer() throws NamingException, RemoteException ;
}
