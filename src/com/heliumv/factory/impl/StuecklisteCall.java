package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IStuecklisteCall;
import com.lp.server.stueckliste.service.StuecklisteDto;
import com.lp.server.stueckliste.service.StuecklisteFac;

public class StuecklisteCall extends BaseCall<StuecklisteFac> implements IStuecklisteCall {
	public StuecklisteCall() {
		super(StuecklisteFacBean) ;
	}

	@Override
	public StuecklisteDto stuecklisteFindByPrimaryKey(Integer stuecklisteId)
			throws RemoteException, NamingException {
		return getFac().stuecklisteFindByPrimaryKey(stuecklisteId, Globals.getTheClientDto());
	}	
}
