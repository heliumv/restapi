package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IAuftragRestCall;
import com.lp.server.auftrag.service.CreateOrderResult;
import com.lp.server.auftrag.service.WebshopOrderServiceInterface;
import com.lp.server.system.service.WebshopAuthHeader;

public class AuftragCleverCureCall extends BaseCall<WebshopOrderServiceInterface> implements
		IAuftragRestCall {

	public AuftragCleverCureCall() {
		super(AuftragFacBeanCC) ;
	}
	
	@Override
	public CreateOrderResult createOrder(WebshopAuthHeader header,
			String xmlOpenTransOrder) throws NamingException, RemoteException {
		return getFac().createOrder(header, xmlOpenTransOrder);
	}
}
