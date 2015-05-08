package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.system.service.VersandauftragDto;
import com.lp.util.EJBExceptionLP;

public interface IVersandCall {
	VersandauftragDto createVersandAuftrag(
			VersandauftragDto versandauftragDto) throws RemoteException, NamingException, EJBExceptionLP ;

	VersandauftragDto createVersandAuftrag(
			VersandauftragDto versandauftragDto, boolean dokumentAnhaengen) throws RemoteException, NamingException, EJBExceptionLP ;
}
