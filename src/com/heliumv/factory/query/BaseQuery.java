package com.heliumv.factory.query;

import java.util.List;

import com.heliumv.api.BaseFLRTransformer;
import com.heliumv.factory.impl.FastLaneReaderCall;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

public abstract class BaseQuery<T> extends FastLaneReaderCall  {

	private BaseFLRTransformer<T> entryTransformer ;
	
	protected BaseQuery(Integer usecaseId) {
		super(usecaseId) ;
	}
	
	protected BaseQuery(String uuid, Integer usecaseId) {
		super(uuid, usecaseId) ;
	}
	
	public void setTransformer(BaseFLRTransformer<T> transformer) {
		entryTransformer = transformer ;
	}
	
	public List<T> getResultList(QueryResult result) {
		return entryTransformer.transform(result.getRowData(), getTableColumnInfo()) ;
	}
}
