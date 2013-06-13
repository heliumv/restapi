package com.heliumv.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.project.ProjectEntry;
import com.heliumv.api.project.ProjectEntryTransformer;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IParameterCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.projekt.service.ProjektFac;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

public class ProjectQuery extends FastLaneReaderCall {
	@Autowired
	private IParameterCall parameterCall ;

	private ProjectEntryTransformer entryTransformer = new ProjectEntryTransformer() ;
	
	public ProjectQuery() {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_PROJEKT) ;
	}

	public ProjectQuery(IParameterCall parameterCall) throws NamingException {		
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_PROJEKT) ;
		this.parameterCall = parameterCall ;
	}
	
	@Autowired 
	public void setParameterCall(IParameterCall parameterCall) {
		this.parameterCall = parameterCall ;
	}

//	private IParameterCall getParameterCall() {
//		return parameterCall ;
//	}	

	public List<ProjectEntry> getResultList(QueryResult result) {
		return entryTransformer.transform(result.getRowData()) ;
	}
	
	protected List<FilterKriterium> getRequiredFilters() {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		filters.add(getMandantFilter()) ;	
		return filters ;
	}
	
	private FilterKriterium getMandantFilter() {
		return new FilterKriterium(
				ProjektFac.FLR_PROJEKT_MANDANT_C_NR, true, 
				StringHelper.asSqlString(Globals.getTheClientDto().getMandant()),
				FilterKriterium.OPERATOR_EQUAL, false);		
	}
}
