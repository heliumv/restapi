package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

public class ItemEntryList {
	private List<ItemEntry> entries ;
	private long rowCount ;
	
	public ItemEntryList() {
		entries = new ArrayList<ItemEntry>() ;
	}
	
	/**
	 * Die Liste aller <code>ItemGroupEntry</code> Eintr&auml;ge
	 * @return
	 */
	public List<ItemEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<ItemEntry> entries) {
		this.entries = entries;
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}
}
