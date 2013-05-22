package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Locale;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.ILogonCall;
import com.lp.server.benutzer.service.LogonFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.Helper;

public class LogonCall extends BaseCall<LogonFac> implements ILogonCall {

	public LogonCall() throws NamingException {
		super(LogonFacBean) ;
	}
	
	public TheClientDto logon(String benutzer, char[] kennwort,
			Locale uILocale, String sMandantI) throws RemoteException {

		String logonCredential = benutzer ;
		int indexPipe = benutzer.indexOf("|") ;
		if(indexPipe > 0) {
			logonCredential = benutzer.substring(0, indexPipe) ;
		} 
		
		TheClientDto theClientDto = getFac().logon(
			benutzer, 
			Helper.getMD5Hash((logonCredential + new String(kennwort)).toCharArray()), 
			uILocale, sMandantI, null, new Timestamp(System.currentTimeMillis()));
		
		return theClientDto ;
	}
	

	public void logout(TheClientDto theClientDto) throws RemoteException {
		getFac().logout(theClientDto) ;
	}	
}
