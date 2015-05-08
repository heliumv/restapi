package com.heliumv.factory.query;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.heliumv.api.partlist.PartlistPositionEntry;
import com.lp.server.stueckliste.service.StuecklisteFac;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class PartlistPositionQuery extends BaseQuery<PartlistPositionEntry> {
	public PartlistPositionQuery() {
		super(QueryParameters.UC_ID_STUECKLISTEPOSITION) ;
	}
	
	@Override
	protected List<FilterKriterium> getRequiredFilters()
			throws NamingException, RemoteException {
		return null;
	}
	
	public FilterKriterium getPartlistIdFilter(Integer partlistId) {
		if (partlistId == null) return null ;

		return new FilterKriterium(
				StuecklisteFac.FLR_STUECKLISTEPOSITION_FLRSTUECKLISTE + ".i_id",
				true, partlistId + "", FilterKriterium.OPERATOR_EQUAL, false);
	}	
}
