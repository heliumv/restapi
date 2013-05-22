package com.heliumv.factory;

import com.lp.server.system.service.ModulberechtigungDto;
import com.lp.server.system.service.ZusatzfunktionberechtigungDto;

public interface IMandantCall {
	ModulberechtigungDto[] modulberechtigungFindByMandantCnr(String mandantCnr) ;

	ZusatzfunktionberechtigungDto[] zusatzfunktionberechtigungFindByMandantCnr(String mandantCnr) ;
	
	boolean hasModulAngebot(String mandantCnr) ;

	boolean hasModulAuftrag(String mandantCnr) ;
	
	boolean hasModulProjekt(String mandantCnr) ;
	
	boolean hasModulLos(String mandantCnr) ;
	
	boolean hasFunctionProjektZeiterfassung(String mandantCnr) ;

	boolean hasFunctionAngebotsZeiterfassung(String mandantCnr) ;
	
}
