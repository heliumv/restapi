package com.heliumv.api.order;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class OrderEntry extends BaseEntryId {
	private String cnr ;
	
	private String orderType ;
	private String projectName ;
	private String orderState ;
//	private Timestamp documentDate ;
//	private Integer customerIdOrderAddress ;
	private String customerName ;
	private String customerAddress ;
	
	private Integer customerPartnerId ;
	private Integer customerContactId ;
	private Integer deliveryPartnerId ;
	private Integer deliveryContactId ;
	
	private Boolean internalComment ;
	private Boolean externalComment ;
	
	/**
	 * Die Auftragsnummer
	 * @return
	 */
	public String getCnr() {
		return cnr;
	}
	public void setCnr(String cnr) {
		this.cnr = cnr;
	}
	
	/**
	 * Der (technische) Auftrags-Typ (Freier Auftrag, Rahmenauftrag, Abrufauftrag, Wiederholend)
	 * @return
	 */
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	/**
	 * Die (optional) vorhandene "Projekt" oder "Bestell" Bezeichnung
	 * @return
	 */
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName == null ? "" : projectName ;
	}
	
	/** 
	 * Der Auftragsstatus (offen, angelegt, ...)
	 */
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
//	public Timestamp getDocumentDate() {
//		return documentDate;
//	}
//	public void setDocumentDate(Timestamp documentDate) {
//		this.documentDate = documentDate;
//	}
//	public Integer getCustomerIdOrderAddress() {
//		return customerIdOrderAddress;
//	}
//	public void setCustomerIdOrderAddress(Integer customerIdOrderAddress) {
//		this.customerIdOrderAddress = customerIdOrderAddress;
//	}
	
	/**
	 * Der Kundenname
	 * @return
	 */
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	/**
	 * Die Kundenadresse
	 * @return
	 */
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress == null ? "" : customerAddress ;
	}
	public Integer getCustomerPartnerId() {
		return customerPartnerId;
	}
	public void setCustomerPartnerId(Integer customerPartnerId) {
		this.customerPartnerId = customerPartnerId;
	}
	public Integer getCustomerContactId() {
		return customerContactId;
	}
	public void setCustomerContactId(Integer customerContactId) {
		this.customerContactId = customerContactId;
	}
	public Integer getDeliveryPartnerId() {
		return deliveryPartnerId;
	}
	public void setDeliveryPartnerId(Integer deliveryPartnerId) {
		this.deliveryPartnerId = deliveryPartnerId;
	}
	public Integer getDeliveryContactId() {
		return deliveryContactId;
	}
	public void setDeliveryContactId(Integer deliveryContactId) {
		this.deliveryContactId = deliveryContactId;
	}
	public Boolean getInternalComment() {
		return internalComment;
	}
	public void setInternalComment(Boolean internalComment) {
		this.internalComment = internalComment;
	}
	public Boolean getExternalComment() {
		return externalComment;
	}
	public void setExternalComment(Boolean externalComment) {
		this.externalComment = externalComment;
	}
}
