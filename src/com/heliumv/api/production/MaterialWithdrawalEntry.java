package com.heliumv.api.production;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MaterialWithdrawalEntry {
	private String lotCnr ;
	private String itemCnr ;
	private BigDecimal amount ;
	private List<IdentityAmountEntry> identities ;
	
	public String getLotCnr() {
		return lotCnr;
	}
	public void setLotCnr(String lotCnr) {
		this.lotCnr = lotCnr;
	}
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
	public List<IdentityAmountEntry> getIdentities() {
		return identities;
	}
	public void setIdentities(List<IdentityAmountEntry> identities) {
		this.identities = identities;
	}
}
