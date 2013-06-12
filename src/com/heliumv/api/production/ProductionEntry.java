package com.heliumv.api.production;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductionEntry {
	private Integer id ;
	private String cnr ;
	private Integer amount ;
	private String orderOrItemCnr ;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
