package com.heliumv.api.order;

import java.util.List;

public interface IOrderApi {
	
	/**
	 * Eine Liste aller Auftr&auml;ge liefern, die den Filterkriterien entsprechen
	 * @param userId ist der beim Logon ermittelte "token"
	 * @param limit die maximale Anzahl von Eintr&auml;gen die ermittelt werden sollen
	 * @param startIndex der StartIndex 
	 * @param filterCnr die zu filternde Auftragsnummer
	 * @param filterCustomer der zu filternde Kundenname
	 * @param filterProject der zu filternde Projektname des Auftrags
	 * @param filterWithHidden mit <code>true</code> werden auch versteckte Auftr&auml;ge geliefert
	 * @return
	 */
	List<OrderEntry> getOrders(
		String userId, Integer limit, Integer startIndex, String filterCnr,
		String filterCustomer, String filterProject, Boolean filterWithHidden) ;
	
	/**
	 * Die Liste der Positionen f&uuml;r den angegeben Auftrag ermitteln
	 * @param orderId ist die Auftrag-Id f&uuml;r die die Positionen ermittelt werden sollen
	 * @param userId ist der beim Logon ermittelte "token"
	 * @param limit ist die maximale Anzahl von zu liefernden Positionsdaten. Default 50
	 * @param startIndex ist der Index ab dem die Positionen geliefert werden sollen
	 * @return
	 */
	List<OrderpositionEntry> getPositions(
			Integer orderId,
			String userId,
			Integer limit,
			Integer startIndex) ;
	
	/**
	 * Eine Liste aller Positionen die den Filterkriterien f&uuml;r Auftr&auml;ge entsprechen ermitteln
	 * 
	 * @param userId ist der beim Logon ermittelte "token"
	 * @param limit die maximale Anzahl von zu liefernden Auftr&auml;gen
	 * @param startIndex der Startindex der Auftr&auml;ge
	 * @param filterCnr die zu filternde Auftragsnummer
	 * @param filterCustomer der zu filternde Kundenname
	 * @param filterProject der zu filternde Projektname des Auftrags
	 * @param filterWithHidden mit <code>true</code> werden auch versteckte Auftr&auml;ge geliefert
	 * @return eine Liste von <code>OrderpositionsEntry</code>
	 */
	List<OrderpositionsEntry> getOrderPositions(
			String userId,
			Integer limit,
			Integer startIndex,
			String filterCnr,
			String filterCustomer, 
			String filterProject,
			Boolean filterWithHidden) ;
	
	/**
	 * Liefert die Auftragsdaten f&uuml;r eine sp&auml;tere Offline-Verarbeitung<br>
	 * 
	 * <p>Es werden s&auml;mtliche den Kritieren entsprechenden Auftr&auml;ge geliefert.
	 * Zus&auml;tzlich die Positionen f&uuml;r dieser Auftr&auml;ge und die unterschiedlichen
	 * Lieferadressen.</p>
	 * 
	 * @param headerUserId ist der beim Logon ermittelte Token. (optional) 
	 * @param userId ist der beim Logon ermittelte "token"
	 * @param limit die maximale Anzahl von zu liefernden Auftr&auml;gen
	 * @param startIndex der Startindex der Auftr&auml;ge
	 * @param filterCnr die zu filternde Auftragsnummer
	 * @param filterCustomer der zu filternde Kundenname des Auftraggebers
	 * @param filterProject der zu filternde Projektname des Auftrags
	 * @param filterDeliveryCustomer der zu filternde Kundename des Empf&auml;ngers
	 * @param filterWithHidden mit <code>true</code> werden auch versteckte Auftr&auml;ge geliefert
	 * @return eine Liste von <code>OfflineOrderEntry</code>
	 */
	OfflineOrderEntry getOfflineOrders(
			String headerUserId,
			String userId,
			Integer limit,
			Integer startIndex,
			String filterCnr,
			String filterCustomer, 
			String filterDeliveryCustomer, 
			String filterProject,
			Boolean filterWithHidden) ;
	
	/**
	 * Die Auftragskommentare ermitteln<br>
	 * <p>Einer der Parameter <code>orderId</code> oder <code>orderCnr</code> muss angegeben werden.</p>
	 * 
	 * @param headerToken ist der beim Logon ermittelte Token
	 * @param orderId ist die (optionale) Id des Auftrags
	 * @param orderCnr ist die (optionale) Auftragsnummer
	 * @param userId ist der beim Logon ermittelte "token"
	 * @param addInternalComment wenn true oder nicht angegeben wird der interne Kommentar ermittelt
	 * @param addExternalComment wenn true oder nicht angegeben wird der externe Kommentar ermittelt
	 * @return
	 */
	OrderComments getOrderComments (
			String headerToken,
			String userId,
			Integer orderId,
			String orderCnr,
			Boolean addInternalComment,
			Boolean addExternalComment) ;	
}
