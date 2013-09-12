package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.partner.service.CustomerPricelistReportDto;
import com.lp.server.partner.service.KundenpreislisteParams;

public interface IKundeReportCall {
	public CustomerPricelistReportDto getKundenpreisliste(KundenpreislisteParams params) throws NamingException, RemoteException ;
}
