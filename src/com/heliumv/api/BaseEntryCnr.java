package com.heliumv.api;

public class BaseEntryCnr {
	private String cnr ;
	
	public BaseEntryCnr() {
	}
	
	public BaseEntryCnr(String cnr) {
		this.setCnr(cnr) ;
	}

	public String getCnr() {
		return cnr;
	}

	public void setCnr(String cnr) {
		this.cnr = cnr;
	}
}
