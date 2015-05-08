package com.heliumv.factory.query;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.machine.MachineEntry;
import com.heliumv.factory.filter.MandantFilterFactory;
import com.heliumv.tools.FilterHelper;
import com.lp.server.personal.service.ZeiterfassungFac;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class MachineQuery extends BaseQuery<MachineEntry> {
	@Autowired
	private MandantFilterFactory mandantFilter ;

	public MachineQuery() {
		super(QueryParameters.UC_ID_MASCHINE) ;
	}

	@Override
	protected List<FilterKriterium> getRequiredFilters()
			throws NamingException, RemoteException {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		filters.add(mandantFilter.maschine()) ;
		return filters ;
	}
	
	public FilterKriterium getFilterWithHidden(Boolean withHidden) {
		return FilterHelper.createWithHidden(withHidden, ZeiterfassungFac.FLR_MASCHINE_B_VERSTECKT) ;
	}
}
