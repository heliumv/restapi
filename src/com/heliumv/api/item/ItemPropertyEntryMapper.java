package com.heliumv.api.item;

import java.util.ArrayList;
import java.util.List;

import com.heliumv.factory.legacy.PaneldatenPair;
import com.heliumv.tools.ShortHelper;
import com.lp.server.system.service.PanelbeschreibungDto;
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

	public List<ItemPropertyEntry> mapEntry(List<PaneldatenPair> panelEntries) {
		List<ItemPropertyEntry> properties = new ArrayList<ItemPropertyEntry>() ;

		for (PaneldatenPair panelPair : panelEntries) {
			properties.add(mapEntryImpl(panelPair)) ;
		}
		return properties ;
	}
	
	private ItemPropertyEntry mapEntryImpl(PaneldatenPair pair) {
		ItemPropertyEntry entry = mapEntryImpl(pair.getPaneldatenDto()) ;
		PanelbeschreibungDto beschreibungDto = pair.getPanelbeschreibungDto() ;
		entry.setName(beschreibungDto.getCDruckname()) ;
		entry.setMandatory(ShortHelper.isSet(beschreibungDto.getBMandatory())) ;
		entry.setItemgroupId(beschreibungDto.getArtgruIId()) ;
		return entry ;
	}
	
	private ItemPropertyEntry mapEntryImpl(PaneldatenDto paneldatenDto) {
		ItemPropertyEntry entry = new ItemPropertyEntry() ;
		entry.setId(paneldatenDto.getIId()) ;
		entry.setDatatype(paneldatenDto.getCDatentypkey()) ;
		entry.setContent(paneldatenDto.getXInhalt()) ;
		return entry ;
	}
}
