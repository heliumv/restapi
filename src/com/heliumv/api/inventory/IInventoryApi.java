package com.heliumv.api.inventory;

import java.util.List;

import com.heliumv.api.item.InventoryEntry;

public interface IInventoryApi {
	List<InventoryEntry> getOpenInventories(String userId) ;

}
