package com.heliumv.factory.query;

import java.util.List;

import com.heliumv.api.order.OrderEntry;
import com.heliumv.api.order.OrderEntryTransformerOffline;
import com.lp.server.auftrag.service.AuftragQueryResult;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

public class AuftragQueryOffline extends AuftragQuery {	
	public AuftragQueryOffline() {
		setTransformer(new OrderEntryTransformerOffline()) ;
	}
	
	@Override
	protected List<OrderEntry> transform(QueryResult result) {
		if(result instanceof AuftragQueryResult) {
			prepareTransformer((AuftragQueryResult)result);
		}
		return super.transform(result);
	}
	
	private void prepareTransformer(AuftragQueryResult result) {
		OrderEntryTransformerOffline offlineTransformer = (OrderEntryTransformerOffline) getTransformer() ;
		offlineTransformer.setFlrData(result.getFlrData());
	}
}
