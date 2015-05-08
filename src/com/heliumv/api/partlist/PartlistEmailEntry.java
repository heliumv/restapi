package com.heliumv.api.partlist;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PartlistEmailEntry {
	private String emailText ;

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}
}
