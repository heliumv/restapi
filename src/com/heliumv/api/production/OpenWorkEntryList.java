package com.heliumv.api.production;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OpenWorkEntryList {
	private List<OpenWorkEntry> entries ;

	/**
	 * Die (leere) Liste aller offenen Arbeitsg&auml;nge
	 * @return die (leere) Liste aller offenen Arbeitsg&auml;nge
	 */
	public List<OpenWorkEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<OpenWorkEntry> entries) {
		this.entries = entries;
	}
}
