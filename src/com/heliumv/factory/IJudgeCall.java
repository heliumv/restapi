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

	boolean hasFertDarfLosErledigen() throws NamingException ;
	boolean hasFertDarfLosErledigen(TheClientDto theClientDto) throws NamingException ;

	/**
	 * Darf ein Los erzeugt(Create)/geändert(Update)/gelöscht(Delete) werden?
	 * @return
	 * @throws NamingException
	 */
	boolean hasFertLosCUD() throws NamingException ;
}
