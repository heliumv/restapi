package com.heliumv.api.worktime;


public class DocumentRecordingEntry extends TimeRecordingEntry {
	private Integer workItemId ;
	private String  remark ;
	private String  extendedRemark ;
	private Integer forUserId ;
	
	
	public Integer getForUserId() {
		return forUserId;
	}

	public void setForUserId(Integer forUserId) {
		this.forUserId = forUserId;
	}

	public Integer getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(Integer workItemId) {
		this.workItemId = workItemId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExtendedRemark() {
		return extendedRemark;
	}
	public void setExtendedRemark(String extendedRemark) {
		this.extendedRemark = extendedRemark;
	}	
}
