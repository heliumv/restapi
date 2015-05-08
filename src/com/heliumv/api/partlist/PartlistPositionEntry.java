package com.heliumv.api.partlist;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class PartlistPositionEntry extends BaseEntryId {
	private String cnr ;
	private String description ;
	private BigDecimal amount ;
	private BigDecimal calcPrice ;
	private String comment ;
	private String position ;
	private String unitCnr ;
	private BigDecimal salesPrice ;
	private Integer itemId ;
	private Integer iSort ;
	private String customerItemCnr ;
	private boolean itemHidden ;
	private boolean itemLocked ;
	
	public String getCnr() {
		return cnr;
	}
	public void setCnr(String cnr) {
		this.cnr = cnr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getCalcPrice() {
		return calcPrice;
	}
	public void setCalcPrice(BigDecimal calcPrice) {
		this.calcPrice = calcPrice;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getUnitCnr() {
		return unitCnr;
	}
	public void setUnitCnr(String unitCnr) {
		this.unitCnr = unitCnr;
	}
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getiSort() {
		return iSort;
	}
	public void setiSort(Integer iSort) {
		this.iSort = iSort;
	}
	public String getCustomerItemCnr() {
		return customerItemCnr;
	}
	public void setCustomerItemCnr(String customerItemCnr) {
		this.customerItemCnr = customerItemCnr;
	}
	public boolean isItemHidden() {
		return itemHidden;
	}
	public void setItemHidden(boolean itemHidden) {
		this.itemHidden = itemHidden;
	}
	public boolean isItemLocked() {
		return itemLocked;
	}
	public void setItemLocked(boolean itemLocked) {
		this.itemLocked = itemLocked;
	}	
}
