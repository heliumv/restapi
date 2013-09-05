package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

public interface IKundeReportCall {
	void printKundenpreisliste(KundenpreislisteParams params) throws NamingException, RemoteException ;
}
