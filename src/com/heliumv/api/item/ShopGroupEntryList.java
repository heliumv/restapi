package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShopGroupEntryList {
	private List<ShopGroupEntry> entries ;
	
	public ShopGroupEntryList() {
		setEntries(new ArrayList<ShopGroupEntry>()) ;
	}
	
	public ShopGroupEntryList(List<ShopGroupEntry> entries) {
		this.setEntries(entries) ;
	}

	public List<ShopGroupEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<ShopGroupEntry> entries) {
		this.entries = entries;
	}
}
