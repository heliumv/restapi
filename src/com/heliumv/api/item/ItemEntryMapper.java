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
		entry.setRevision(artikelDto.getCRevision());
		entry.setReferenceNumber(artikelDto.getCReferenznr());
		entry.setIndex(artikelDto.getCIndex());
		
		mapItemGroup(entry, artikelDto) ;
		mapItemClass(entry, artikelDto) ;
		
		return entry ;
	}
	
	private void mapItemGroup(ItemEntry entry, ArtikelDto artikelDto) {
		if(artikelDto.getArtgruDto() != null) {
			entry.setItemgroupCnr(artikelDto.getArtgruDto().getCNr()) ;
		}		
	}
	
	private void mapItemClass(ItemEntry entry, ArtikelDto artikelDto) {
		if(artikelDto.getArtklaDto() != null) {
			entry.setItemclassCnr(artikelDto.getArtklaDto().getCNr()) ;			
		}
	}
}
