package com.heliumv.api.machine;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.production.OpenWorkEntryList;

@XmlRootElement
public class PlanningView {
	private MachineEntryList machineList ;
	private Map<Integer, MachineAvailabilityEntryList> machineAvailabilityMap ;
	private OpenWorkEntryList openWorkList ;
	private MachineGroupEntryList machineGroupList ;
	
	public MachineEntryList getMachineList() {
		return machineList;
	}
	public void setMachineList(MachineEntryList machineList) {
		this.machineList = machineList;
	}
	
	public OpenWorkEntryList getOpenWorkList() {
		return openWorkList;
	}
	public void setOpenWorkList(OpenWorkEntryList openWorkList) {
		this.openWorkList = openWorkList;
	}

	public Map<Integer, MachineAvailabilityEntryList> getMachineAvailabilityMap() {
		return machineAvailabilityMap;
	}
	public void setMachineAvailabilityMap(
			Map<Integer, MachineAvailabilityEntryList> machineAvailabilityMap) {
		this.machineAvailabilityMap = machineAvailabilityMap;
	}	
	public MachineGroupEntryList getMachineGroupList() {
		return machineGroupList;
	}
	public void setMachineGroupList(MachineGroupEntryList machineGroupList) {
		this.machineGroupList = machineGroupList;
	}
}
