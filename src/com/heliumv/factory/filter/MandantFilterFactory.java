package com.heliumv.factory.filter;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.IGlobalInfo;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;

public class MandantFilterFactory {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public MandantFilterFactory(){}
	
	protected FilterKriterium baseMandantFilterWO() {
		return baseMandantFilterWO(globalInfo.getMandant()) ;
	}

	protected FilterKriterium baseMandantFilterWO(String mandantCnr) {		
		return new FilterKriterium(
				"mandant_c_nr", true, "'" + mandantCnr + "'", FilterKriterium.OPERATOR_EQUAL, false) ;				
	}
	
	protected FilterKriterium baseMandantFilter(String flrTableName) {
		return baseMandantFilter(flrTableName, globalInfo.getMandant()) ;
	}

	protected FilterKriterium baseMandantFilter(String flrTableName, String mandantCnr) {
		return new FilterKriterium(
				flrTableName + ".mandant_c_nr", true, "'" + mandantCnr + "'", FilterKriterium.OPERATOR_EQUAL, false) ;
	}
	
	public FilterKriterium offeneAg() {
		return baseMandantFilter("flroffeneags") ;
	}

	public FilterKriterium offeneAg(String mandantCnr) {
		return baseMandantFilter("flroffeneags", mandantCnr) ;
	}
	
	public FilterKriterium maschine() {
		return baseMandantFilterWO() ;
	}

	public FilterKriterium maschine(String mandantCnr) {
		return baseMandantFilterWO(mandantCnr) ;
	}
}
