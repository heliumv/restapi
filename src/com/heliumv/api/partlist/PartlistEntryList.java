package com.heliumv.api.partlist;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PartlistEntryList {
	private List<PartlistEntry> list ;

	public PartlistEntryList() {
		this.list = new ArrayList<PartlistEntry>() ;
	}

	public PartlistEntryList(List<PartlistEntry> entries) {
		setList(entries);
	}

	public List<PartlistEntry> getList() {
		return list;
	}

	public void setList(List<PartlistEntry> entries) {
		this.list = entries;
	}	
}
