package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.stueckliste.service.StuecklisteDto;

public interface IStuecklisteCall {
	StuecklisteDto stuecklisteFindByPrimaryKey(Integer stuecklisteId) throws RemoteException, NamingException ;
}
