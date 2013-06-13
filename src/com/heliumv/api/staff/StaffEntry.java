package com.heliumv.api.staff;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StaffEntry {
	private Integer id ;
	private Integer personalNr ;
	private String  identityCnr ;
	private String  shortMark ;
	private String  name ;
	private String  firstName ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPersonalNr() {
		return personalNr;
	}
	public void setPersonalNr(Integer personalNr) {
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
