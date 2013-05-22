package com.heliumv.api.order;

import java.util.List;

public interface IOrderApi {
	List<OrderEntry> getOrders(
		String userId, Integer limit, Integer startIndex, String filterCnr,
		String filterCustomer, String filterProject, Boolean filterWithHidden) ;
}
