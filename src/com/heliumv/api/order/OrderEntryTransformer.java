package com.heliumv.api.order;

import java.util.ArrayList;
import java.util.List;

import com.heliumv.api.BaseFLRTransformer;

public class OrderEntryTransformer extends BaseFLRTransformer<OrderEntry> {

	@Override
	public List<OrderEntry> transform(Object[][] flrObjects) {
		ArrayList<OrderEntry> orders = new ArrayList<OrderEntry>() ;
		if(flrObjects == null || flrObjects.length == 0) return orders ;
		
		for (Object[] objects : flrObjects) {
			OrderEntry entry = new OrderEntry() ;
			entry.setId((Integer) objects[0]) ;
			entry.setOrderType((String) objects[1]) ;
			entry.setCnr((String) objects[2]) ;
			entry.setCustomerName((String) objects[3]) ;
			entry.setCustomerAddress((String) objects[4]) ;
			entry.setProjectName((String) objects[5]) ;
//			entry.setOrderState((String) data[i][9]) ;
			
			orders.add(entry) ;			
		}

		return orders ;
	}

}
