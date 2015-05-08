package com.heliumv.api.production;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class OpenWorkUpdateEntry extends BaseEntryId {
	private String productionCnr ;
	private Long workItemStartDate ;
	private Integer machineOffsetMs ;	
	private Integer machineId ;

	/**
	 * Die Los-Nummer
	 * @return die Losnummer
	 */
	public String getProductionCnr() {
		return productionCnr;
	}

	public void setProductionCnr(String productionCnr) {
		this.productionCnr = productionCnr;
	}

	/**
	 * Die Zeit in ms seit dem 1.1.1970
	 * 
	 * @return die Zeit in ms seit dem 1.1.1970
	 */
	public Long getWorkItemStartDate() {
		return workItemStartDate;
	}

	public void setWorkItemStartDate(Long workItemStartDate) {
		this.workItemStartDate = workItemStartDate;
	}

	public Integer getMachineOffsetMs() {
		return machineOffsetMs;
	}

	public void setMachineOffsetMs(Integer machineOffsetMs) {
		this.machineOffsetMs = machineOffsetMs;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}
}
