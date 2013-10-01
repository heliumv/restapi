package com.heliumv.api.staff;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class StaffEntry extends BaseEntryId {
	private String personalNr ;
	private String  identityCnr ;
	private String  shortMark ;
	private String  name ;
	private String  firstName ;
	
	public String getPersonalNr() {
		return personalNr;
	}
	public void setPersonalNr(String personalNr) {
		this.personalNr = personalNr;
	}
	public String getIdentityCnr() {
		return identityCnr;
	}
	public void setIdentityCnr(String identityCnr) {
		this.identityCnr = identityCnr;
	}
	public String getShortMark() {
		return shortMark;
	}
	public void setShortMark(String shortMark) {
		this.shortMark = shortMark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
}
