package com.heliumv.api.item;

import com.lp.server.artikel.service.InventurDto;
import com.lp.server.artikel.service.LagerDto;

public class InventoryStockEntryMapper {
	private InventoryEntryMapper inventoryMapper ;
	private StockEntryMapper stockMapper ;

	public InventoryStockEntryMapper() {
		inventoryMapper = new InventoryEntryMapper() ;
		stockMapper = new StockEntryMapper() ;
	}
	
	public InventoryStockEntryMapper(InventoryEntryMapper inventoryMapper, StockEntryMapper stockMapper) {
		this.inventoryMapper = inventoryMapper ;
		this.stockMapper = stockMapper ;		
	}
	
	public InventoryEntry mapEntry(InventurDto inventurDto, LagerDto lagerDto) {
		InventoryEntry inventory = inventoryMapper.mapEntry(inventurDto) ;
		inventory.setStockEntry(stockMapper.mapEntry(lagerDto));
		return inventory ;
	}
}
