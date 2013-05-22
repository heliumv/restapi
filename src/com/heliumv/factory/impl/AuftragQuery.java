package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.order.OrderEntry;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IParameterCall;
import com.lp.server.auftrag.service.AuftragFac;
import com.lp.server.auftrag.service.AuftragServiceFac;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

public class AuftragQuery extends FastLaneReaderCall {
	@Autowired
	private IParameterCall parameterCall ;
	
	public AuftragQuery() throws NamingException {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_AUFTRAG) ;
	}
	
	public AuftragQuery(IParameterCall parameterCall) throws NamingException {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_AUFTRAG) ;
		this.parameterCall = parameterCall ;
	}
	
	@Autowired 
	public void setParameterCall(IParameterCall parameterCall) {
		this.parameterCall = parameterCall ;
	}

	private IParameterCall getParameterCall() {
		return parameterCall ;
	}
	
	public List<OrderEntry> asOrderEntry(QueryResult result) {
		ArrayList<OrderEntry> orders = new ArrayList<OrderEntry>() ;
		if(result.getRowData() == null || result.getRowData().length == 0) return orders ;
		
		for (Object[] objects : result.getRowData()) {
			OrderEntry entry = new OrderEntry() ;
			entry.setId((Integer) objects[0]) ;
			entry.setOrderType((String) objects[1]) ;
			entry.setCnr((String) objects[2]) ;
			entry.setCustomerName((String) objects[3]) ;
			entry.setCustomerAddress((String) objects[4]) ;
			entry.setProjectName((String) objects[5]) ;
//			entry.setOrderState((String) data[i][9]) ;
			
			orders.add(entry) ;			
		}

		return orders ;
	}
	
	@Override
	public QueryResult setQuery(QueryParameters queryParams) {
		FilterKriterium[] knownFilters = queryParams.getFilterBlock().filterKrit ;

		List<FilterKriterium> myFilters = getDefaultFilters() ;
		if(knownFilters != null && knownFilters.length > 0) {
			for(int i = 0 ; i < knownFilters.length; i++) {
				myFilters.add(knownFilters[i]) ;
			}
		}
		
		queryParams.getFilterBlock().filterKrit = myFilters.toArray(new FilterKriterium[0]);
		return super.setQuery(queryParams);
	}
	
	public List<FilterKriterium> getDefaultFilters() {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;

		filters.add(getMandantFilter()) ;
		
		try {
			if(getParameterCall().isZeitdatenAufErledigteBuchbar()) {
				filters.add(getFilterErledigteBuchbar()) ;
			} else {
				filters.add(getFiltersErledigteNichtBuchbar()) ;
			}
		} catch(RemoteException e) {
			filters.add(getFiltersErledigteNichtBuchbar()) ;
 		}
		
		return filters ;
	}
	
	private FilterKriterium getMandantFilter() {
		return new FilterKriterium(
				AuftragFac.FLR_AUFTRAG_MANDANT_C_NR, true, "'"
						+ Globals.getTheClientDto().getMandant() + "'",
				FilterKriterium.OPERATOR_EQUAL, false);		
	}
	
	private FilterKriterium getFilterErledigteBuchbar() {
		return new FilterKriterium(
				AuftragFac.FLR_AUFTRAG_AUFTRAGSTATUS_C_NR, true, "('"
						+ AuftragServiceFac.AUFTRAGSTATUS_STORNIERT + "')",
				FilterKriterium.OPERATOR_NOT_IN, false);
	}

	private FilterKriterium getFiltersErledigteNichtBuchbar() {
		return new FilterKriterium(
				AuftragFac.FLR_AUFTRAG_AUFTRAGSTATUS_C_NR, true, "('"
						+ AuftragServiceFac.AUFTRAGSTATUS_ERLEDIGT + "','"
						+ AuftragServiceFac.AUFTRAGSTATUS_STORNIERT + "')",
				FilterKriterium.OPERATOR_NOT_IN, false);
	}
}
