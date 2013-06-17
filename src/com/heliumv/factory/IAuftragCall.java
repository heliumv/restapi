package com.heliumv.factory;

import javax.naming.NamingException;

import com.lp.server.auftrag.service.AuftragDto;

public interface IAuftragCall {
	AuftragDto auftragFindByPrimaryKeyOhneExc(Integer orderId) throws NamingException ;

}
