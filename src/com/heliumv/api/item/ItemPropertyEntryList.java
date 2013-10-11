package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ItemPropertyEntryList {
	private List<ItemPropertyEntry> entries ;
	
	public ItemPropertyEntryList() {
		entries = new ArrayList<ItemPropertyEntry>() ;
	}
	/**
	 * Die Liste aller <code>ItemGroupEntry</code> Eintr&auml;ge
	 * @return
	 */
	public List<ItemPropertyEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<ItemPropertyEntry> entries) {
		this.entries = entries;
	}	
}
