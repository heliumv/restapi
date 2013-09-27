package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IPanelCall;
import com.lp.server.system.service.PanelFac;
import com.lp.server.system.service.PaneldatenDto;
import com.lp.util.EJBExceptionLP;

public class PanelCall extends BaseCall<PanelFac> implements IPanelCall {
	public PanelCall() {
		super(PanelFacBean) ;
	}

	@Override
	public PaneldatenDto[] paneldatenFindByPanelCNrCKey(String panelCNr,
			String cKey) throws RemoteException, NamingException,
			EJBExceptionLP {
		return getFac().paneldatenFindByPanelCNrCKey(panelCNr, cKey) ;
	}
}
