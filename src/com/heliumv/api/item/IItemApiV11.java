package com.heliumv.api.item;


public interface IItemApiV11 extends IItemApi {
	/**
	 * Liefert eine Liste aller Lagerst&auml;nde dieses Artikels</br>
	 * <p>Es werden nur Lager geliefert, die einen Lagerstand > 0 haben. Es werden nur jene
	 * Lagerst&auml;nde geliefert, f&uuml;r die der Benutzer das Recht hat das jeweilige Lager zu benutzen.</p>
	 * 
	 * @param userId der angemeldete API Benutzer
	 * @param itemCnr die gesuchte Artikelnummer
	 * @param returnItemInfo mit <code>true</code> werden neben den Lagerst&auml;nden auch die Daten des
	 * betreffenden Artikels zur&uuml;ckgeliefert.
	 * @return eine (leere) Liste von Lagerst&auml;nden
	 */
	StockAmountEntryList getStockAmountList(String userId, String itemCnr, Boolean returnItemInfo) ;	
}
