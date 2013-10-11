package com.heliumv.factory.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IFehlmengeCall;
import com.heliumv.factory.IGlobalInfo;
import com.lp.server.artikel.service.FehlmengeFac;
import com.lp.util.EJBExceptionLP;

public class FehlmengeCall extends BaseCall<FehlmengeFac> implements
		IFehlmengeCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public FehlmengeCall() {
		super(FehlmengeFacBean) ;
	}

	@Override
	public BigDecimal getAnzahlFehlmengeEinesArtikels(Integer itemId)
			throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().getAnzahlFehlmengeEinesArtikels(itemId, globalInfo.getTheClientDto());
	}	
}
