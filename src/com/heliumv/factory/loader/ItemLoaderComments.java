package com.heliumv.factory.loader;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.factory.IArtikelkommentarCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.ArtikelkommentarDto;

public class ItemLoaderComments implements IItemLoaderAttribute {
	@Autowired
	private IArtikelkommentarCall artikelkommentarCall ;
	
	@Override
	public ItemEntry load(ItemEntry entry, ArtikelDto artikelDto) {
		try {
			List<String> itemComments = new ArrayList<String>() ;
			List<ArtikelkommentarDto> comments = artikelkommentarCall
					.artikelkommentarFindByArtikelIId(artikelDto.getIId()) ;
			for (ArtikelkommentarDto artikelkommentarDto : comments) {
				if("text/html".equals(StringHelper.trim(artikelkommentarDto.getDatenformatCNr()))) {
					itemComments.add(
							artikelkommentarDto.getArtikelkommentarsprDto().getXKommentar()) ;
				}
			}
			
			if(itemComments.size() > 0) {
				if(entry.getComments() == null) {
					entry.setComments(itemComments) ;
				} else {
					entry.getComments().addAll(itemComments) ;
				}
			}
		} catch(NamingException e) {			
		} catch(RemoteException e) {
		}

		return entry ;
	}
}
