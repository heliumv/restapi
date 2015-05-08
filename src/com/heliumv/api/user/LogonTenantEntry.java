package com.heliumv.api.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogonTenantEntry extends LogonEntry {
	private String tenantCnr ;

	public LogonTenantEntry() {
	}
	
	public LogonTenantEntry(String username, String password) {
		super(username, password) ;
	}
	
	public String getTenantCnr() {
		return tenantCnr;
	}

	public void setTenantCnr(String tenantCnr) {
		this.tenantCnr = tenantCnr;
	}	
}
