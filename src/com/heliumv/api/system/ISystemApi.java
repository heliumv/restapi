package com.heliumv.api.system;

/**
 * Systeminformationen abfragen
 * 
 * @author Gerold
 */
public interface ISystemApi {
	/**
	 * Prüft, ob vom API-Server ein Zugriff auf den HELIUM V Server möglich ist.
	 *  
	 * @return verschiedene Informationen über das System
	 */
	PingResult ping() ;
}
