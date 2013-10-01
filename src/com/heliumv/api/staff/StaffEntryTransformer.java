package com.heliumv.api.staff;

import com.heliumv.api.BaseFLRTransformer;

public class StaffEntryTransformer extends BaseFLRTransformer<StaffEntry> {

	@Override
	public StaffEntry transformOne(Object[] flrObject) {
		StaffEntry entry = new StaffEntry() ;
		entry.setId((Integer)flrObject[0]) ;
		entry.setPersonalNr((String) flrObject[1]) ;
		entry.setIdentityCnr((String) flrObject[2]) ;
		entry.setName((String) flrObject[3]) ;
		entry.setFirstName((String) flrObject[4]) ;
		entry.setShortMark((String) flrObject[5]) ;
		return entry ;
	}
}
