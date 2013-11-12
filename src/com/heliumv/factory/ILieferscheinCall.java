package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.lieferschein.service.LieferscheinDto;
import com.lp.server.system.service.TheClientDto;

public interface ILieferscheinCall {
	LieferscheinDto lieferscheinFindByPrimaryKey(Integer lieferscheinId) throws NamingException, RemoteException ;
	
	LieferscheinDto lieferscheinFindByPrimaryKey(
			Integer lieferscheinId, TheClientDto theClientDto) throws NamingException, RemoteException ;

	LieferscheinDto lieferscheinFindByCNr(String cnr) throws NamingException, RemoteException ;

	LieferscheinDto lieferscheinFindByCNr(String cnr, String clientCnr) throws NamingException, RemoteException ;
	
	String createLieferscheinAviso(
			LieferscheinDto lieferscheinDto, TheClientDto theClientDto) throws NamingException, RemoteException ;
	void sendLieferscheinAviso(
			LieferscheinDto lieferscheinDto, TheClientDto theClientDto) throws NamingException, RemoteException ;
}
