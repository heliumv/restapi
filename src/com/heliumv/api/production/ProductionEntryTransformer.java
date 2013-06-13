package com.heliumv.api.production;

import com.heliumv.api.BaseFLRTransformer;

public class ProductionEntryTransformer extends
		BaseFLRTransformer<ProductionEntry> {

	@Override
	public ProductionEntry transformOne(Object[] flrObject) {
		ProductionEntry entry = new ProductionEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setCnr((String) flrObject[1]) ;
		entry.setAmount((Integer) flrObject[2]) ;
		entry.setOrderOrItemCnr((String) flrObject[3]) ;
		
		return entry ;
	}
}
