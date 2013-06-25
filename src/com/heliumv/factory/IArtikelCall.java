package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.artikel.service.ArtikelDto;

public interface IArtikelCall {
	/**
	 * Liefert den angeforderten ARtikel unter Beruecksichtigung des Zentralen Artikelstamm
	 * @param cNr die gewuenschte Artikelnummer
	 * @return null wenn nicht existiert, ansonsten den Artikel
	 */
	ArtikelDto artikelFindByCNrOhneExc(String cNr) throws NamingException, RemoteException ;
}
