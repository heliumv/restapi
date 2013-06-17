package com.heliumv.factory.impl;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IAuftragCall;
import com.lp.server.auftrag.service.AuftragDto;
import com.lp.server.auftrag.service.AuftragFac;

public class AuftragCall extends BaseCall<AuftragFac> implements IAuftragCall {
	public AuftragCall() {
		super(AuftragFacBean) ;
	}

	public AuftragDto auftragFindByPrimaryKeyOhneExc(Integer orderId) throws NamingException {
		return getFac().auftragFindByPrimaryKey(orderId) ;
	}
}
