package com.heliumv.api.cc;


public interface IClevercureApi {
	/**
	 * Die CC Daten sind im Request-Body direkt enthalten
	 * 
	 * @param companyCode
	 * @param token
	 * @param user
	 * @param password
	 * @param datatype
	 * @param ccdata
	 */
	void receiveAnyCCData(String companyCode, String token,
			/* String user, String password, */ String datatype, String ccdata) ;

	/**
	 * Die CC Daten werden im POST als Multipart Body angeliefert
	 * 
	 * @param companyCode
	 * @param token
	 * @param user
	 * @param password
	 * @param datatype
	 * @param ccdata
	 */
	void receiveAnyCCDataMultipart(String companyCode, String token,
			/* String user, String password, */ String datatype, String ccdata) ;
	
	/**
	 * Ein Lieferaviso erzeugen
	 * 
	 * @param userId
	 * @param deliveryId
	 * @param deliveryCnr
	 * @return
	 */
	String createDispatchNotification(String userId, Integer deliveryId, String deliveryCnr) ;

	String createAndTransmitDispatchNotification(String userId, Integer deliveryId, String deliveryCnr) ;	
}
