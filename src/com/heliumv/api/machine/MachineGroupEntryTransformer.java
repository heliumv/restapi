package com.heliumv.api.machine;

import com.heliumv.api.BaseFLRTransformer;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class MachineGroupEntryTransformer extends
		BaseFLRTransformer<MachineGroupEntry> {

	@Override
	public MachineGroupEntry transformOne(Object[] flrObject,
			TableColumnInformation columnInformation) {
		MachineGroupEntry entry = new MachineGroupEntry(flrObject[0]) ;
		entry.setId((Integer) flrObject[0]);
		entry.setDescription((String) flrObject[1]) ;
		return entry ;
	}
}
