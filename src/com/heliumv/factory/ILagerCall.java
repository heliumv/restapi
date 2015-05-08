/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
package com.heliumv.factory;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.heliumv.factory.legacy.AllLagerEntry;
import com.lp.server.artikel.service.LagerDto;
import com.lp.server.artikel.service.SeriennrChargennrMitMengeDto;
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

	/**
	 * Ermittelt ein Lager auf Grund seiner Nummer
	 * 
	 * @param lagerCnr die Lagernummer
	 * @return 
	 * @throws NamingException
	 * @throws RemoteException
	 */
	LagerDto lagerFindByCnrOhnExc(String lagerCnr) throws NamingException, RemoteException ;
	
	BigDecimal getLagerstandOhneExc(Integer itemId, Integer lagerIId)
			throws NamingException, RemoteException;

	boolean hatRolleBerechtigungAufLager(Integer lagerIId)
			throws NamingException;
	
	List<AllLagerEntry> getAllLager() throws NamingException, RemoteException, EJBExceptionLP ; 

	BigDecimal getLagerstandsVeraenderungOhneInventurbuchungen(
			Integer artikelIId, Integer lagerIId, java.sql.Timestamp tVon,
			java.sql.Timestamp tBis) throws NamingException, RemoteException ; 
	
	BigDecimal getLagerstandAllerLagerEinesMandanten(Integer itemId, Boolean mitKonsignationsLager) throws NamingException, RemoteException ;
	BigDecimal getPaternosterLagerstand(Integer itemId) throws NamingException, RemoteException ;
	
	List<SeriennrChargennrMitMengeDto> getAllSeriennrchargennrEinerBelegartposition(
			String belegartCNr, Integer belegartpositionIId) throws NamingException, RemoteException ;
	
	List<SeriennrChargennrMitMengeDto> getAllSeriennrchargennrEinerBelegartpositionUeberHibernate(
			String belegartCNr, Integer belegartpositionIId) throws NamingException, RemoteException ;
	
	void bucheZu(String belegartCNr, Integer belegartIId,
			Integer belegartpositionIId, Integer artikelIId,
			BigDecimal fMengeAbsolut, BigDecimal nEinstansdpreis,
			Integer lagerIId,
			List<SeriennrChargennrMitMengeDto> alSeriennrchargennr,
			java.sql.Timestamp tBelegdatum) throws NamingException, RemoteException ;
	
	BigDecimal getEinstandspreis(String belegartCNr,
			Integer belegartpositionIId, String cSeriennrChargennr)
			throws NamingException, RemoteException, EJBExceptionLP ;
		
}
