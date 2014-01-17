package com.heliumv.factory.query;

import java.util.List;

import com.heliumv.api.order.OrderpositionEntry;
import com.heliumv.api.order.OrderpositionEntryTransformer;
import com.lp.server.auftrag.service.AuftragFac;
import com.lp.server.auftrag.service.AuftragpositionFac;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class AuftragpositionQuery extends BaseQuery<OrderpositionEntry> {

	public AuftragpositionQuery() {
		super(QueryParameters.UC_ID_AUFTRAGPOSITION) ;
		setTransformer(new OrderpositionEntryTransformer()) ;
	}

	@Override
	protected List<FilterKriterium> getRequiredFilters() {
		return null;
	}
	
	public FilterKriterium getOrderIdFilter(Integer orderId) {
		if (orderId == null) return null ;

		return new FilterKriterium(
				AuftragpositionFac.FLR_AUFTRAGPOSITION_FLRAUFTRAG + "."
						+ AuftragFac.FLR_AUFTRAG_I_ID, true,
				orderId.toString(), FilterKriterium.OPERATOR_EQUAL, false) ;		
	}
	
	public FilterKriterium getIsIdentFilter() {
		return new FilterKriterium(
				AuftragpositionFac.FLR_AUFTRAGPOSITION_POSITIONART_C_NR, true, 
				"('Ident', 'Handeingabe')", FilterKriterium.OPERATOR_IN, false) ;
	}
}
