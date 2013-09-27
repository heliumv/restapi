package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

import com.lp.server.system.service.PaneldatenDto;

public class ItemPropertyEntryMapper {
	public ItemPropertyEntry mapEntry(PaneldatenDto paneldatenDto) {
		return mapEntryImpl(paneldatenDto) ;
	}
	
	public List<ItemPropertyEntry> mapEntry(PaneldatenDto[] dtos) {
		List<ItemPropertyEntry> properties = new ArrayList<ItemPropertyEntry>() ;
		if(dtos == null) return properties ;
		
		for (PaneldatenDto paneldatenDto : dtos) {
			properties.add(mapEntryImpl(paneldatenDto)) ;
		}
		
		return properties ;
	}

	private ItemPropertyEntry mapEntryImpl(PaneldatenDto paneldatenDto) {
		ItemPropertyEntry entry = new ItemPropertyEntry() ;
		entry.setId(paneldatenDto.getIId()) ;
		entry.setDatatype(paneldatenDto.getCDatentypkey()) ;
		entry.setContent(paneldatenDto.getXInhalt()) ;
		return entry ;
	}
}
