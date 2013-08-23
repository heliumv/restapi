package com.heliumv.api.item;

import com.lp.server.artikel.service.InventurDto;

public class InventoryEntryMapper {

	public InventoryEntry mapEntry(InventurDto inventurDto) {
		InventoryEntry entry = new InventoryEntry() ;
		if(inventurDto != null) {
			entry.setId(inventurDto.getIId()) ;
			entry.setStockId(inventurDto.getLagerIId());
			entry.setName(inventurDto.getCBez()) ;
		}
		return entry ;
 	}
}
