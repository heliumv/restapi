package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.order.OrderEntry;
import com.heliumv.api.order.OrderEntryTransformer;
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
	
	private OrderEntryTransformer entryTransformer = new OrderEntryTransformer() ;
	
	public AuftragQuery() throws NamingException {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_AUFTRAG) ;
	}
	
	@Autowired
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
	
	public List<OrderEntry> getResultList(QueryResult result) {
		return entryTransformer.transform(result.getRowData()) ;
	}

	
	protected List<FilterKriterium> getRequiredFilters() {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;

		filters.add(getMandantFilter()) ;
		
		try {
			if(getParameterCall().isZeitdatenAufErledigteBuchbar()) {
				filters.add(getFilterErledigteBuchbar()) ;
			} else {
				filters.add(getFiltersErledigteNichtBuchbar()) ;
			}
		} catch(NamingException e) {			
			filters.add(getFiltersErledigteNichtBuchbar()) ;
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
