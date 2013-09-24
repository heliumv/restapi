package com.heliumv.api.item;

import java.math.BigDecimal;

import com.heliumv.api.BaseFLRTransformer;

public class ItemEntryTransformer extends BaseFLRTransformer<ItemEntry> {

	@Override
	public ItemEntry transformOne(Object[] flrObject) {
		ItemEntry entry = new ItemEntry() ;
		entry.setId((Integer) flrObject[0]); 
		entry.setCnr((String) flrObject[1]) ;
		entry.setBillOfMaterialType((String) flrObject[2]);
		entry.setDescription((String) flrObject[3]);
		entry.setDescription2((String) flrObject[4]);
		
		if(flrObject[5] instanceof BigDecimal) {
			entry.setStockAmount((BigDecimal) flrObject[5]) ;
		}
		if(flrObject[6] instanceof BigDecimal) {
			entry.setCosts((BigDecimal) flrObject[6]) ;			
		}
		if(flrObject[7] instanceof Boolean) {
			entry.setAvailable((Boolean) flrObject[7]) ;
		} else {
			entry.setAvailable(true) ;			
		}
		
		return entry ;
	}
}
