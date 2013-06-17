package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderRecordingEntry extends DocumentRecordingEntry {
	private Integer orderId ;
	private Integer orderPositionId ;

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderPositionId() {
		return orderPositionId;
	}
	public void setOrderPositionId(Integer orderPositionId) {
		this.orderPositionId = orderPositionId;
	}
}
