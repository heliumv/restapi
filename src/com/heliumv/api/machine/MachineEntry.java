package com.heliumv.api.machine;

import com.heliumv.api.BaseEntryId;

public class MachineEntry extends BaseEntryId {
	private String inventoryNumber ;
	private String description ;
	private String identificationNumber ;
	private Integer machineGroupId ;
	private String machineGroupDescription ;
	
	public MachineEntry() {
	}

	public MachineEntry(Object flrId) {
		super((Integer) flrId) ;
	}
	
	public String getInventoryNumber() {
		return inventoryNumber;
	}
	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public Integer getMachineGroupId() {
		return machineGroupId;
	}

	public void setMachineGroupId(Integer machineGroupId) {
		this.machineGroupId = machineGroupId;
	}

	public String getMachineGroupDescription() {
		return machineGroupDescription;
	}

	public void setMachineGroupDescription(String machineGroupDescription) {
		this.machineGroupDescription = machineGroupDescription;
	}	
}
