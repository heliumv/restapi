package com.heliumv.api.delivery;

public interface IDeliveryApi {
	String createDispatchNotification(String userId, Integer deliveryId, String deliveryCnr) ;
}
