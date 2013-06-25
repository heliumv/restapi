package com.heliumv.api.production;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdentityAmountEntry {
	private String identiy ;
	private BigDecimal amount ;
	
	public String getIdentiy() {
		return identiy;
	}
	public void setIdentiy(String identiy) {
		this.identiy = identiy;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
