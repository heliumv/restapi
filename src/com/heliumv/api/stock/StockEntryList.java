package com.heliumv.api.stock;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.item.StockEntry;

@XmlRootElement
public class StockEntryList {
	private List<StockEntry> entries ;

	public StockEntryList() {
		entries = new ArrayList<StockEntry>() ;
	}
	
	/**
	 * Eine Liste aller Lagereintr&auml;ge</br>
	 * <p>Es sind nur jene Lager enthalten, auf die der angemeldete Benutzer Zugriff hat</p>
	 * @return eine (leere) Liste aller Lagereintr&auml;ge
	 */
	public List<StockEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<StockEntry> entries) {
		this.entries = entries;
	}
}
