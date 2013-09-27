package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.system.service.PaneldatenDto;
import com.lp.util.EJBExceptionLP;

public interface IPanelCall {

	PaneldatenDto[] paneldatenFindByPanelCNrCKey(String panelCNr,
			String cKey) throws RemoteException, NamingException, EJBExceptionLP ;
}
