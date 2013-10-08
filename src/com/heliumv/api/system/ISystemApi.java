package com.heliumv.api.system;

/**
 * Systeminformationen abfragen
 * 
 * @author Gerold
 */
public interface ISystemApi {
	/**
	 * Pr&uuml;ft, ob vom API-Server ein Zugriff auf den HELIUM V Server m&ouml;glich ist.</b>
	 * <p>Liefert zus&auml;tzlich Informationen &uuml;ber den verbundenen HELIUM V Server</p>
	 *  
	 * @return verschiedene Informationen &uuml;ber das System
	 */
	PingResult ping() ;

	/**
	 * Pr&uuml;ft, ob ein Zugriff auf den API-Server m&ouml;glich ist.</b>
	 * <p>Liefert zus&auml;tzlich Informationen &uuml;ber den API Server</p>
	 *  
	 * @return verschiedene Informationen &uuml;ber das System
	 */
	LocalPingResult localping() ;
}
