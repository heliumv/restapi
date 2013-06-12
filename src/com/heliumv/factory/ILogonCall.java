package com.heliumv.factory;

import java.rmi.RemoteException;
import java.util.Locale;

import javax.naming.NamingException;

import com.lp.server.system.service.TheClientDto;

public interface ILogonCall {
	
	 public TheClientDto logon(String benutzer, char[] kennwort, Locale uILocale, String sMandantI) throws NamingException, RemoteException ;
	
	 public void logout(TheClientDto theClientDto) throws NamingException, RemoteException ;

}
