package com.heliumv.api.item;

import com.lp.server.artikel.service.LagerDto;

public class StockEntryMapper {
	public StockEntry mapEntry(LagerDto lagerDto) {
		StockEntry entry = new StockEntry() ;
		if(lagerDto != null) {
			entry.setId(lagerDto.getIId()) ;
			entry.setName(lagerDto.getCNr());
			entry.setTypeCnr(lagerDto.getLagerartCNr()) ;
		}
		return entry ;
	}
}
