package com.heliumv.api.order;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class OrderEntry extends BaseEntryId {
	private String cnr ;
	
	private String orderType ;
	private String projectName ;
	private String orderState ;
	private Timestamp documentDate ;
	private Integer customerIdOrderAddress ;
	private String customerName ;
	private String customerAddress ;
	
	public String getCnr() {
		return cnr;
	}
	public void setCnr(String cnr) {
		this.cnr = cnr;
	}
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName == null ? "" : projectName ;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public Timestamp getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Timestamp documentDate) {
		this.documentDate = documentDate;
	}
	public Integer getCustomerIdOrderAddress() {
		return customerIdOrderAddress;
	}
	public void setCustomerIdOrderAddress(Integer customerIdOrderAddress) {
		this.customerIdOrderAddress = customerIdOrderAddress;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress == null ? "" : customerAddress ;
	}
	
}
