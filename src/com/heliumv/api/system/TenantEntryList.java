package com.heliumv.api.system;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TenantEntryList {
	private List<TenantEntry> entries ;
	
	public TenantEntryList() {
		entries = new ArrayList<TenantEntry>() ;
	}
	
	public TenantEntryList(List<TenantEntry> entries) {
		this.entries = entries ;
	}
	
	/**
	 * Eine Liste aller Mandanten</br>
	 * @return eine (leere) Liste aller im System bekannten Mandanten
	 */
	public List<TenantEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<TenantEntry> entries) {
		this.entries = entries;
	}
}
