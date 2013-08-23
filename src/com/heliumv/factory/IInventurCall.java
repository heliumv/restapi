package com.heliumv.factory;

import javax.naming.NamingException;

import com.lp.server.artikel.service.InventurDto;
import com.lp.util.EJBExceptionLP;

public interface IInventurCall {
	/**
	 * Alle offenen Inventuren des Mandanten ermitteln
	 * 
	 * @param mandantCNr
	 * @return ein (leeres) Array von offenen Inventuren
	 * @throws EJBExceptionLP
	 */
	InventurDto[] inventurFindOffene(String mandantCNr) throws NamingException, EJBExceptionLP ;

	InventurDto[] inventurFindOffene() throws NamingException, EJBExceptionLP ;
}
