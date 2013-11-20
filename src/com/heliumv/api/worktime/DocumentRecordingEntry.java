package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Die Zeitdaten einer T&auml;tigkeit 
 * 
 * @author Gerold
 */
@XmlRootElement
public class DocumentRecordingEntry extends TimeRecordingEntry {
	private Integer workItemId ;
	private String  remark ;
	private String  extendedRemark ;

	/**
	 * Die Id der T&auml;tigkeit bzw. des Arbeitszeitartikels
	 * @return
	 */
	public Integer getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(Integer workItemId) {
		this.workItemId = workItemId;
	}
	
	/**
	 * Die Bemerkung
	 * @return
	 */
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * Die erweiterte Bemerkung
	 * @return
	 */
	public String getExtendedRemark() {
		return extendedRemark;
	}
	public void setExtendedRemark(String extendedRemark) {
		this.extendedRemark = extendedRemark;
	}	
}
