package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.stueckliste.service.MontageartDto;
import com.lp.server.stueckliste.service.StuecklisteDto;
import com.lp.util.EJBExceptionLP;

public interface IStuecklisteCall {
	StuecklisteDto stuecklisteFindByPrimaryKey(Integer stuecklisteId) throws RemoteException, NamingException ;
	
	MontageartDto[] montageartFindByMandantCNr() throws RemoteException, NamingException, EJBExceptionLP ;
}
