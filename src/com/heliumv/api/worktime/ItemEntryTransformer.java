package com.heliumv.api.worktime;

import com.heliumv.api.BaseFLRTransformer;
import com.heliumv.api.item.ItemEntry;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class ItemEntryTransformer extends BaseFLRTransformer<ItemEntry> {
	
	@Override
	public ItemEntry transformOne(Object[] flrObject, TableColumnInformation columnInformation) {
		ItemEntry entry = new ItemEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setCnr((String) flrObject[1]) ;
		entry.setDescription((String) flrObject[2]) ;

		return entry ;
	}
}
