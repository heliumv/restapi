package com.heliumv.api.machine;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MachineGroupEntryList {
	private List<MachineGroupEntry> entries ;

	public MachineGroupEntryList() {
		entries = new ArrayList<MachineGroupEntry>();
	}
	
	public List<MachineGroupEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<MachineGroupEntry> entries) {
		this.entries = entries;
	}	
}
