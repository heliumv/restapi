package com.heliumv.api.user;

import com.heliumv.api.system.TenantEntryList;

public class LoggedOnTenantEntry extends LoggedOnEntry {
	private boolean valid ;
	private TenantEntryList possibleTenants ;
	
	public LoggedOnTenantEntry() {
		valid = false ;
	}
	
	public LoggedOnTenantEntry(String token, String client, String localeString) {
		super(token, client, localeString) ;
		valid = true ;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public TenantEntryList getPossibleTenants() {
		return possibleTenants;
	}

	public void setPossibleTenants(TenantEntryList possibleTenants) {
		this.possibleTenants = possibleTenants;
	}
}
