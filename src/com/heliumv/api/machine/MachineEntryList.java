package com.heliumv.api.machine;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MachineEntryList {
	private List<MachineEntry> entries ;

	public MachineEntryList() {
		entries = new ArrayList<MachineEntry>();
	}
	
	public List<MachineEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<MachineEntry> entries) {
		this.entries = entries;
	}	
}
