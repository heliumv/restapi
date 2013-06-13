package com.heliumv.api.order;

import com.heliumv.api.BaseFLRTransformer;

public class OrderEntryTransformer extends BaseFLRTransformer<OrderEntry> {

	@Override
	public OrderEntry transformOne(Object[] flrObject) {
		OrderEntry entry = new OrderEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setOrderType((String) flrObject[1]) ;
		entry.setCnr((String) flrObject[2]) ;
		entry.setCustomerName((String) flrObject[3]) ;
		entry.setCustomerAddress((String) flrObject[4]) ;
		entry.setProjectName((String) flrObject[5]) ;
//		entry.setOrderState((String) data[i][9]) ;
		return entry ;
	}
}
