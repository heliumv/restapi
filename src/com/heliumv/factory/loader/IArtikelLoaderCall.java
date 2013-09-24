package com.heliumv.factory.loader;

import java.rmi.RemoteException;
import java.util.Set;

import javax.naming.NamingException;

import com.heliumv.api.item.ItemEntry;

public interface IArtikelLoaderCall  {
//	enum Attribute { COMMENTS, PRICE, PROPERTIES } ;	

	ItemEntry artikelFindByCNrOhneExc(String cnr) throws RemoteException, NamingException ;

	ItemEntry artikelFindByCNrOhneExc(String cnr, Set<IItemLoaderAttribute> attributes) throws RemoteException, NamingException ;	
}
