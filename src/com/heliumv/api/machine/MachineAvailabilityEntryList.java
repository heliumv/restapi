package com.heliumv.api.machine;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MachineAvailabilityEntryList {
	private List<MachineAvailabilityEntry> entries ;

	public MachineAvailabilityEntryList() {
		entries = new ArrayList<MachineAvailabilityEntry>();
	}
	
	public List<MachineAvailabilityEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<MachineAvailabilityEntry> entries) {
		this.entries = entries;
	}	
}
