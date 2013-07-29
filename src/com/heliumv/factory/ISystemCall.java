package com.heliumv.factory;

import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.naming.NamingException;

public interface ISystemCall {
	String getHauptmandant() throws NamingException ;
	String getServerVersion() throws NamingException ;	
	Integer getServerBuildNumber() throws NamingException ;
	Timestamp getServerTimestamp() throws RemoteException, NamingException ; 	
}
