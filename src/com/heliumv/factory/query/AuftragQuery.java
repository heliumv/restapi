package com.heliumv.factory.query;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.order.OrderEntry;
import com.heliumv.api.order.OrderEntryTransformer;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IParameterCall;
import com.heliumv.tools.FilterHelper;
import com.heliumv.tools.StringHelper;
import com.lp.server.auftrag.service.AuftragFac;
import com.lp.server.auftrag.service.AuftragServiceFac;
import com.lp.server.partner.service.KundeFac;
import com.lp.server.partner.service.PartnerFac;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class AuftragQuery extends BaseQuery<OrderEntry> {
	@Autowired
	private IParameterCall parameterCall ;

	public AuftragQuery() {
		super(QueryParameters.UC_ID_AUFTRAG) ;
		setTransformer(new OrderEntryTransformer()) ;
	}
	
	@Autowired
	public AuftragQuery(IParameterCall parameterCall) throws NamingException {
		super(QueryParameters.UC_ID_AUFTRAG) ;
		setTransformer(new OrderEntryTransformer()) ;
		this.parameterCall = parameterCall ;
	}
	
	@Autowired 
	public void setParameterCall(IParameterCall parameterCall) {
		this.parameterCall = parameterCall ;
	}

	private IParameterCall getParameterCall() {
		return parameterCall ;
	}
	
	public FilterKriterium getFilterCnr(String cnr) {
		if(cnr == null || cnr.trim().length() == 0) return null ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt("c_nr", StringHelper.removeSqlDelimiters(cnr), 
				FilterKriterium.OPERATOR_LIKE, "", 
				FilterKriteriumDirekt.PROZENT_LEADING, 
				true, false, Facade.MAX_UNBESCHRAENKT) ;
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	public FilterKriterium getFilterProject(String project) {
		if(null == project || project.trim().length() == 0) return null ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt("c_bez", StringHelper.removeSqlDelimiters(project), 
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING, 
				true, true, Facade.MAX_UNBESCHRAENKT) ;
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	public FilterKriterium getFilterCustomer(String customer) throws NamingException, RemoteException {
		if(null == customer || customer.trim().length() == 0) return null ;

		int percentType = parameterCall
				.isPartnerSucheWildcardBeidseitig() ? FilterKriteriumDirekt.PROZENT_BOTH : FilterKriteriumDirekt.PROZENT_TRAILING ;

		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(AuftragFac.FLR_AUFTRAG_FLRKUNDE
				+ "." + KundeFac.FLR_PARTNER + "."
				+ PartnerFac.FLR_PARTNER_NAME1NACHNAMEFIRMAZEILE1, StringHelper.removeSqlDelimiters(customer),
				FilterKriterium.OPERATOR_LIKE, "",
				percentType, true, true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	
	public FilterKriterium getFilterWithHidden(Boolean withHidden) {
		return FilterHelper.createWithHidden(withHidden, AuftragFac.FLR_AUFTRAG_B_VERSTECKT) ;
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
				AuftragFac.FLR_AUFTRAG_MANDANT_C_NR, true, 
				StringHelper.asSqlString(Globals.getTheClientDto().getMandant()),
				FilterKriterium.OPERATOR_EQUAL, false);		
	}
	
	private FilterKriterium getFilterErledigteBuchbar() {
		return new FilterKriterium(
				AuftragFac.FLR_AUFTRAG_AUFTRAGSTATUS_C_NR, true, "(" +
						StringHelper.asSqlString(AuftragServiceFac.AUFTRAGSTATUS_STORNIERT) + ")",
				FilterKriterium.OPERATOR_NOT_IN, false);
	}

	private FilterKriterium getFiltersErledigteNichtBuchbar() {
		return new FilterKriterium(
				AuftragFac.FLR_AUFTRAG_AUFTRAGSTATUS_C_NR, true, "(" +
						StringHelper.asSqlString(AuftragServiceFac.AUFTRAGSTATUS_ERLEDIGT) + "," +
						StringHelper.asSqlString(AuftragServiceFac.AUFTRAGSTATUS_STORNIERT) + ")",
				FilterKriterium.OPERATOR_NOT_IN, false);
	}
}
