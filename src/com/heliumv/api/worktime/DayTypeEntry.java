package com.heliumv.api.worktime;

import com.heliumv.api.BaseEntryId;

public class DayTypeEntry extends BaseEntryId {
	private String description ;

	public DayTypeEntry() {
	}
	
	public DayTypeEntry(Integer id, String description) {
		super(id) ;
		setDescription(description);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
