package com.heliumv.api;

import java.util.ArrayList;
import java.util.List;

public class BaseEntryListWrapper<T> {
	protected List<T> entries ;

	public BaseEntryListWrapper() {
		entries = new ArrayList<T>() ;
	}
	
//	public List<T> getEntries() {
//		return entries ;
//	}
	
	public void setEntries(List<T> newEntries) {
		entries = newEntries ;
	}
}
