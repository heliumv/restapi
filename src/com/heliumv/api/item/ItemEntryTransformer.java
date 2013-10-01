package com.heliumv.api.item;

import com.heliumv.api.BaseFLRTransformer;
import com.heliumv.factory.query.TableColumnInformationMapper;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class ItemEntryTransformer extends BaseFLRTransformer<ItemEntry> {

	private TableColumnInformationMapper flrMapper = new TableColumnInformationMapper() ;
	
	@Override
	public ItemEntry transformOne(Object[] flrObject, TableColumnInformation columnInformation) {
		ItemEntry entry = new ItemEntry() ;

		flrMapper.setTableColumnInformation(columnInformation) ;
		flrMapper.setValues(entry, flrObject);
		return entry ;
	}
}
