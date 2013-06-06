package com.heliumv.api.project;

import java.util.ArrayList;
import java.util.List;

import com.heliumv.api.BaseFLRTransformer;

public class ProjectEntryTransformer extends BaseFLRTransformer<ProjectEntry> {

	@Override
	public List<ProjectEntry> transform(Object[][] flrObjects) {
		ArrayList<ProjectEntry> orders = new ArrayList<ProjectEntry>() ;
		if(flrObjects == null || flrObjects.length == 0) return orders ;
		
		for (Object[] objects : flrObjects) {
			ProjectEntry entry = new ProjectEntry() ;
			entry.setId((Integer) objects[0]) ;
			entry.setCnr((String) objects[1]) ;
			entry.setCustomerName((String) objects[2]) ;
			
			orders.add(entry) ;			
		}
		
		return orders ;	}

}
