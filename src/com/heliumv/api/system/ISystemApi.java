package com.heliumv.api.system;

/**
 * Systeminformationen abfragen
 * 
 * @author Gerold
 */
public interface ISystemApi {
	/**
	 * Prüft, ob vom API-Server ein Zugriff auf den HELIUM V Server möglich ist.</b>
	 * <p>Liefert zusätzlich Informationen über den verbundenen HELIUM V Server</p>
	 *  
	 * @return verschiedene Informationen über das System
	 */
	PingResult ping() ;

	/**
	 * Prüft, ob ein Zugriff auf den API-Server möglich ist.</b>
	 * <p>Liefert zusätzlich Informationen über den API Server</p>
	 *  
	 * @return verschiedene Informationen über das System
	 */
	LocalPingResult localping() ;
}
