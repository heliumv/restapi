package com.heliumv.factory.legacy;

public class AllEinheitEntry {
	private String cnr ;
	private String description ;
	
	public AllEinheitEntry() {
	}
	
	public AllEinheitEntry(String cnr, String description) {
		this.cnr = cnr ;
		this.description = description ;
	}
	
	public String getCnr() {
		return cnr;
	}
	public void setCnr(String cnr) {
		this.cnr = cnr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
