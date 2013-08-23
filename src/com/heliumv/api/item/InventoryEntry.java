package com.heliumv.api.item;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class InventoryEntry extends BaseEntryId {
	private String name ;
	private Integer stockId ;
	private StockEntry stockEntry ;
	
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
	public StockEntry getStockEntry() {
		return stockEntry;
	}
	public void setStockEntry(StockEntry stockEntry) {
		this.stockEntry = stockEntry;
	}	
}
