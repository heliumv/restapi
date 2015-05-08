package com.heliumv.api.machine;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MachineAvailabilityEntry {
	private Integer machineId ;
	private Integer dayTypeId ;
	private String dayTypeDescription ;	
	private BigDecimal availabilityHours ;
	private long dateMs ;
	
	public Integer getMachineId() {
		return machineId;
	}
	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}
	public Integer getDayTypeId() {
		return dayTypeId;
	}
	public void setDayTypeId(Integer dayTypeId) {
		this.dayTypeId = dayTypeId;
	}	
	public BigDecimal getAvailabilityHours() {
		return availabilityHours;
	}
	public void setAvailabilityHours(BigDecimal availabilityHours) {
		this.availabilityHours = availabilityHours;
	}
	public String getDayTypeDescription() {
		return dayTypeDescription;
	}
	public void setDayTypeDescription(String dayTypeDescription) {
		this.dayTypeDescription = dayTypeDescription;
	}
	public long getDateMs() {
		return dateMs;
	}
	public void setDateMs(long dateMs) {
		this.dateMs = dateMs;
	}
}
