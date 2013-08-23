package com.heliumv.api.item;

import com.heliumv.api.BaseEntryId;

public class InventoryEntry extends BaseEntryId {
	private String name ;
	private Integer stockId ;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStockId() {
		return stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}	
}
