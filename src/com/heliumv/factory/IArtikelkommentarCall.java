package com.heliumv.factory;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.lp.server.artikel.service.ArtikelkommentarDto;

public interface IArtikelkommentarCall {
//	ArtikelkommentarDto artikelkommentarFindByPrimaryKey(Integer iId) ;
	List<ArtikelkommentarDto> artikelkommentarFindByArtikelIId(
			Integer artikelIId) throws RemoteException, NamingException ;
}
