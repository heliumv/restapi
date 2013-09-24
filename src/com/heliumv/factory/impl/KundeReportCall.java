package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IKundeReportCall;
import com.lp.server.partner.service.CustomerPricelistReportDto;
import com.lp.server.partner.service.KundeReportFac;
import com.lp.server.partner.service.KundenpreislisteParams;

public class KundeReportCall extends BaseCall<KundeReportFac> implements IKundeReportCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public KundeReportCall() {
		super(KundeReportFacBean) ;
	}
	
	@Override
	public CustomerPricelistReportDto getKundenpreisliste(KundenpreislisteParams params) throws NamingException, RemoteException {
//		getFac().printKundenpreisliste(params.getKundeId(), params.getArtikelgruppeId(),
//				params.getArtikelklasseId(), params.isMitInaktiven(), params.getItemCnrVon(),
//				params.getItemCnrBis(), params.isMitVersteckten(), params.getGueltigkeitsDatum(),
//				params.isNurSonderkonditionen(), params.isMitMandantensprache(), globalInfo.getTheClientDto()) ;
		return getFac().printKundenpreislisteRaw(params.getKundeId(), params.getArtikelgruppeId(),
				params.getArtikelklasseId(), params.isMitInaktiven(), params.getItemCnrVon(),
				params.getItemCnrBis(), params.isMitVersteckten(), params.getGueltigkeitsDatum(),
				params.isNurSonderkonditionen(), params.isMitMandantensprache(), params.isNurWebshop(), globalInfo.getTheClientDto()) ;
	}
}
