package com.heliumv.api.item;

import java.util.List;


public interface IItemApi {
	ItemEntry findItemByCnr(String userId, String cnr) ;

	/**
	 * Liefert eine Liste aller Lagerstaende dieses Artikels</br>
	 * Es werden nur Lager geliefert, die einen Lagerstand (> 0) haben. </br>
	 * Der angemeldete Benutzer muss das Recht haben, dieses Lager zu benutzen.
	 * 
	 * @param userId
	 * @param itemCnr
	 * @return
	 */
	List<StockAmountEntry> getStockAmount(String userId, String itemCnr) ;	
}
