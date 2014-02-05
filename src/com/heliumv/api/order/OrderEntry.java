/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
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
