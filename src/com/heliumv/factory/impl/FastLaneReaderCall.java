package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IFastLaneReaderCall;
import com.lp.server.system.fastlanereader.service.FastLaneReader;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.util.EJBExceptionLP;

public class FastLaneReaderCall extends BaseCall<FastLaneReader> implements IFastLaneReaderCall {
	
	private String uuid ;
	private Integer usecaseId ;
	
	protected FastLaneReaderCall(String theUuid, Integer theUsecaseId) throws NamingException {
		super(FastLaneReaderBean) ;
		
		uuid = theUuid ;
		usecaseId = theUsecaseId ; 
	}

	public Integer getUsecaseId() {
		return usecaseId ;
	}
	
	public QueryResult setQuery(QueryParameters queryParams) {
		try {
			QueryResult result = getFac().setQuery(uuid, usecaseId, queryParams, Globals.getTheClientDto()) ;
			result.getRowCount() ;
			
			return result ;
		} catch(RemoteException e) {
			e.printStackTrace() ;
		} catch(EJBExceptionLP e) {
			e.printStackTrace() ;
		}
		
		return new QueryResult(new Object[0][0], 0, 0, 0, 0) ;
	}
}
