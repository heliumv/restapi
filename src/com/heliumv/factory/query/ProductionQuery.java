package com.heliumv.factory.query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.production.ProductionEntry;
import com.heliumv.api.production.ProductionEntryTransformer;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IParameterCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class ProductionQuery extends BaseQuery<ProductionEntry> {
	@Autowired
	private IParameterCall parameterCall ;
	
	public ProductionQuery() {
		super(QueryParameters.UC_ID_LOS) ;
		setTransformer(new ProductionEntryTransformer()) ;
	}
	
	
	public ProductionQuery(IParameterCall parameterCall) throws NamingException {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_LOS) ;
		setTransformer(new ProductionEntryTransformer()) ;
		this.parameterCall = parameterCall ;
	}
	
			
	@Override
	protected List<FilterKriterium> getRequiredFilters() {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;

		filters.add(getMandantFilter()) ;		
		return filters ;
	}

	private FilterKriterium getMandantFilter() {
		return new FilterKriterium("flrlos.mandant_c_nr", true,
				StringHelper.asSqlString( Globals.getTheClientDto().getMandant()),
				FilterKriterium.OPERATOR_EQUAL, false);
	}
}
