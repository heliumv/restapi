package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

import com.lp.server.artikel.service.IArtikellisteFLRData;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class ItemV11EntryTransformer extends ItemEntryTransformer {
	private IArtikellisteFLRData[] flrData ;

	public void setFlrData(IArtikellisteFLRData[] flrData) {
		this.flrData = flrData ;
	}
	
	@Override
	public List<ItemEntry> transform(Object[][] flrObjects, TableColumnInformation columnInformation) {
		ArrayList<ItemEntry> entities = new ArrayList<ItemEntry>() ;
		if(flrObjects == null || flrObjects.length == 0) return entities ;

		if(flrObjects.length != flrData.length) {
			throw new IllegalArgumentException(
					"flrData.length (" + flrData.length + ") != flrObjects.length (" + flrObjects.length + ")") ;
		}
		
		int index = 0 ;
		for(Object[] objects : flrObjects) {
			ItemEntry entry = transformOne(objects, columnInformation) ;
			transformFlr(entry, flrData[index]) ;

			entities.add (entry) ;
			++index ;
		}

		return entities ;
	}
	
	public void transformFlr(ItemEntry entry, IArtikellisteFLRData flrData) {
		entry.setUnitCnr(flrData.getEinheitCnr());
		entry.setCustomerItemCnr(flrData.getKundenartikelCnr());
	}	
}
