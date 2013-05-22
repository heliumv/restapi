package com.heliumv.factory;

import com.lp.server.system.service.TheClientDto;

public interface IClientCall {

	/**
	 * Das ClientDto zum angegebenen Token finden
	 * 
	 * @param token das fuer das entsprechende TheClientDto steht
	 * @return null wenn es keine Entsprechung gibt, ansonsten TheClientDto
	 */
	TheClientDto theClientFindByUserLoggedIn(String token);
}
