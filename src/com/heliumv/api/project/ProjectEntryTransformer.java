package com.heliumv.api.project;

import com.heliumv.api.BaseFLRTransformer;

public class ProjectEntryTransformer extends BaseFLRTransformer<ProjectEntry> {

	@Override
	public ProjectEntry transformOne(Object[] flrObject) {
		ProjectEntry entry = new ProjectEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setCnr((String) flrObject[1]) ;
		entry.setCustomerName((String) flrObject[2]) ;
		
		return entry ;
	}
}
