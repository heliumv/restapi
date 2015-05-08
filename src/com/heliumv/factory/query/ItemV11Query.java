package com.heliumv.factory.query;

import java.util.List;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.api.item.ItemV11EntryTransformer;
import com.lp.server.artikel.service.ArtikellisteQueryResult;
import com.lp.server.util.fastlanereader.service.query.QueryResult;


public class ItemV11Query extends ItemQuery {
//	@Autowired
//	private ItemV11EntryTransformer entryTransformer ;

//	public ItemV11Query() {
//		setTransformer(entryTransformer) ;
//	}
	
	@Override
	protected List<ItemEntry> transform(QueryResult result) {
		if(result instanceof ArtikellisteQueryResult) {
			prepareTransformer((ArtikellisteQueryResult)result);
		}
		return super.transform(result);
	}
	
	private void prepareTransformer(ArtikellisteQueryResult result) {
		ItemV11EntryTransformer entryTransformer = (ItemV11EntryTransformer) getTransformer() ;
		entryTransformer.setFlrData(result.getFlrData());
	}
	
}
