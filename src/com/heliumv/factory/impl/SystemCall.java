package com.heliumv.factory.impl;

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
	
}
