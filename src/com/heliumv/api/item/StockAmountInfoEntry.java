package com.heliumv.api.item;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StockAmountInfoEntry {
	private BigDecimal stockAmount ;
	private BigDecimal reservedAmount ;
	private BigDecimal missingAmount ;
	private BigDecimal availableAmount ;
	
	public BigDecimal getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(BigDecimal stockAmount) {
		this.stockAmount = stockAmount;
	}
	public BigDecimal getReservedAmount() {
		return reservedAmount;
	}
	public void setReservedAmount(BigDecimal reservedAmount) {
		this.reservedAmount = reservedAmount;
	}
	public BigDecimal getMissingAmount() {
		return missingAmount;
	}
	public void setMissingAmount(BigDecimal missingAmount) {
		this.missingAmount = missingAmount;
	}
	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}	
}
