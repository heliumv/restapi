package com.heliumv.factory;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.heliumv.factory.legacy.PaneldatenPair;
import com.lp.server.system.service.PaneldatenDto;
import com.lp.util.EJBExceptionLP;

public interface IPanelCall {

	PaneldatenDto[] paneldatenFindByPanelCNrCKey(String panelCNr,
			String cKey) throws RemoteException, NamingException, EJBExceptionLP ;
	
	/**
	 * Eine Liste aller Eigenschaften mitsamt der Beschreibung holen
	 * 
	 * @param panelCNr
	 * @param cKey
	 * @return eine (leere) Liste von Eigenschaften mit Beschreibung
	 * @throws RemoteException
	 * @throws NamingException
	 * @throws EJBExceptionLP
	 */
	List<PaneldatenPair> paneldatenFindByPanelCNrCKeyBeschreibung(String panelCNr, String cKey) 
			throws RemoteException, NamingException, EJBExceptionLP ;	
}
