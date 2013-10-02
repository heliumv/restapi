package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IGlobalInfo;
import com.lp.server.artikel.service.ArtgruDto;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.artikel.service.ArtklaDto;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.LocaleFac;
import com.lp.util.EJBExceptionLP;

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
	
	public ArtikelDto artikelFindByPrimaryKeySmallOhneExc(Integer itemId) throws NamingException, RemoteException {
		return getFac().artikelFindByPrimaryKeySmallOhneExc(itemId, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public ArtgruDto artikelgruppeFindByPrimaryKeyOhneExc(Integer artikelgruppeId) throws NamingException, RemoteException {
		try {
			return getFac().artgruFindByPrimaryKey(artikelgruppeId, globalInfo.getTheClientDto()) ;
		} catch(EJBExceptionLP e) {
			return null ;
		}
	}
	
	@Override
	public ArtgruDto artikelgruppeFindByCnrOhneExc(String artikelgruppeCnr) throws NamingException, RemoteException {
		try {
			List<ArtgruDto> artikelgruppeDtos = getFac().artgruFindByMandantCNrSpr(globalInfo.getTheClientDto()) ;
			for (ArtgruDto artgruDto : artikelgruppeDtos) {
				if(artgruDto.getCNr().equals(artikelgruppeCnr)) return artgruDto ;
			}
			
			return null ;
		} catch(EJBExceptionLP e) {
			return null ;
		}
	}
	
	
	@Override
	public List<ArtgruDto> artikelgruppeFindByMandantCNr() throws NamingException,
			RemoteException {
		return getFac().artgruFindByMandantCNrSpr(globalInfo.getTheClientDto());
	}

	@Override
	public ArtklaDto artikelklasseFindByPrimaryKeyOhneExc(Integer artikelklasseId) throws NamingException, RemoteException {
		try {
			return getFac().artklaFindByPrimaryKey(artikelklasseId, globalInfo.getTheClientDto()) ;
		} catch(EJBExceptionLP e) {
			return null ;
		}
	}
	
	@Override
	public ArtklaDto artikelklasseFindByCnrOhneExc(String artikelklasseCnr) throws NamingException, RemoteException {
		try {
			ArtklaDto[] artikelklasseDtos = getFac().artklaFindByMandantCNr(globalInfo.getTheClientDto()) ;
			for (ArtklaDto artklaDto : artikelklasseDtos) {
				if(artklaDto.getCNr().equals(artikelklasseCnr)) return artklaDto ;
			}
			
			return null ;
		} catch(EJBExceptionLP e) {
			return null ;
		}
	}	
}
