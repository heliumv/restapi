package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.lp.server.auftrag.service.AuftragDto;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public interface IAuftragCall {
	AuftragDto auftragFindByPrimaryKeyOhneExc(Integer orderId) throws NamingException ;
	
	AuftragDto auftragFindByCnr(String cnr) throws NamingException, RemoteException ;

	AuftragDto auftragFindByCnr(String cnr, TheClientDto theClientDto) throws NamingException, RemoteException ;
	
	String createOrderResponseToString(AuftragDto auftragDto) throws NamingException, RemoteException ;	

	String createOrderResponseToString(AuftragDto auftragDto, TheClientDto theClientDto) 
			throws NamingException, RemoteException ;	

	String createOrderResponsePost(AuftragDto auftragDto) throws RemoteException, NamingException, EJBExceptionLP ;

	String createOrderResponsePost(
			AuftragDto auftragDto, TheClientDto theClientDto) throws RemoteException, NamingException, EJBExceptionLP ;	
}
