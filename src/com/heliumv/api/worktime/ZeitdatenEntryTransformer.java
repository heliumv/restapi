package com.heliumv.api.worktime;

import com.heliumv.api.BaseFLRTransformer;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class ZeitdatenEntryTransformer extends
		BaseFLRTransformer<ZeitdatenEntry> {

	@Override
	public ZeitdatenEntry transformOne(Object[] flrObject, TableColumnInformation columnInformation) {
		ZeitdatenEntry entry = new ZeitdatenEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setActivityCnr((String) flrObject[1]) ;
		entry.setDescription((String) flrObject[2]) ;
		entry.setTime((String) flrObject[3]) ;
		entry.setDuration((String) flrObject[4]) ;
		entry.setFlags((String) flrObject[5]) ;
		entry.setWhere((String) flrObject[6]) ;
		return entry ;
	}

}
