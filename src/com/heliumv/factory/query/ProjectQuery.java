package com.heliumv.factory.query;

import java.util.ArrayList;
import java.util.List;

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

public class ProjectQuery extends BaseQuery<ProjectEntry> {
	@Autowired
	private IParameterCall parameterCall ;
	
	public ProjectQuery() {
		super(QueryParameters.UC_ID_PROJEKT) ;
		setTransformer(new ProjectEntryTransformer()) ;
	}

	public ProjectQuery(IParameterCall parameterCall) throws NamingException {		
		super(QueryParameters.UC_ID_PROJEKT) ;
		setTransformer(new ProjectEntryTransformer()) ;
		this.parameterCall = parameterCall ;
	}
	
	@Autowired 
	public void setParameterCall(IParameterCall parameterCall) {
		this.parameterCall = parameterCall ;
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
