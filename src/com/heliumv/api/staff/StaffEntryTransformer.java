package com.heliumv.api.staff;

import com.heliumv.api.BaseFLRTransformer;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class StaffEntryTransformer extends BaseFLRTransformer<StaffEntry> {

	@Override
	public StaffEntry transformOne(Object[] flrObject, TableColumnInformation columnInformation) {
		StaffEntry entry = new StaffEntry() ;
		entry.setId((Integer)flrObject[0]) ;
		entry.setPersonalNr(Integer.valueOf((String) flrObject[1])) ;
		entry.setIdentityCnr((String) flrObject[2]) ;
		entry.setName((String) flrObject[3]) ;
		entry.setFirstName((String) flrObject[4]) ;
		entry.setShortMark((String) flrObject[5]) ;
		return entry ;
	}
}
