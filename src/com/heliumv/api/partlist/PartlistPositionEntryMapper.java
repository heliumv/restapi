package com.heliumv.api.partlist;

import com.lp.server.stueckliste.service.KundenStuecklistepositionDto;
import com.lp.server.stueckliste.service.StuecklistepositionDto;

public class PartlistPositionEntryMapper {
	public PartlistPositionEntry mapEntry(KundenStuecklistepositionDto positionDto) {
		PartlistPositionEntry entry = new PartlistPositionEntry() ;
		if(positionDto != null) {
			StuecklistepositionDto stkposDto = positionDto.getPositionDto() ;
			mapEntryImpl(entry, stkposDto) ;
			entry.setSalesPrice(positionDto.getVkPreis());
			entry.setCustomerItemCnr(positionDto.getKundenartikelNummer());
			entry.setItemHidden(positionDto.isArtikelVersteckt());
			entry.setItemLocked(positionDto.isArtikelGesperrt());
		}
		return entry ;
	}
	
	public PartlistPositionEntry mapEntry(StuecklistepositionDto positionDto) {
		PartlistPositionEntry entry = new PartlistPositionEntry() ;
		if(positionDto != null) {
			mapEntryImpl(entry, positionDto) ;
		}
		return entry ;
	}
	
	protected PartlistPositionEntry mapEntryImpl(PartlistPositionEntry entry, StuecklistepositionDto positionDto) {
		entry.setiSort(positionDto.getISort()) ;
		entry.setId(positionDto.getIId());
		entry.setAmount(positionDto.getNMenge());
		entry.setCalcPrice(positionDto.getNKalkpreis());
		entry.setCnr(positionDto.getArtikelDto().getCNr());
		entry.setComment(positionDto.getCKommentar());
		if (positionDto.getArtikelDto().getArtikelsprDto() != null) {
			entry.setDescription(positionDto.getArtikelDto().getArtikelsprDto().getCBez());
		} else {
			entry.setDescription("");
		}
		entry.setPosition(positionDto.getCPosition()) ;
		if(positionDto.getArtikelDto() != null) {
			entry.setUnitCnr(positionDto.getArtikelDto().getEinheitCNr());
			entry.setItemId(positionDto.getArtikelDto().getIId());
		} else {
			entry.setUnitCnr(positionDto.getEinheitCNr());
		}
		
		return entry ;
	}
}
