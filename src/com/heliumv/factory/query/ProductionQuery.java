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
import com.heliumv.factory.impl.FastLaneReaderCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

public class ProductionQuery extends FastLaneReaderCall {
	@Autowired
	private IParameterCall parameterCall ;

	private ProductionEntryTransformer entryTransformer = new ProductionEntryTransformer() ;
	
	public ProductionQuery() {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_LOS) ;
	}
	
	
	public ProductionQuery(IParameterCall parameterCall) throws NamingException {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_LOS) ;
		this.parameterCall = parameterCall ;
	}
	
	public List<ProductionEntry> getResultList(QueryResult result) {
		return entryTransformer.transform(result.getRowData()) ;
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
