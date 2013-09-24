package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.artikel.service.ArtgruDto;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.ArtklaDto;

public interface IArtikelCall {
	/**
	 * Liefert den angeforderten ARtikel unter Beruecksichtigung des Zentralen Artikelstamm
	 * @param cNr die gewuenschte Artikelnummer
	 * @return null wenn nicht existiert, ansonsten den Artikel
	 */
	ArtikelDto artikelFindByCNrOhneExc(String cNr) throws NamingException, RemoteException ;
	
	ArtikelDto artikelFindByPrimaryKeySmallOhneExc(Integer itemId) throws NamingException, RemoteException  ;
	
	ArtgruDto artikelgruppeFindByPrimaryKeyOhneExc(Integer artikelgruppeId) throws NamingException, RemoteException ;
	
	ArtgruDto artikelgruppeFindByCnrOhneExc(String artikelgruppeCnr) throws NamingException, RemoteException ;	
	
	ArtklaDto artikelklasseFindByPrimaryKeyOhneExc(Integer artikelklasseId) throws NamingException, RemoteException ;
	
	ArtklaDto artikelklasseFindByCnrOhneExc(String artikelklasseCnr) throws NamingException, RemoteException ;
}
