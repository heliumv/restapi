package com.heliumv.factory.query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.customer.CustomerEntry;
import com.heliumv.api.customer.CustomerEntryTransformer;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IParameterCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.partner.service.KundeFac;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.util.Helper;

public class CustomerQuery extends BaseQuery<CustomerEntry> {
	@Autowired
	private IParameterCall parameterCall ;
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public CustomerQuery() {
		super(QueryParameters.UC_ID_KUNDE2) ;
		setTransformer(new CustomerEntryTransformer()) ;
	}
	
	public CustomerQuery(IParameterCall parameterCall) throws NamingException {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_KUNDE2) ;
		setTransformer(new CustomerEntryTransformer()) ;
		this.parameterCall = parameterCall ;
	}


	@Override
	protected List<FilterKriterium> getRequiredFilters() {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		filters.add(getMandantFilter()) ;
		filters.add(getVersteckerLieferantFilter()) ;
		
		return filters;
	}
	
	private FilterKriterium getMandantFilter() {
		return new FilterKriterium(
				"mandant_c_nr", true, 
				StringHelper.asSqlString(globalInfo.getTheClientDto().getMandant()),
				FilterKriterium.OPERATOR_EQUAL, false);		
	}
	
	private FilterKriterium getVersteckerLieferantFilter() {
		return new FilterKriterium(
				KundeFac.FLR_KUNDE_B_VERSTECKTERLIEFERANT, true,
				Helper.boolean2Short(false) + "",
				FilterKriterium.OPERATOR_EQUAL, false) ;
	}
}