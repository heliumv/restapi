package com.heliumv.factory.loader;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.factory.IArtikelkommentarCall;
import com.lp.server.artikel.service.ArtikelDto;

public class ItemLoaderProperties implements IItemLoaderAttribute {

	@Autowired
	private IArtikelkommentarCall artikelkommentarCall ;
	
	@Override
	public ItemEntry load(ItemEntry entry, ArtikelDto artikelDto) {
		return entry ;
	}
}
