package com.heliumv.api.inventory;

import java.math.BigDecimal;
import java.util.List;

import com.heliumv.api.item.InventoryEntry;

public interface IInventoryApi {
	List<InventoryEntry> getOpenInventories(String userId) ;
	
	/**
	 * Einen Eintrag in der Inventurliste erzeugen
	 * 
	 * @param userId
	 * @param inventoryId die betreffende Inventur
	 * @param itemId der Artikel fuer den dies gilt
	 * @param amount die Menge des Artikels
	 */
	void createInventoryEntry(
			String userId, Integer inventoryId, Integer itemId, BigDecimal amount, Boolean largeDifference) ;
	
	void updateInventoryEntry(
			String userId, Integer inventoryId, Integer itemId, BigDecimal amount,
			Boolean changeAmountTo) ;
	
	void createInventoryDataEntry(
			String userId, Integer inventoryId, 
			InventoryDataEntry inventoryEntry, Boolean largeDifference) ;	
}
