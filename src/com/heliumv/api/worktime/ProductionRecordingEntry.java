package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductionRecordingEntry {

	private String  userId ;
	private Integer productionId ;
	private Integer workItemId ;
	private String  remark ;
	private String  extendedRemark ;
	private Integer forUserId ;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Integer getForUserId() {
		return forUserId;
	}

	public void setForUserId(Integer forUserId) {
		this.forUserId = forUserId;
	}
	public Integer getProductionId() {
		return productionId;
	}
	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
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
