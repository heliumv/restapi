package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IPersonalApiCall;
import com.lp.server.personal.service.PersonalApiFac;


public class PersonalApiCall extends BaseCall<PersonalApiFac> implements IPersonalApiCall {

	public PersonalApiCall() {
		super(PersonalApiFacBean) ;
	}

	@Override
	public int bucheLosGroessenAenderung(String cSeriennummerLeser,
			String idUser, String station, String losCNr, Integer menge,
			String cAusweis) throws NamingException, RemoteException {
		return getFac().bucheLosGroessenAenderung(cSeriennummerLeser, idUser, station, losCNr, menge, cAusweis);
	}
	
	@Override
	public int bucheLosAblieferung(String cSeriennummerLeser, String idUser,
			String station, String losCNr, Integer menge, String cAusweis)
			throws NamingException, RemoteException {
		return getFac().bucheLosAblieferung(cSeriennummerLeser, idUser, station, losCNr, menge, cAusweis);
	}

	@Override
	public int bucheLosAblieferungSeriennummer(String idUser, String station,
		String losCNr, String artikelCNr, String cSeriennummer,
		String cVersion) throws NamingException, RemoteException {
		return getFac().bucheLosAblieferungSeriennummer(idUser, station, losCNr, artikelCNr, cSeriennummer, cVersion);
	}
}
