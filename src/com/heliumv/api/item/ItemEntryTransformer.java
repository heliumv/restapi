package com.heliumv.api.item;

import java.math.BigDecimal;

import com.heliumv.api.BaseFLRTransformer;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class ItemEntryTransformer extends BaseFLRTransformer<ItemEntry> {

	@Override
	public ItemEntry transformOne(Object[] flrObject, TableColumnInformation columnInformation) {
		ItemEntry entry = new ItemEntry() ;
		entry.setId((Integer) flrObject[0]); 
		entry.setCnr((String) flrObject[1]) ;
		entry.setBillOfMaterialType((String) flrObject[2]);

		if(columnInformation.existsColumnName("lp.kurzbezeichnung")){
			entry.setShortName((String)flrObject[columnInformation.getViewIndex("lp.kurzbezeichnung")] );
		}
		
		if(columnInformation.existsColumnName("bes.artikelbezeichnung")){
			entry.setDescription((String)flrObject[columnInformation.getViewIndex("bes.artikelbezeichnung")] );
		}

		if(columnInformation.existsColumnName("lp.artikelgruppeInAbmessung")){
			entry.setItemgroupCnr((String)flrObject[columnInformation.getViewIndex("lp.artikelgruppeInAbmessung")] );
		}

		if(columnInformation.existsColumnName("lp.artikelgruppe")){
			entry.setItemgroupCnr((String)flrObject[columnInformation.getViewIndex("lp.artikelgruppe")] );
		}

		if(columnInformation.existsColumnName("artikel.zusatzbez")){
			entry.setDescription2((String)flrObject[columnInformation.getViewIndex("artikel.zusatzbez")] );
		}

		if(columnInformation.existsColumnName("lp.artikelklasse")){
			entry.setItemclassCnr((String)flrObject[columnInformation.getViewIndex("lp.artikelklasse")] );
		}

//		entry.setDescription2((String) flrObject[4]);	
		
		if(columnInformation.existsColumnName("lp.lagerstand")) {
			entry.setStockAmount((BigDecimal) flrObject[columnInformation.getViewIndex("lp.lagerstand")]);
		}

		if(columnInformation.existsColumnName("lp.preis")) {
			entry.setCosts((BigDecimal) flrObject[columnInformation.getViewIndex("lp.preis")]);		
		}

		if(columnInformation.existsColumnName("Icon")) {
			int index = columnInformation.getViewIndex("Icon") ;
			if(flrObject[index] instanceof Boolean) {
				entry.setAvailable((Boolean) flrObject[index]) ;
			} else {
				entry.setAvailable(true) ;			
			}			
		}
		
		return entry ;
	}
}
