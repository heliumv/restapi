package com.heliumv.api.production;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.heliumv.api.BaseFLRTransformer;

public class ProductionEntryTransformer extends
		BaseFLRTransformer<ProductionEntry> {

	@Override
	public List<ProductionEntry> transform(Object[][] flrObjects) {
		ArrayList<ProductionEntry> projects = new ArrayList<ProductionEntry>() ;
		if(flrObjects == null || flrObjects.length == 0) return projects ;
		
		for (Object[] objects : flrObjects) {
			ProductionEntry entry = new ProductionEntry() ;
			entry.setId((Integer) objects[0]) ;
			entry.setCnr((String) objects[1]) ;
			entry.setAmount((Integer) objects[2]) ;
			entry.setOrderOrItemCnr((String) objects[3]) ;
			
			projects.add(entry) ;			
		}

		return projects ;
	}
}
