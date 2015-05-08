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

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public interface IJudgeCall {
	
	boolean hatRecht(String rechtCnr) throws NamingException ;
	
	boolean hasPersSichtbarkeitAlle() throws NamingException ;
	boolean hasPersSichtbarkeitAlle(TheClientDto theClientDto) throws NamingException ;
	
	boolean hasPersSichtbarkeitAbteilung() throws NamingException ;
	boolean hasPersSichtbarkeitAbteilung(TheClientDto theClientDto) throws NamingException ;
	
	boolean hasPersZeiteingabeNurBuchen() throws NamingException ;
	boolean hasPersZeiteingabeNurBuchen(TheClientDto theClientDto) throws NamingException ;

	boolean hasPersDarfKommtGehtAendern() throws NamingException ;
	boolean hasPersDarfKommtGehtAendern(TheClientDto theClientDto) throws NamingException ;
	
	boolean hasFertDarfLosErledigen() throws NamingException ;
	boolean hasFertDarfLosErledigen(TheClientDto theClientDto) throws NamingException ;

	/**
	 * Darf ein Los erzeugt(Create)/ge&auml;ndert(Update)/gel&ouml;scht(Delete) werden?
	 * @return
	 * @throws NamingException
	 */
	boolean hasFertLosCUD() throws NamingException ;
	boolean hasFertLosCUD(TheClientDto theClientDto) throws NamingException ;

	/**
	 * Darf f&uuml;r ein Los Sollmaterial erzeugt/ge&auml;ndert/gel&ouml;scht werden?
	 * @return
	 * @throws NamingException
	 */
	boolean hasFertDarfSollmaterialCUD() throws NamingException ;
	boolean hasFertDarfSollmaterialCUD(TheClientDto theClientDto) throws NamingException ;

	/**
	 * Darf Istmaterial nachtr&auml;glich gebucht werden?
	 * 
	 * @return
	 * @throws NamingException
	 */
	boolean hasFertDarfIstmaterialManuellNachbuchen() throws NamingException ;
	boolean hasFertDarfIstmaterialManuellNachbuchen(TheClientDto theClientDto) throws NamingException ;
	
	void addLock(String lockedTable, Integer id) throws NamingException, RemoteException, EJBExceptionLP ;	
	void removeLock(String lockedTable, Integer id) throws NamingException, RemoteException, EJBExceptionLP ;	
}
