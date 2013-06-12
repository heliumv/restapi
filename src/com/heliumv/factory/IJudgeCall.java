package com.heliumv.factory;

import javax.naming.NamingException;

import com.lp.server.system.service.TheClientDto;

public interface IJudgeCall {
	boolean hasPersZeiteingabeNurBuchen(TheClientDto theClientDto) throws NamingException ;
	boolean hasFertDarfLosErledigen(TheClientDto theClientDto) throws NamingException ;
}
