package com.heliumv.api.item;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StockAmountEntry {
	private BigDecimal amount ;
	private StockEntry stock ;

	public StockAmountEntry() {
	}
	
	public StockAmountEntry(StockEntry stock, BigDecimal amount) {
		this.stock = stock ;
		this.amount = amount ;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public StockEntry getStock() {
		return stock;
	}
	public void setStock(StockEntry stock) {
		this.stock = stock;
	}
}
