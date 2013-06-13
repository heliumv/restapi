package com.heliumv.api.worktime;

import com.heliumv.api.BaseFLRTransformer;

public class ItemEntryTransformer extends BaseFLRTransformer<ItemEntry> {
	
	@Override
	public ItemEntry transformOne(Object[] flrObject) {
		ItemEntry entry = new ItemEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setCnr((String) flrObject[1]) ;
		entry.setDescription((String) flrObject[2]) ;

		return entry ;
	}
}
