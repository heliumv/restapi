package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IFastLaneReaderCall;
import com.lp.server.system.fastlanereader.service.FastLaneReader;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.server.util.fastlanereader.service.query.SortierKriterium;
import com.lp.server.util.fastlanereader.service.query.TableInfo;
import com.lp.util.EJBExceptionLP;

public abstract class FastLaneReaderCall extends BaseCall<FastLaneReader> implements IFastLaneReaderCall {
	
	private String uuid ;
	private Integer usecaseId ;
	
	protected FastLaneReaderCall(Integer theUsercaseId) {
		this(UUID.randomUUID().toString(), theUsercaseId) ;
	}
	
	protected FastLaneReaderCall(String theUuid, Integer theUsecaseId) {
		super(FastLaneReaderBean) ;
		
		uuid = theUuid ;
		usecaseId = theUsecaseId ; 
	}

	public Integer getUsecaseId() {
		return usecaseId ;
	}
	
	public QueryResult setQuery(QueryParameters queryParams) {
		installRequiredFilters(queryParams) ;
		
		try {
			QueryResult result = getFac().setQuery(uuid, usecaseId, queryParams, Globals.getTheClientDto()) ;
			result.getRowCount() ;
			
			return result ;
		} catch(RemoteException e) {
			e.printStackTrace() ;
		} catch(EJBExceptionLP e) {
			e.printStackTrace() ;
		} catch(NamingException e) {
			e.printStackTrace() ;
		}
		
		return new QueryResult(new Object[0][0], 0, 0, 0, 0) ;
	}
	
	public QueryParameters getDefaultQueryParameters() {
		return getDefaultQueryParameters(new FilterBlock(new FilterKriterium[0], " AND ")) ;
	}
	
	public QueryParameters getDefaultQueryParameters(FilterBlock filterCrits) {
		ArrayList<?> listOfExtraData = new ArrayList() ;
		SortierKriterium[] sortCrits = new SortierKriterium[0] ;
		QueryParameters params = new QueryParameters(
				getUsecaseId(), sortCrits, filterCrits, 0, listOfExtraData) ;

		return params ;		
	}
	
	
	public void getTableInfo() throws NamingException, RemoteException {
		TableInfo info = getFac().getTableInfo(uuid, usecaseId, Globals.getTheClientDto()) ;
		System.out.println("" + info.getDataBaseColumnNames()) ;
	}
	
	private void installRequiredFilters(QueryParameters queryParams) {
		List<FilterKriterium> requiredFilters = getRequiredFilters() ;
		if(requiredFilters == null || requiredFilters.size() == 0) return ;
		
		FilterKriterium[] knownFilters = queryParams.getFilterBlock().filterKrit ;
		for(int i = 0 ; knownFilters != null && i < knownFilters.length; i++) {
			requiredFilters.add(knownFilters[i]) ;
		}
		queryParams.getFilterBlock().filterKrit = requiredFilters.toArray(new FilterKriterium[0]);
	}
	
	/**
	 * Jene Filter einhängen, die automatisch (und immer!) notwendig sind damit richtige 
	 * Daten geliefert werden. Beispielsweise der Mandant.
	 * 
	 * @return null, oder eine (auch leere) Liste von immer notwendigen Filtern
	 */
	protected abstract List<FilterKriterium> getRequiredFilters() ;
}
