package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.partner.service.AnsprechpartnerDto;
import com.lp.util.EJBExceptionLP;

public interface IAnsprechpartnerCall {
	AnsprechpartnerDto ansprechpartnerFindByPrimaryKey(Integer ansprechpartnerId) 
			throws EJBExceptionLP, NamingException, RemoteException; 

	void updateAnsprechpartner(AnsprechpartnerDto ansprechpartnerDto) throws EJBExceptionLP, NamingException, RemoteException ;
}
