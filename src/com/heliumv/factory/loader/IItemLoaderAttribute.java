package com.heliumv.factory.loader;

import com.heliumv.api.item.ItemEntry;
import com.lp.server.artikel.service.ArtikelDto;

public interface IItemLoaderAttribute {
	ItemEntry load(ItemEntry entry, ArtikelDto artikelDto) ;
}
