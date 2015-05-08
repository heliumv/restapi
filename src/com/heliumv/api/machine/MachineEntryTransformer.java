package com.heliumv.api.machine;

import com.heliumv.api.BaseFLRTransformer;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class MachineEntryTransformer extends BaseFLRTransformer<MachineEntry> {

	@Override
	public MachineEntry transformOne(Object[] flrObject,
			TableColumnInformation columnInformation) {
		MachineEntry entry = new MachineEntry(flrObject[0]) ;
		entry.setInventoryNumber((String) flrObject[1]) ;
		entry.setDescription((String) flrObject[2]) ;
		entry.setIdentificationNumber((String) flrObject[3]) ; 
		entry.setMachineGroupId((Integer) flrObject[6]) ;
		entry.setMachineGroupDescription((String) flrObject[7]) ;
		
		return entry;
	}
}
