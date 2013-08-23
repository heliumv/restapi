package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.artikel.service.InventurDto;
import com.lp.server.artikel.service.InventurlisteDto;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public interface IInventurCall {	
	Integer createInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge, TheClientDto theClientDto)
			throws NamingException, RemoteException, EJBExceptionLP ;

	Integer createInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge) throws NamingException, RemoteException, EJBExceptionLP ;
	
	/**
	 * Alle offenen Inventuren des Mandanten ermitteln
	 * 
	 * @param mandantCNr
	 * @return ein (leeres) Array von offenen Inventuren
	 * @throws EJBExceptionLP
	 */
	InventurDto[] inventurFindOffene(String mandantCNr) throws NamingException, EJBExceptionLP ;

	InventurDto[] inventurFindOffene() throws NamingException, EJBExceptionLP ;
	
	InventurDto inventurFindByPrimaryKey(Integer inventurId) throws NamingException, RemoteException ;
}
