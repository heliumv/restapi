package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IGlobalInfo;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.ArtikelFac;

public class ArtikelCall extends BaseCall<ArtikelFac> implements IArtikelCall {
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public ArtikelCall() {
		super(ArtikelFacBean) ;
	}

	@Override
	public ArtikelDto artikelFindByCNrOhneExc(String cNr) throws NamingException, RemoteException {
		return getFac().artikelFindByCNr(cNr, globalInfo.getTheClientDto());
	}
}
