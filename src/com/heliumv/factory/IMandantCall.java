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

import java.util.Locale;

import javax.naming.NamingException;

import com.lp.server.system.service.ModulberechtigungDto;
import com.lp.server.system.service.ZusatzfunktionberechtigungDto;
import com.lp.util.EJBExceptionLP;

public interface IMandantCall {
	
	Locale getLocaleDesHauptmandanten() throws NamingException, EJBExceptionLP ;
	
	ModulberechtigungDto[] modulberechtigungFindByMandantCnr(String mandantCnr) throws NamingException ;

	ZusatzfunktionberechtigungDto[] zusatzfunktionberechtigungFindByMandantCnr(String mandantCnr) throws NamingException ;
	
	boolean hasModulAngebot() throws NamingException ;
	boolean hasModulAngebot(String mandantCnr) throws NamingException ;

	boolean hasModulArtikel() throws NamingException ;
	boolean hasModulArtikel(String mandantCnr) throws NamingException ;
	
	boolean hasModulAuftrag() throws NamingException ;
	boolean hasModulAuftrag(String mandantCnr) throws NamingException ;
	
	boolean hasModulProjekt() throws NamingException ;
	boolean hasModulProjekt(String mandantCnr) throws NamingException ;
	
	boolean hasModulLos() throws NamingException ;
	boolean hasModulLos(String mandantCnr) throws NamingException ;
	
	/**
	 * Existiert ein Modul fuer den Mandanten?</br>
	 * <p><b>Bitte diese Funktion nur benutzen wenn es unbedingt notwendig ist. Besser ist es,
	 * die speziellen Methoden wie hasModulProjekt()...hasModulAngebot()... zu benutzen</b></p>
	 * 
	 * @param moduleName ist der betreffende Modulname aus LocaleFac.*
	 * 
	 * @return true wenn der angemeldete Client Zugriff auf das Modul hat
	 * @throws NamingException
	 */
	boolean hasNamedModul(String moduleName) throws NamingException ;
	
	boolean hasFunctionProjektZeiterfassung() throws NamingException ;
	boolean hasFunctionProjektZeiterfassung(String mandantCnr) throws NamingException ;

	boolean hasFunctionAngebotsZeiterfassung() throws NamingException ;
	boolean hasFunctionAngebotsZeiterfassung(String mandantCnr) throws NamingException ;	
	
	boolean hasFunctionZentralerArtikelstamm() throws NamingException ;
	boolean hasFunctionZentralerArtikelstamm(String mandantCnr) throws NamingException ;	
}
