package com.heliumv.api.partlist;

import java.math.BigDecimal;

import com.heliumv.api.BaseFLRTransformer;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class PartlistPositionEntryTransformer extends
		BaseFLRTransformer<PartlistPositionEntry> {

	@Override
	public PartlistPositionEntry transformOne(Object[] flrObject,
			TableColumnInformation columnInformation) {
		PartlistPositionEntry entry = new PartlistPositionEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setCnr((String) flrObject[2]);
		entry.setDescription((String) flrObject[3]) ;
		entry.setAmount((BigDecimal) flrObject[4]);
		entry.setPosition((String) flrObject[5]) ;
		return entry ;
	}
}
