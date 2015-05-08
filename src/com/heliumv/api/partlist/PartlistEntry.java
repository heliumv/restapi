package com.heliumv.api.partlist;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class PartlistEntry extends BaseEntryId {
	private String cnr ;
	private String description ;
	private String additionalDescription ;
	private Integer lotCount ;
	private String statusCnr ;
	private String customerItemCnr ;
	
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
	public String getAdditionalDescription() {
		return additionalDescription;
	}
	public void setAdditionalDescription(String additionalDescription) {
		this.additionalDescription = additionalDescription;
	}
	public Integer getLotCount() {
		return lotCount;
	}
	public void setLotCount(Integer lotCount) {
		this.lotCount = lotCount;
	}
	public String getStatusCnr() {
		return statusCnr;
	}
	public void setStatusCnr(String statusCnr) {
		this.statusCnr = statusCnr;
	}
	public String getCustomerItemCnr() {
		return customerItemCnr;
	}
	public void setCustomerItemCnr(String customerItemCnr) {
		this.customerItemCnr = customerItemCnr;
	}
}
