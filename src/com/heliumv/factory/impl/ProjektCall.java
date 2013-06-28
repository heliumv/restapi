package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IProjektCall;
import com.lp.server.projekt.service.ProjektDto;
import com.lp.server.projekt.service.ProjektFac;

public class ProjektCall extends BaseCall<ProjektFac> implements IProjektCall {
	public ProjektCall() {
		super(ProjektFacBean) ;
	}

	@Override
	public ProjektDto projektFindByPrimaryKeyOhneExc(Integer projectId) throws NamingException, RemoteException {
		return getFac().projektFindByPrimaryKeyOhneExc(projectId) ;
	}
}
