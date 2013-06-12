package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IMandantCall;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.MandantFac;
import com.lp.server.system.service.ModulberechtigungDto;
import com.lp.server.system.service.ZusatzfunktionberechtigungDto;

public class MandantCall extends BaseCall<MandantFac> implements IMandantCall {
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

	public boolean hasModulAngebot(String mandantCnr) throws NamingException {
		return hasModul(LocaleFac.BELEGART_ANGEBOT, mandantCnr) ;
	}

	public boolean hasModulAuftrag(String mandantCnr) throws NamingException {
		return hasModul(LocaleFac.BELEGART_AUFTRAG, mandantCnr) ;
	}
	
	public boolean hasModulProjekt(String mandantCnr) throws NamingException {
		return hasModul(LocaleFac.BELEGART_PROJEKT, mandantCnr) ;
	}
	
	public boolean hasModulLos(String mandantCnr) throws NamingException {
		return hasModul(LocaleFac.BELEGART_LOS, mandantCnr) ;
	}	
	
	public boolean hasFunctionProjektZeiterfassung(String mandantCnr) throws NamingException {
		return hasFunction(MandantFac.ZUSATZFUNKTION_PROJEKTZEITERFASSUNG, mandantCnr) ;
	}

	public boolean hasFunctionAngebotsZeiterfassung(String mandantCnr) throws NamingException  {
		return hasFunction(MandantFac.ZUSATZFUNKTION_ANGEBOTSZEITERFASSUNG, mandantCnr) ;		
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
