package com.heliumv.factory;

import javax.naming.NamingException;

import com.lp.server.auftrag.service.AuftragpositionDto;

public interface IAuftragpositionCall {
	AuftragpositionDto auftragpositionFindByPrimaryKeyOhneExc(Integer positionId) throws NamingException ;
}
