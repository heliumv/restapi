package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IGlobalInfo;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.LocaleFac;

public class ArtikelCall extends BaseCall<ArtikelFac> implements IArtikelCall {
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public ArtikelCall() {
		super(ArtikelFacBean) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_ARTIKEL)
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_R)
	public ArtikelDto artikelFindByCNrOhneExc(String cNr) throws NamingException, RemoteException {
		return getFac().artikelFindByCNrOhneExc(cNr, globalInfo.getTheClientDto());
	}
}
