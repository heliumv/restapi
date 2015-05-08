package com.heliumv.api.system;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryCnr;

@XmlRootElement
public class TenantEntry extends BaseEntryCnr {
	private String description ;
	
	public TenantEntry() {
	}
	
	public TenantEntry(String cnr) {
		super(cnr) ; 
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
