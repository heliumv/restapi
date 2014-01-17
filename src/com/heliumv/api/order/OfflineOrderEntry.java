package com.heliumv.api.order;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OfflineOrderEntry {
	private List<OrderEntry> orders ;
	private List<OrderpositionsEntry> orderpositions ;
	private List<OrderAddressContact> addresses ;
	
	/**
	 * Die Liste aller Auftr&auml;ge entsprechend den Suchkriterien
	 * @return
	 */
	public List<OrderEntry> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderEntry> orders) {
		this.orders = orders;
	}
	
	/**
	 * Die Liste aller Positionen f&uuml;r die gelieferten Auftr&auml;ge
	 * 
	 * @return
	 */
	public List<OrderpositionsEntry> getOrderpositions() {
		return orderpositions;
	}
	public void setOrderpositions(List<OrderpositionsEntry> orderpositions) {
		this.orderpositions = orderpositions;
	}
	
	/**
	 * Die Liste aller eindeutigen Adressen
	 * 
	 * @return
	 */
	public List<OrderAddressContact> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<OrderAddressContact> addresses) {
		this.addresses = addresses;
	}	
}
