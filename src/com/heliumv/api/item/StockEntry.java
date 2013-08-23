package com.heliumv.api.item;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class StockEntry extends BaseEntryId {
	private String name ;
	private String typeCnr ;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeCnr() {
		return typeCnr;
	}
	public void setTypeCnr(String typeCnr) {
		this.typeCnr = typeCnr;
	}
}
