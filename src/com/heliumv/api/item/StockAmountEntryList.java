package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/**
 * Die Liste aller Lagerst&auml;nde eines Artikels
 * @author Gerold
 */
public class StockAmountEntryList {
	private List<StockAmountEntry> entries ;
	
	public StockAmountEntryList() {
		entries = new ArrayList<StockAmountEntry>() ;
	}

	/**
	 * Die Liste aller <code>StockAmountEntry</code> Eintr&auml;ge
	 * @return
	 */
	public List<StockAmountEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<StockAmountEntry> entries) {
		this.entries = entries;
	}
}
