package com.heliumv.factory.legacy;

public class AllLagerEntry {
	private Integer stockId ;
	private String stockCnr ;
	
	public AllLagerEntry() {}
	
	public AllLagerEntry(Integer id, String cnr) {
		this.stockId = id ;
		this.stockCnr = cnr ;
	}
	
	public Integer getStockId() {
		return stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}
	public String getStockCnr() {
		return stockCnr;
	}
	public void setStockCnr(String stockCnr) {
		this.stockCnr = stockCnr;
	}	
}
