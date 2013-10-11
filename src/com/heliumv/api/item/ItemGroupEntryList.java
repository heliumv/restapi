package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// public class ItemGroupEntryList extends BaseEntryListWrapper<ItemGroupEntry> {	
public class ItemGroupEntryList  {	
	private List<ItemGroupEntry> entries ;

	public ItemGroupEntryList() {
		entries = new ArrayList<ItemGroupEntry>() ;
	}
	
	/**
	 * Die Liste aller <code>ItemGroupEntry</code> Eintr&auml;ge
	 * @return
	 */
	public List<ItemGroupEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<ItemGroupEntry> entries) {
		this.entries = entries;
	}
}
