package com.heliumv.api.partlist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PartlistPositionEntryList {
	private List<PartlistPositionEntry> list ;
	private BigDecimal salesPrice ;
	
	
	public PartlistPositionEntryList() {
		this.setList(new ArrayList<PartlistPositionEntry>()) ;
		salesPrice = BigDecimal.ZERO ;
	}

	public List<PartlistPositionEntry> getList() {
		return list;
	}

	public void setList(List<PartlistPositionEntry> list) {
		this.list = list;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}	
}
