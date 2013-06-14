package com.heliumv.api.order;

import java.math.BigDecimal;

import com.heliumv.api.BaseFLRTransformer;

public class OrderpositionEntryTransformer extends
		BaseFLRTransformer<OrderpositionEntry> {

	@Override
	public OrderpositionEntry transformOne(Object[] flrObject) {
		OrderpositionEntry entry = new OrderpositionEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setPositionNr((Integer) flrObject[1]) ;
		entry.setAmount((BigDecimal) flrObject[2]) ;
		entry.setUnitCnr((String) flrObject[3]) ;
		entry.setItemCnr((String) flrObject[4]) ;
		entry.setDescription((String) flrObject[5]) ;
		entry.setPrice((BigDecimal) flrObject[6]) ;
		entry.setStatus((String) flrObject[7]) ;

		return entry ;
	}

}
