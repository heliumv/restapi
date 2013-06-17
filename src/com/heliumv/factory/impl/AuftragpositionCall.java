package com.heliumv.factory.impl;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.lp.server.auftrag.service.AuftragpositionDto;
import com.lp.server.auftrag.service.AuftragpositionFac;

public class AuftragpositionCall extends BaseCall<AuftragpositionFac> implements IAuftragpositionCall {
	public AuftragpositionCall() {
		super(AuftragpositionFacBean) ;
	}
	
	public AuftragpositionDto auftragpositionFindByPrimaryKeyOhneExc(Integer positionId) throws NamingException {
		return getFac().auftragpositionFindByPrimaryKeyOhneExc(positionId) ;
	}
}
