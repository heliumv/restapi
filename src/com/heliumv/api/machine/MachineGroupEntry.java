package com.heliumv.api.machine;

import com.heliumv.api.BaseEntryId;

public class MachineGroupEntry extends BaseEntryId {
	private String description ;

	public MachineGroupEntry() {
	}

	public MachineGroupEntry(Object flrId) {
		super((Integer) flrId) ;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
