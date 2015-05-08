package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IVersandCall;
import com.lp.server.system.service.VersandFac;
import com.lp.server.system.service.VersandauftragDto;
import com.lp.util.EJBExceptionLP;

public class VersandCall extends BaseCall<VersandFac> implements IVersandCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public VersandCall() {
		super(VersandFacBean) ;
	}
	
	public VersandauftragDto createVersandAuftrag(
			VersandauftragDto versandauftragDto) throws RemoteException, NamingException, EJBExceptionLP {
		return getFac().createVersandauftrag(versandauftragDto, false, globalInfo.getTheClientDto()) ;
	}

	public VersandauftragDto createVersandAuftrag(
			VersandauftragDto versandauftragDto, boolean dokumentAnhaengen) throws RemoteException, NamingException, EJBExceptionLP {
		return getFac().createVersandauftrag(versandauftragDto, dokumentAnhaengen, globalInfo.getTheClientDto()) ;
	}
}
