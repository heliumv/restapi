package com.heliumv.factory;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.heliumv.factory.legacy.AllLagerEntry;
import com.lp.server.artikel.service.LagerDto;
import com.lp.util.EJBExceptionLP;

public interface ILagerCall {
	/**
	 * Sucht im Lager nach der Seriennummer. Zuerst im Zugang, dann im Abgang
	 * 
	 * @param serialnumber ist die gesuchte Eineindeutige Seriennummer
	 * @return null wenn es die seriennummer nicht gibt, ansonsten die Artikel-IId
	 * @throws NamingException
	 * @throws RemoteException
	 */
	Integer artikelIdFindBySeriennummerOhneExc(String serialnumber) throws NamingException, RemoteException ;	

	BigDecimal getGemittelterGestehungspreisEinesLagers(
			Integer itemId, Integer lagerId) throws NamingException, RemoteException;

	LagerDto lagerFindByPrimaryKeyOhneExc(Integer lagerIId)
			throws NamingException;

	BigDecimal getLagerstandOhneExc(Integer itemId, Integer lagerIId)
			throws NamingException, RemoteException;

	boolean hatRolleBerechtigungAufLager(Integer lagerIId)
			throws NamingException;
	
	List<AllLagerEntry> getAllLager() throws NamingException, RemoteException, EJBExceptionLP ; 

	BigDecimal getLagerstandsVeraenderungOhneInventurbuchungen(
			Integer artikelIId, Integer lagerIId, java.sql.Timestamp tVon,
			java.sql.Timestamp tBis) throws NamingException, RemoteException ; 

}
