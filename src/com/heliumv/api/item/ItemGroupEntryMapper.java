package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

import com.heliumv.tools.ShortHelper;
import com.lp.server.artikel.service.ArtgruDto;

public class ItemGroupEntryMapper {
	public ItemGroupEntry mapEntry(ArtgruDto artikelgruppeDto) {
		if(artikelgruppeDto == null) throw new NullPointerException("artikelgruppeDto") ;
		
 		return mapEntryImpl(artikelgruppeDto) ;
	}
	
	public List<ItemGroupEntry> mapEntry(ArtgruDto[] artikelgruppeDtos) {
		List<ItemGroupEntry> entries = new ArrayList<ItemGroupEntry>() ;
		for (ArtgruDto artgruDto : artikelgruppeDtos) {
			entries.add(mapEntryImpl(artgruDto)) ;
		}
		
		return entries ;
	}
	
	public List<ItemGroupEntry> mapEntry(List<ArtgruDto> artikelgruppeDtos) {
		List<ItemGroupEntry> entries = new ArrayList<ItemGroupEntry>() ;
		for (ArtgruDto artgruDto : artikelgruppeDtos) {
			entries.add(mapEntryImpl(artgruDto)) ;
		}
		
		return entries ;
	}
	
	private ItemGroupEntry mapEntryImpl(ArtgruDto artikelgruppeDto) {
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
