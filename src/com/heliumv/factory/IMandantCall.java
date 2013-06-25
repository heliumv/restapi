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

	boolean hasModulAuftrag() throws NamingException ;
	boolean hasModulAuftrag(String mandantCnr) throws NamingException ;
	
	boolean hasModulProjekt() throws NamingException ;
	boolean hasModulProjekt(String mandantCnr) throws NamingException ;
	
	boolean hasModulLos() throws NamingException ;
	boolean hasModulLos(String mandantCnr) throws NamingException ;
	
	boolean hasFunctionProjektZeiterfassung() throws NamingException ;
	boolean hasFunctionProjektZeiterfassung(String mandantCnr) throws NamingException ;

	boolean hasFunctionAngebotsZeiterfassung() throws NamingException ;
	boolean hasFunctionAngebotsZeiterfassung(String mandantCnr) throws NamingException ;	
	
	boolean hasFunctionZentralerArtikelstamm() throws NamingException ;
	boolean hasFunctionZentralerArtikelstamm(String mandantCnr) throws NamingException ;	
}
