package com.heliumv.api.user;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChangePasswordEntry {
	private String password ;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
