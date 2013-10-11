package com.heliumv.factory.loader;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.api.item.ItemEntryMapper;
import com.heliumv.factory.IArtikelCall;
import com.lp.server.artikel.service.ArtikelDto;

public class ArtikelLoaderCall implements IArtikelLoaderCall {
	@Autowired
	private IArtikelCall artikelCall ;
	@Autowired
	private ItemEntryMapper itemEntryMapper ;
	
	@Override
	public ItemEntry artikelFindByCNrOhneExc(String cnr) throws NamingException, RemoteException {
		return artikelFindByCNrOhneExc(cnr, new HashSet<IItemLoaderAttribute>());
	}

	@Override
	public ItemEntry artikelFindByCNrOhneExc(
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
		
		ItemEntry entry = itemEntryMapper.mapEntry(artikelDto) ;
		
		for (IItemLoaderAttribute loaderAttribute : attributes) {
			loaderAttribute.load(entry, artikelDto) ;
		}

		return entry ;
	}
}
