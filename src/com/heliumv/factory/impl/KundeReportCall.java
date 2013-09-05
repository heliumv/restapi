package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IKundeReportCall;
import com.heliumv.factory.KundenpreislisteParams;
import com.lp.server.partner.service.KundeReportFac;

public class KundeReportCall extends BaseCall<KundeReportFac> implements IKundeReportCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public KundeReportCall() {
		super(KundeReportFacBean) ;
	}
	
	@Override
	public void printKundenpreisliste(KundenpreislisteParams params) throws NamingException, RemoteException {
		getFac().printKundenpreisliste(params.getKundeId(), params.getArtikelgruppeId(),
				params.getArtikelklasseId(), params.isMitInaktiven(), params.getItemCnrVon(),
				params.getItemCnrBis(), params.isMitVersteckten(), params.getGueltigkeitsDatum(),
				params.isNurSonderkonditionen(), params.isMitMandantensprache(), globalInfo.getTheClientDto()) ;
	}
	
//	void printKundenpreisliste(Integer kundeId, Integer artikelgruppeId, Integer artikelklasseId, 
//			String itemCnrVon, String itemCnrBis, boolean mitVersteckten, Date gueltigkeitsDatum,
//			boolean nurSonderkonditionen, boolean mitMandantensprache) throws NamingException, RemoteException {
//		getFac().printKundenpreisliste(kundeId, artikelgruppeId,
//				artikelklasseId, true, itemCnrVon, itemCnrBis, mitVersteckten, 
//				gueltigkeitsDatum, nurSonderkonditionen, mitMandantensprache, globalInfo.getTheClientDto()) ;
//	}	
}
