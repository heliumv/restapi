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
package com.heliumv.api.production;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.lp.util.EJBExceptionLP;

public interface IProductionApi {
	/**
	 * Die Liste der den Filterkriterien entsprechenden Lose</p>
	 * 
	 * @param userId
	 *            ist der beim Logon ermittelte "token"
	 * @param limit
	 *            die maximale Anzahl von Eintr&auml;gen die ermittelt werden
	 *            sollen
	 * @param startIndex
	 *            der StartIndex
	 * @param filterCnr
	 *            die zu filternde Losnummer
	 * @param filterCustomer
	 *            der zu filternde Kundenname
	 * @param filterProject
	 *            der zu filternde Projektname
	 * @param filterItemCnr
	 *            die zu filternde Artikelnummer
	 * @param filterWithHidden
	 *            mit <code>true</code> werden auch versteckte Auftr&auml;ge
	 *            geliefert
	 * @return eine (leere) Liste von Losen entsprechend den Filterkriterien
	 */
	List<ProductionEntry> getProductions(String userId, Integer limit,
			Integer startIndex, String filterCnr, String filterCustomer,
			String filterProject, String filterItemCnr, Boolean filterWithHidden);

	/**
	 * Eine Materialentnahme nachtr&auml;glich durchf&uuml;hren</br>
	 * <p>
	 * Im Gegensatz zum HELIUM V Client d&uuml;rfen nur Materialien
	 * zur&uuml;ckgegeben werden, die auch tats&auml;chlich ausgegeben worden
	 * sind.
	 * </p>
	 * 
	 * @param headerUserId
	 *            ist der beim Logon ermittelte Token. (optional)
	 * @param materialEntry
	 *            sind die Daten zur Materialentnahme wie Artikelnummer, Menge,
	 *            Lager
	 * @param userId
	 *            ist der beim Logon ermittelte "token"
	 */
	void bucheMaterialNachtraeglichVomLagerAb(String headerUserId,
			MaterialWithdrawalEntry materialEntry, String userId);

	/**
	 * Eine Liste aller offenen Arbeitsg&auml;nge ermitteln</br>
	 * 
	 * @param userId  ist der beim Logon ermittelte "token"
	 * @param limit die maximale Anzahl von Eintr&auml;gen
	 * @param startIndex
	 * @param filterCnr
	 * @param filterCustomer
	 * @param filterItemCnr
	 * @return eine (leere) Liste aller offenen Arbeitsg&auml;nge
	 * @throws RemoteException
	 * @throws NamingException
	 * @throws EJBExceptionLP
	 */
	OpenWorkEntryList getOpenWorkEntries(String userId, Integer limit,
			Integer startIndex, String filterCnr, String filterCustomer,
			String filterItemCnr,
			Long startDateMs,
			Long endDateMs) throws RemoteException, NamingException, EJBExceptionLP;

	OpenWorkEntryList getOpenWorkEntriesImpl(Integer limit,
			Integer startIndex, Long startDateMs, Long endDateMs) throws NamingException, RemoteException ;
	
	/**
	 * Einen offen Arbeitsgang modifizieren</br>
	 * <p>Es kann die Resource (Maschine), die Startzeit und der Tagesversatz abge&auml;ndert werden.
	 * 
	 * @param headerUserId ist der (optionale) beim Logon ermittelte "Token"
	 * @param updateEntry
	 * @param userId ist der beim Logon ermittelte "Token". Entweder headerUserId oder userId muss gesetzt sein
	 * @throws NamingException
	 * @throws RemoteException
	 * @throws EJBExceptionLP
	 */
	void updateOpenWorkEntry(
			String headerUserId,
			OpenWorkUpdateEntry updateEntry,
			String userId
	) throws NamingException, RemoteException, EJBExceptionLP ;
	
	void updateOpenWorkEntryList(
			String headerUserId,
			OpenWorkUpdateEntryList updateList,
			String userId
	) throws NamingException, RemoteException, EJBExceptionLP ;		
}
