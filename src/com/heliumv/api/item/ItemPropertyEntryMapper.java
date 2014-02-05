/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
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
