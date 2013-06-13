package com.heliumv.factory;

import javax.naming.NamingException;

import com.lp.server.system.service.TheClientDto;

public interface IJudgeCall {
	boolean hasPersSichtbarkeitAlle() throws NamingException ;
	boolean hasPersSichtbarkeitAlle(TheClientDto theClientDto) throws NamingException ;
	
	boolean hasPersSichtbarkeitAbteilung() throws NamingException ;
	boolean hasPersSichtbarkeitAbteilung(TheClientDto theClientDto) throws NamingException ;
	
	boolean hasPersZeiteingabeNurBuchen(TheClientDto theClientDto) throws NamingException ;
	boolean hasFertDarfLosErledigen(TheClientDto theClientDto) throws NamingException ;
}
