package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.ISystemCall;
import com.lp.server.system.service.SystemFac;

public class SystemCall extends BaseCall<SystemFac> implements ISystemCall {
	public SystemCall() {
		super(SystemFacBean) ;
	}

	@Override
	public String getHauptmandant() throws NamingException {
		return getFac().getHauptmandant() ;
	}

	@Override
	public String getServerVersion() throws NamingException {
		return getFac().getServerVersion() ;
	}

	@Override
	public Integer getServerBuildNumber() throws NamingException {
		return getFac().getServerBuildNumber() ;
	}

	@Override
	public Timestamp getServerTimestamp() throws RemoteException, NamingException {
		return getFac().getServerTimestamp() ;
	}
	
}
