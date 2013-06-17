package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductionRecordingEntry extends DocumentRecordingEntry {

	private Integer productionId ;
	
	public Integer getProductionId() {
		return productionId;
	}
	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
	}
}
