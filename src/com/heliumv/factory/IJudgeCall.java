package com.heliumv.factory;

import javax.naming.NamingException;

import com.lp.server.system.service.TheClientDto;

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
	 * Darf ein Los erzeugt(Create)/geändert(Update)/gelöscht(Delete) werden?
	 * @return
	 * @throws NamingException
	 */
	boolean hasFertLosCUD() throws NamingException ;
	boolean hasFertLosCUD(TheClientDto theClientDto) throws NamingException ;

	/**
	 * Darf für ein Los Sollmaterial erzeugt/geändert/gelöscht werden?
	 * @return
	 * @throws NamingException
	 */
	boolean hasFertDarfSollmaterialCUD() throws NamingException ;
	boolean hasFertDarfSollmaterialCUD(TheClientDto theClientDto) throws NamingException ;

	/**
	 * Darf Istmaterial nachträglich gebucht werden?
	 * 
	 * @return
	 * @throws NamingException
	 */
	boolean hasFertDarfIstmaterialManuellNachbuchen() throws NamingException ;
	boolean hasFertDarfIstmaterialManuellNachbuchen(TheClientDto theClientDto) throws NamingException ;
}
