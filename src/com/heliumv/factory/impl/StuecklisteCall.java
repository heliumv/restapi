package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IStuecklisteCall;
import com.lp.server.stueckliste.service.MontageartDto;
import com.lp.server.stueckliste.service.StuecklisteDto;
import com.lp.server.stueckliste.service.StuecklisteFac;
import com.lp.util.EJBExceptionLP;

public class StuecklisteCall extends BaseCall<StuecklisteFac> implements IStuecklisteCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public StuecklisteCall() {
		super(StuecklisteFacBean) ;
	}

	@Override
	public StuecklisteDto stuecklisteFindByPrimaryKey(Integer stuecklisteId)
			throws RemoteException, NamingException {
		return getFac().stuecklisteFindByPrimaryKey(stuecklisteId, Globals.getTheClientDto());
	}

	@Override
	public MontageartDto[] montageartFindByMandantCNr() throws RemoteException,
			NamingException, EJBExceptionLP {
		return getFac().montageartFindByMandantCNr(globalInfo.getTheClientDto()) ;
	}	
}
