package com.heliumv.factory;

import java.util.List;

import javax.naming.NamingException;

public interface IRechnungCall {
	void repairRechnungZws2276(Integer rechnungId) throws NamingException  ;
	
	List<Integer> repairRechnungZws2276GetList() throws NamingException ;

}
