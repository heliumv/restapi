package com.heliumv.api.order;

import java.util.List;

public interface IOrderApi {
	
	/**
	 * Eine Liste aller Auftr&auml;ge liefer den den Filterkriterien entsprechen
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
}
