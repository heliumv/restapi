package com.heliumv.api.order;

public class OrderpositionsEntry extends OrderpositionEntry {
	private Integer orderId ;

	public OrderpositionsEntry() {
	}
	
	public OrderpositionsEntry(Integer orderId, OrderpositionEntry entry) {
		this.setOrderId(orderId);
		this.setAmount(entry.getAmount());
		this.setDescription(entry.getDescription());
		this.setId(entry.getId());
		this.setItemCnr(entry.getItemCnr());
		this.setPositionNr(entry.getPositionNr());
		this.setPrice(entry.getPrice());
		this.setStatus(entry.getStatus());
		this.setUnitCnr(entry.getUnitCnr());
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
