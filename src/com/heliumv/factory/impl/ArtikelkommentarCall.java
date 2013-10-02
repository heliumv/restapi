package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IArtikelkommentarCall;
import com.heliumv.factory.IGlobalInfo;
import com.lp.server.artikel.service.ArtikelkommentarDto;
import com.lp.server.artikel.service.ArtikelkommentarFac;

public class ArtikelkommentarCall extends BaseCall<ArtikelkommentarFac> implements
		IArtikelkommentarCall {

	@Autowired
	private IGlobalInfo globalInfo ;
	
	public ArtikelkommentarCall() {
		super(ArtikelkommentarFacBean) ;
	}
	
	@Override
	public List<ArtikelkommentarDto> artikelkommentarFindByArtikelIId(
			Integer artikelIId) throws RemoteException, NamingException {
		ArtikelkommentarDto[] dtos = getFac()
				.artikelkommentarFindByArtikelIId(artikelIId, globalInfo.getTheClientDto()) ;
		
		return Arrays.<ArtikelkommentarDto>asList(dtos);
	}
}
