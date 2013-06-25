package com.heliumv.factory.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.ILagerCall;
import com.lp.server.artikel.service.LagerDto;
import com.lp.server.artikel.service.LagerFac;

public class LagerCall extends BaseCall<LagerFac> implements ILagerCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public LagerCall() {
		super(LagerFacBean) ;
	}
	
	@Override
	public BigDecimal  getGemittelterGestehungspreisEinesLagers(
			Integer itemId, Integer lagerId) throws NamingException, RemoteException {
		return getFac().getGemittelterGestehungspreisEinesLagers(itemId, lagerId, globalInfo.getTheClientDto()) ;
	}

	@Override
	public LagerDto lagerFindByPrimaryKeyOhneExc(Integer lagerIId) throws NamingException {
		LagerDto lagerDto = getFac().lagerFindByPrimaryKeyOhneExc(lagerIId) ;
		if(lagerDto != null) {
			if(!globalInfo.getTheClientDto().getMandant().equals(lagerDto.getMandantCNr())) {
				lagerDto = null ;
			}
		}

		return lagerDto ;
	}

	@Override
	public BigDecimal getLagerstandOhneExc(Integer itemId, Integer lagerIId)
			throws NamingException, RemoteException {
		return getFac().getLagerstandOhneExc(itemId, lagerIId, globalInfo.getTheClientDto());
	}
	
}
