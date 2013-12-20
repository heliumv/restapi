package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.lieferschein.service.ILieferscheinAviso;
import com.lp.server.lieferschein.service.LieferscheinDto;
import com.lp.server.system.service.TheClientDto;

public interface ILieferscheinCall {
	LieferscheinDto lieferscheinFindByPrimaryKey(Integer lieferscheinId) throws NamingException, RemoteException ;
	
	LieferscheinDto lieferscheinFindByPrimaryKey(
			Integer lieferscheinId, TheClientDto theClientDto) throws NamingException, RemoteException ;

	LieferscheinDto lieferscheinFindByCNr(String cnr) throws NamingException, RemoteException ;

	LieferscheinDto lieferscheinFindByCNr(String cnr, String clientCnr) throws NamingException, RemoteException ;
	
	/**
	 * Ein LieferscheinAviso erzeugen
	 *
	 * @param lieferscheinDto
	 * @param theClientDto
	 * @return
	 * @throws NamingException
	 * @throws RemoteException
	 */
	ILieferscheinAviso createLieferscheinAviso(
			Integer lieferscheinId, TheClientDto theClientDto) throws NamingException, RemoteException ;

	/**
	 * Ein Aviso zu einem String-Content transformieren
	 *
	 * @param lieferscheinDto
	 * @param lieferscheinAviso
	 * @param theClientDto
	 * @return
	 * @throws NamingException
	 * @throws RemoteException
	 */
	String getLieferscheinAvisoAsString(
			LieferscheinDto lieferscheinDto, ILieferscheinAviso lieferscheinAviso,
			TheClientDto theClientDto) throws NamingException, RemoteException ;

	/**
	 * Ein Lieferscheinaviso erzeugen und als transformierten String zurueckliefern
	 * 
	 * @param lieferscheinDto
	 * @param theClientDto
	 * @return
	 * @throws RemoteException
	 * @throws NamingException
	 */
	String createLieferscheinAvisoToString(
			Integer lieferscheinId, TheClientDto theClientDto) throws RemoteException, NamingException ;
	
	/**
	 * Ein LieferscheinAviso erzeugen und versenden 
	 * 
	 * @param lieferscheinDto
	 * @param theClientDto
	 * @return den Aviso-Inhalt als String
	 * @throws RemoteException
	 * @throws NamingException
	 */
	String createLieferscheinAvisoPost(
			Integer lieferscheinId, TheClientDto theClientDto) throws RemoteException, NamingException ;	
}
