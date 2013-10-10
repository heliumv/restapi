package com.heliumv.factory;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.lp.server.partner.service.KundeDto;

public interface IKundeCall {
	List<KundeDto> kundeFindByKbezMandantCnr(String kbez) throws RemoteException, NamingException ;
	KundeDto kundeFindByPrimaryKeyOhneExc(Integer customerId) throws RemoteException, NamingException ;
}
