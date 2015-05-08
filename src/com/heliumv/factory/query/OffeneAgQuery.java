package com.heliumv.factory.query;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.production.OpenWorkEntry;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.filter.MandantFilterFactory;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.util.Helper;

public class OffeneAgQuery extends BaseQuery<OpenWorkEntry> {
	@Autowired
	private IGlobalInfo globalInfo ;
	@Autowired
	private MandantFilterFactory mandantFilter ;
	
	public OffeneAgQuery() {
		super(QueryParameters.UC_ID_OFFENE_AGS) ;
	}

	@Override
	protected List<FilterKriterium> getRequiredFilters()
			throws NamingException, RemoteException {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		filters.add(getFilterMandant()) ;
		return filters ;
	}
	
	public FilterKriterium getFilterBeginnDatum(Date startDate) {
		return new FilterKriterium("flroffeneags.t_agbeginn", true,
		"'" + Helper.formatDateWithSlashes(new java.sql.Date(startDate.getTime())) + "'",
		FilterKriterium.OPERATOR_GTE, false) ;	
	}

	public FilterKriterium getFilterEndeDatum(Date endDate) {
		return new FilterKriterium("flroffeneags.t_agbeginn", true,
		"'" + Helper.formatDateWithSlashes(new java.sql.Date(endDate.getTime())) + "'",
		FilterKriterium.OPERATOR_LT, false) ;	
	}

	private FilterKriterium getFilterMandant() {
		return mandantFilter.offeneAg() ;
//		return new FilterKriterium("flroffeneags.mandant_c_nr", true,
//				"'" + globalInfo.getMandant() + "'",
//				FilterKriterium.OPERATOR_EQUAL, false);		
	}		
}
