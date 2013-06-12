package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IClientCall;
import com.lp.server.system.service.TheClientDto;
import com.lp.server.system.service.TheClientFac;
import com.lp.util.EJBExceptionLP;

public class ClientCall extends BaseCall<TheClientFac>  implements IClientCall {

	public ClientCall() {
		super(TheClientFacBean) ;
	}

	public TheClientDto theClientFindByUserLoggedIn(String token) {
		try {
			return getFac().theClientFindByUserLoggedIn(token) ;
		} catch(EJBExceptionLP e) {
//			e.printStackTrace() ;
		} catch(RemoteException e) {
//			e.printStackTrace() ;
		} catch(NamingException e) {
		}

		return null ;
	}
}
