package com.heliumv.factory.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IReservierungCall;
import com.lp.server.artikel.service.ReservierungFac;
import com.lp.util.EJBExceptionLP;

public class ReservierungCall extends BaseCall<ReservierungFac> implements
		IReservierungCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public ReservierungCall() {
		super(ReservierungFacBean) ;
	}

	@Override
	public BigDecimal getAnzahlReservierungen(Integer itemId)
			throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().getAnzahlReservierungen(itemId, globalInfo.getTheClientDto());
	}
}
