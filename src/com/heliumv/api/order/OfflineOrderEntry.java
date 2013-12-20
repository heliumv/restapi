package com.heliumv.api.order;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OfflineOrderEntry {
	private List<OrderEntry> orders ;
//	private List<OrderpositionsEntry> orderpositions ;
	private List<OrderAddress> addresses ;
	
	public List<OrderEntry> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderEntry> orders) {
		this.orders = orders;
	}
//	public List<OrderpositionsEntry> getOrderpositions() {
//		return orderpositions;
//	}
//	public void setOrderpositions(List<OrderpositionsEntry> orderpositions) {
//		this.orderpositions = orderpositions;
//	}
	public List<OrderAddress> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<OrderAddress> addresses) {
		this.addresses = addresses;
	}	
}
