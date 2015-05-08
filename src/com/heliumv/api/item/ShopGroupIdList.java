package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Eine Liste von Integer die die Shopgruppen-Ids repr&auml;sentiert
 * 
 * @author gp
 */
@XmlRootElement
public class ShopGroupIdList {
	private List<Integer> entries ;
	
	public ShopGroupIdList() {
		setEntries(new ArrayList<Integer>()) ;
	}
	
	public ShopGroupIdList(List<Integer> entries) {
		this.setEntries(entries) ;
	}

	public List<Integer> getEntries() {
		return entries;
	}

	public void setEntries(List<Integer> entries) {
		this.entries = entries;
	}
}
