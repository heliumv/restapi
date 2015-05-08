package com.heliumv.factory.query;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.heliumv.api.machine.MachineGroupEntry;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class MachineGroupQuery extends BaseQuery<MachineGroupEntry> {
	public MachineGroupQuery() {
		super(QueryParameters.UC_ID_MASCHINENGRUPPE) ;
	}
	
	@Override
	protected List<FilterKriterium> getRequiredFilters()
			throws NamingException, RemoteException {
		return null;
	}
}
