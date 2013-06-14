package com.heliumv.factory.query;

import java.util.List;

import com.heliumv.api.BaseFLRTransformer;
import com.heliumv.api.order.OrderEntry;
import com.heliumv.api.order.OrderpositionEntry;
import com.heliumv.api.order.OrderpositionEntryTransformer;
import com.heliumv.factory.impl.FastLaneReaderCall;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

public class AuftragpositionQuery extends FastLaneReaderCall {

	private BaseFLRTransformer<OrderpositionEntry> entryTransformer = new OrderpositionEntryTransformer() ;
	
	public AuftragpositionQuery() {
		super(QueryParameters.UC_ID_AUFTRAGPOSITION) ;
	}

	public List<OrderpositionEntry> getResultList(QueryResult result) {
		return entryTransformer.transform(result.getRowData()) ;
	}


	@Override
	protected List<FilterKriterium> getRequiredFilters() {
		// TODO Auto-generated method stub
		return null;
	}
}
