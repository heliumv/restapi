package com.heliumv.api.inventory;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InventoryDataEntry {
	private String itemCnr ;
	private BigDecimal amount ;
	
	public String getItemCnr() {
		return itemCnr;
	}
	public void setItemCnr(String itemCnr) {
		this.itemCnr = itemCnr;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
