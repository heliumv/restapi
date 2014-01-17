package com.heliumv.api.order;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderAddressContact {
	private OrderAddress partnerAddress ;
	private OrderAddress contactAddress ;
	
	/**
	 * Die Adressinformation des Partners/Kunden/Lieferanten
	 * @return
	 */
	public OrderAddress getPartnerAddress() {
		return partnerAddress ;
	}

	public void setPartnerAddress(OrderAddress partnerAddress) {
		this.partnerAddress = partnerAddress ;
	}

	/**
	 * Die Adressinformation des Ansprechpartners
	 * @return
	 */
	public OrderAddress getContactAddress() {
		return contactAddress ;
	}

	public void setContactAddress(OrderAddress contactAddress) {
		this.contactAddress = contactAddress ;
	}
}
