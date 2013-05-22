package com.heliumv.tools;

import java.util.ArrayList;
import java.util.List;

import com.lp.server.util.fastlanereader.service.query.FilterKriterium;

public class FilterKriteriumCollector {

	private List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
	
	public FilterKriteriumCollector() {
	}

	public FilterKriteriumCollector(List<FilterKriterium> knownFilters) {
		filters = knownFilters ;
	}
	
	public boolean add(FilterKriterium criterium) {
		if(criterium == null) return false ;
		
		return filters.add(criterium) ;
	}
	
	public List<FilterKriterium> getFilters() {
		return filters ;
	}
	
	public FilterKriterium[] asArray() {
		return filters.toArray(new FilterKriterium[0]) ;
	}
}
