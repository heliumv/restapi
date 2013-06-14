package com.heliumv.api.production;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class ProductionEntry extends BaseEntryId {
	private String cnr ;
	private Integer amount ;
	private String orderOrItemCnr ;
		
	public String getCnr() {
		return cnr;
	}
	public void setCnr(String cnr) {
		this.cnr = cnr;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getOrderOrItemCnr() {
		return orderOrItemCnr;
	}
	public void setOrderOrItemCnr(String orderOrItemCnr) {
		this.orderOrItemCnr = orderOrItemCnr;
	}
	
	
}
