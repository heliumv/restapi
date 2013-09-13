package com.heliumv.api.inventory;

import java.math.BigDecimal;
import java.util.List;

import com.heliumv.api.item.InventoryEntry;

public interface IInventoryApi {
	List<InventoryEntry> getOpenInventories(String userId) ;

	/**
	 * Einen Eintrag in der Inventurliste erzeugen
	 * 
	 * @param inventoryId
	 * @param itemId
	 * @param amount
	 * @param userId
	 * @param largeDifference
	 */
	void createInventoryEntry(
			Integer inventoryId,
			Integer itemId,
			BigDecimal amount,
			String userId,
			Boolean largeDifference) ;

	void updateInventoryEntry(
			Integer inventoryId,
			Integer itemId,
			BigDecimal amount,
			String userId,
			Boolean changeAmountTo) ;	
	
	void updateInventoryDataEntry(
			Integer inventoryId,
			String userId,
			InventoryDataEntry inventoryEntry,
			Boolean changeAmountTo) ;
	
	void createInventoryDataEntry(
			Integer inventoryId,
			InventoryDataEntry inventoryEntry,
			String userId,
			Boolean largeDifference) ;
}
