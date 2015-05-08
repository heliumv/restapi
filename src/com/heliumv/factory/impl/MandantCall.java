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
package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Locale;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IMandantCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.MandantDto;
import com.lp.server.system.service.MandantFac;
import com.lp.server.system.service.ModulberechtigungDto;
import com.lp.server.system.service.ZusatzfunktionberechtigungDto;
import com.lp.util.EJBExceptionLP;

public class MandantCall extends BaseCall<MandantFac> implements IMandantCall {
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
	private HashMap<String, String> moduls ;
	private HashMap<String, String> functions ;
	
	public MandantCall() {
		super(MandantFacBean) ;
	}

	public ModulberechtigungDto[] modulberechtigungFindByMandantCnr(String mandantCnr) throws NamingException {
		try {
			return getFac().modulberechtigungFindByMandantCNr(mandantCnr) ;
		} catch(RemoteException e) {			
		}
		
		return new ModulberechtigungDto[0] ;
	}

	public ZusatzfunktionberechtigungDto[] zusatzfunktionberechtigungFindByMandantCnr(String mandantCnr) throws NamingException  {
		try {
			return getFac().zusatzfunktionberechtigungFindByMandantCNr(mandantCnr) ;
		} catch(RemoteException e) {			
		}
		
		return new ZusatzfunktionberechtigungDto[0] ;
	}
	
	public void setModulBerechtigung(ModulberechtigungDto[] modulBerechtigungen) {
		moduls = new HashMap<String, String>() ;
		if(null == modulBerechtigungen) return ;
		
		for (int i = 0; i < modulBerechtigungen.length; i++) {
			moduls.put(modulBerechtigungen[i].getBelegartCNr().trim(),
					modulBerechtigungen[i].getBelegartCNr().trim()) ;
		}
	}

	public void setZusatzFunktionen(ZusatzfunktionberechtigungDto[] zusatzFunktionen) {		
		functions = new HashMap<String, String>() ;
		if(null == zusatzFunktionen) return ;
		
		for (int i = 0; i < zusatzFunktionen.length; i++) {
			functions.put(zusatzFunktionen[i].getZusatzfunktionCNr().trim(),
					zusatzFunktionen[i].getZusatzfunktionCNr().trim()) ;
		}
	}

	public boolean hasNamedModul(String moduleName) throws NamingException {
		return hasModul(moduleName, globalInfo.getTheClientDto().getMandant()) ;		
	}
	
	public boolean hasModulAngebot(String mandantCnr) throws NamingException {
		return hasModul(LocaleFac.BELEGART_ANGEBOT, mandantCnr) ;
	}

	@Override
	public boolean hasModulAngebot() throws NamingException {
		return hasModulAngebot(globalInfo.getTheClientDto().getMandant());
	}	
	
	@Override
	public boolean hasModulArtikel() throws NamingException {
		return hasModulArtikel(globalInfo.getTheClientDto().getMandant());
	}

	@Override
	public boolean hasModulArtikel(String mandantCnr) throws NamingException {
		return hasModul(LocaleFac.BELEGART_ARTIKEL, mandantCnr) ;
	}

	@Override
	public boolean hasModulAuftrag(String mandantCnr) throws NamingException {
		return hasModul(LocaleFac.BELEGART_AUFTRAG, mandantCnr) ;
	}

	public boolean hasModulAuftrag() throws NamingException {
		return hasModulAuftrag(globalInfo.getTheClientDto().getMandant()) ;
	}
	
	public boolean hasModulProjekt(String mandantCnr) throws NamingException {
		return hasModul(LocaleFac.BELEGART_PROJEKT, mandantCnr) ;
	}

	@Override
	public boolean hasModulProjekt() throws NamingException {
		return hasModulProjekt(globalInfo.getTheClientDto().getMandant()) ;
	}
	
	public boolean hasModulLos(String mandantCnr) throws NamingException {
		return hasModul(LocaleFac.BELEGART_LOS, mandantCnr) ;
	}	
	
	@Override
	public boolean hasModulLos() throws NamingException {
		return hasModulLos(globalInfo.getTheClientDto().getMandant());
	}
	
	public boolean hasFunctionProjektZeiterfassung(String mandantCnr) throws NamingException {
		return hasFunction(MandantFac.ZUSATZFUNKTION_PROJEKTZEITERFASSUNG, mandantCnr) ;
	}

	@Override
	public boolean hasFunctionProjektZeiterfassung() throws NamingException {
		return hasFunctionProjektZeiterfassung(globalInfo.getTheClientDto().getMandant());
	}
	
	public boolean hasFunctionAngebotsZeiterfassung(String mandantCnr) throws NamingException  {
		return hasFunction(MandantFac.ZUSATZFUNKTION_ANGEBOTSZEITERFASSUNG, mandantCnr) ;		
	}

	@Override
	public boolean hasFunctionAngebotsZeiterfassung() throws NamingException {
		return hasFunctionAngebotsZeiterfassung(globalInfo.getTheClientDto().getMandant());
	}
	
	public boolean hasFunctionZentralerArtikelstamm(String mandantCnr) throws NamingException {
		return hasFunction(MandantFac.ZUSATZFUNKTION_ZENTRALER_ARTIKELSTAMM, mandantCnr) ;
	}

	public boolean hasFunctionZentralerArtikelstamm() throws NamingException {
		return hasFunction(MandantFac.ZUSATZFUNKTION_ZENTRALER_ARTIKELSTAMM, globalInfo.getTheClientDto().getMandant()) ;
	}
	
	public Locale getLocaleDesHauptmandanten() throws NamingException, EJBExceptionLP {
		return getFac().getLocaleDesHauptmandanten() ;
	}
	
	public String getMandantEmailAddress() throws NamingException, RemoteException, EJBExceptionLP {
		MandantDto mandantDto = mandantFindByPrimaryKey() ;
		if(mandantDto.getPartnerDto() != null && 
			!StringHelper.isEmpty(mandantDto.getPartnerDto().getCEmail())) {
			return mandantDto.getPartnerDto().getCEmail() ;
		}			
	
		return null ;
	}
	
	public MandantDto mandantFindByPrimaryKey(String mandantCnr) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().mandantFindByPrimaryKey(mandantCnr, globalInfo.getTheClientDto()) ;
 	}
	
	public MandantDto mandantFindByPrimaryKey() throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().mandantFindByPrimaryKey(globalInfo.getMandant(), globalInfo.getTheClientDto()) ;
	}
	
	private boolean hasModul(String whichModul, String mandantCnr) throws NamingException  {
		if(moduls == null) {
			setModulBerechtigung(modulberechtigungFindByMandantCnr(mandantCnr)) ;
		}

		return moduls.containsKey(whichModul.trim()) ;
	}
	
	private boolean hasFunction(String whichFunction, String mandantCnr) throws NamingException {
		if(functions == null) {
			setZusatzFunktionen(zusatzfunktionberechtigungFindByMandantCnr(mandantCnr)) ;
		}
		
		return functions.containsKey(whichFunction.trim()) ;
	}
}
