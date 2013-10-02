package com.heliumv.api.item;

import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.ArtikelsprDto;

public class ItemEntryMapper {

	public ItemEntry mapEntry(ArtikelDto artikelDto) {
		ItemEntry entry = new ItemEntry() ;
		entry.setId(artikelDto.getIId()); 
		entry.setCnr(artikelDto.getCNr());
		ArtikelsprDto artikelSprDto = artikelDto.getArtikelsprDto() ;
		if(artikelSprDto != null) {
			entry.setName(artikelSprDto.getCBez());
			entry.setShortName(artikelSprDto.getCKbez());
			entry.setDescription(artikelSprDto.getCZbez());
			entry.setDescription2(artikelSprDto.getCZbez2());
		}
		entry.setHidden(artikelDto.getBVersteckt() == null ? false : artikelDto.getBVersteckt() > 0);
		entry.setUnitCnr(artikelDto.getEinheitCNr());
		entry.setTypeCnr(artikelDto.getArtikelartCNr());
		
		return entry ;
	}
}
