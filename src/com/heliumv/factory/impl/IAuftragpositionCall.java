package com.heliumv.factory.impl;

import javax.naming.NamingException;

import com.lp.server.auftrag.service.AuftragpositionDto;

public interface IAuftragpositionCall {
	AuftragpositionDto auftragpositionFindByPrimaryKeyOhneExc(Integer positionId) throws NamingException ;
}
