package com.heliumv.api.production;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OpenWorkUpdateEntryList {
	private List<OpenWorkUpdateEntry> entries ;

	/**
	 * Die (leere) Liste aller offenen Arbeitsg&auml;nge
	 * @return die (leere) Liste aller offenen Arbeitsg&auml;nge
	 */
	public List<OpenWorkUpdateEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<OpenWorkUpdateEntry> entries) {
		this.entries = entries;
	}
}
