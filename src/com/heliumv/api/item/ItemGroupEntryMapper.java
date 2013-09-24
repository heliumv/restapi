package com.heliumv.api.item;

import com.heliumv.tools.ShortHelper;
import com.lp.server.artikel.service.ArtgruDto;

public class ItemGroupEntryMapper {
	public ItemGroupEntry mapEntry(ArtgruDto artikelgruppeDto) {
		ItemGroupEntry entry = new ItemGroupEntry() ;
		entry.setId(artikelgruppeDto.getIId()); 
		entry.setCnr(artikelgruppeDto.getCNr());
		entry.setDescription(artikelgruppeDto.getBezeichnung());
		entry.setBookReturn(ShortHelper.isSet(artikelgruppeDto.getBRueckgabe()));
		entry.setCertificationRequired(ShortHelper.isSet(artikelgruppeDto.getBZertifizierung()));
		entry.setParentId(artikelgruppeDto.getArtgruIId());
	
		return entry ;
	}
}
