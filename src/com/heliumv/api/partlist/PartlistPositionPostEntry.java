package com.heliumv.api.partlist;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class PartlistPositionPostEntry extends BaseEntryId {
	private String itemCnr ;
	private BigDecimal amount ;
	private String unitCnr ;
	private String position ;
	private String comment ;
	
	public String getItemCnr() {
		return itemCnr;
	}

	public void setItemCnr(String itemCnr) {
		this.itemCnr = itemCnr ;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUnitCnr() {
		return unitCnr;
	}

	public void setUnitCnr(String unitCnr) {
		this.unitCnr = unitCnr;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
