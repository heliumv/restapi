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
package com.heliumv.factory.loader;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.item.ItemEntryInternal;
import com.heliumv.api.item.ItemEntryMapper;
import com.heliumv.factory.IArtikelCall;
import com.lp.server.artikel.service.ArtikelDto;

public class ArtikelLoaderCall implements IArtikelLoaderCall {
	@Autowired
	private IArtikelCall artikelCall ;
	@Autowired
	private ItemEntryMapper itemEntryMapper ;
	
	@Override
	public ItemEntryInternal artikelFindByCNrOhneExc(String cnr) throws NamingException, RemoteException {
		return artikelFindByCNrOhneExc(cnr, new HashSet<IItemLoaderAttribute>());
	}

	@Override
	public ItemEntryInternal artikelFindByCNrOhneExc(
			String cnr, Set<IItemLoaderAttribute> attributes) throws NamingException, RemoteException {

		ArtikelDto artikelDto = artikelCall.artikelFindByCNrOhneExc(cnr) ;
		if(artikelDto == null) return null ;

		if(artikelDto.getArtgruIId() != null) {
			artikelDto.setArtgruDto(
					artikelCall.artikelgruppeFindByPrimaryKeyOhneExc(artikelDto.getArtgruIId())) ;
		}
		if(artikelDto.getArtklaIId() != null) {
			artikelDto.setArtklaDto(artikelCall.artikelklasseFindByPrimaryKeyOhneExc(artikelDto.getArtklaIId()));
		}
		
		ItemEntryInternal entry = itemEntryMapper.mapEntry(artikelDto) ;
		
		for (IItemLoaderAttribute loaderAttribute : attributes) {
			loaderAttribute.load(entry, artikelDto) ;
		}

		return entry ;
	}
}
