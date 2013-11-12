package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.auftrag.service.CreateOrderResult;
import com.lp.server.system.service.WebshopAuthHeader;

public interface IAuftragRestCall {
	CreateOrderResult createOrder(WebshopAuthHeader header,  String xmlOpenTransOrder)  throws NamingException, RemoteException ;
}
