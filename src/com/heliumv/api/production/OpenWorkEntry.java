package com.heliumv.api.production;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class OpenWorkEntry extends BaseEntryId {
	private String customerShortDescription ;
	private String abc ;
	private String productionCnr ;
	private String partlistCnr ;
	private String partlistDescription ;
	
	private Integer workNumber ;
	private String workItemCnr ;
	private String workItemDescription ;
	
	private Long workItemStartDate ;
	private Integer workItemStartCW ;
	private Integer workItemStartCY ;
	private Integer machineOffsetMs ;
	
	private BigDecimal duration ;
	
	private String machineCnr ;
	private String machineDescription ;
	
	private String materialCnr ;
	private String materialDescription ;
	
	private Boolean hasWorktime ;
	private Integer machineId ;
	
	public String getProductionCnr() {
		return productionCnr;
	}

	public void setProductionCnr(String productionCnr) {
		this.productionCnr = productionCnr;
	}

	public String getPartlistCnr() {
		return partlistCnr;
	}

	public void setPartlistCnr(String partlistCnr) {
		this.partlistCnr = partlistCnr;
	}

	public String getPartlistDescription() {
		return partlistDescription;
	}

	public void setPartlistDescription(String partlistDescription) {
		this.partlistDescription = partlistDescription;
	}

	public Integer getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(Integer workNumber) {
		this.workNumber = workNumber;
	}

	public String getWorkItemCnr() {
		return workItemCnr;
	}

	public void setWorkItemCnr(String workItemCnr) {
		this.workItemCnr = workItemCnr;
	}

	public String getWorkItemDescription() {
		return workItemDescription;
	}

	public void setWorkItemDescription(String workItemDescription) {
		this.workItemDescription = workItemDescription;
	}

	public Long getWorkItemStartDate() {
		return workItemStartDate;
	}

	public void setWorkItemStartDate(Long workItemStartDate) {
		this.workItemStartDate = workItemStartDate;
	}

	public Integer getWorkItemStartCalendarWeek() {
		return workItemStartCW;
	}

	public void setWorkItemStartCalendarWeek(Integer calendarWeek) {
		this.workItemStartCW = calendarWeek;
	}

	public Integer getWorkItemStartCalendarYear() {
		return workItemStartCY;
	}
	
	public void setWorkItemStartCalendarYear(Integer year) {
		this.workItemStartCY = year;
	}

	public Integer getMachineOffsetMs() {
		return machineOffsetMs;
	}

	public void setMachineOffsetMs(Integer machineOffsetMs) {
		this.machineOffsetMs = machineOffsetMs;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public String getMachineCnr() {
		return machineCnr;
	}

	public void setMachineCnr(String machineCnr) {
		this.machineCnr = machineCnr;
	}

	public String getMachineDescription() {
		return machineDescription;
	}

	public void setMachineDescription(String machineDescription) {
		this.machineDescription = machineDescription;
	}

	public String getMaterialCnr() {
		return materialCnr;
	}

	public void setMaterialCnr(String materialCnr) {
		this.materialCnr = materialCnr;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public Boolean getHasWorktime() {
		return hasWorktime;
	}

	public void setHasWorktime(Boolean hasWorktime) {
		this.hasWorktime = hasWorktime;
	}

	public String getCustomerShortDescription() {
		return customerShortDescription;
	}

	public void setCustomerShortDescription(String customerShortDescription) {
		this.customerShortDescription = customerShortDescription;
	}

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}	
}
