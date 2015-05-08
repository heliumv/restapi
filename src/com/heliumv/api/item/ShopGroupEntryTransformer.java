package com.heliumv.api.item;

import com.heliumv.api.BaseFLRTransformer;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class ShopGroupEntryTransformer extends
		BaseFLRTransformer<ShopGroupEntry> {

	@Override
	public ShopGroupEntry transformOne(Object[] flrObject,
			TableColumnInformation columnInformation) {
		ShopGroupEntry entry = new ShopGroupEntry() ;
		entry.setId((Integer) flrObject[0]);
		entry.setCnr((String) flrObject[1]) ;
		entry.setDescription((String) flrObject[2]) ;
		entry.setParentCnr((String) flrObject[3]);
		entry.setParentDescription((String) flrObject[4]) ;
		entry.setParentId((Integer) flrObject[5]) ;
		return entry ;
	}
}
