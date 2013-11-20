package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/**
 * Die Auftragsspezifischen Zeitdaten (Auftrags-Id und AuftragspositionsId)
 * @author Gerold
 *
 */
public class OrderRecordingEntry extends DocumentRecordingEntry {
	private Integer orderId ;
	private Integer orderPositionId ;

	/**
	 * Die Auftrags-Id muss gesetzt sein. 
	 * @return
	 */
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * Die Positions-Id einer Auftragsposition muss gesetzt sein
	 * @return
	 */
	public Integer getOrderPositionId() {
		return orderPositionId;
	}
	public void setOrderPositionId(Integer orderPositionId) {
		this.orderPositionId = orderPositionId;
	}
}
