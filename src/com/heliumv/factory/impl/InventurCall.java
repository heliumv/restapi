package com.heliumv.factory.impl;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.annotation.HvModul;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IInventurCall;
import com.lp.server.artikel.service.InventurDto;
import com.lp.server.artikel.service.InventurFac;
import com.lp.server.system.service.LocaleFac;
import com.lp.util.EJBExceptionLP;

public class InventurCall extends BaseCall<InventurFac> implements IInventurCall {
	@Autowired
	private IGlobalInfo globalInfo ;

	public InventurCall() {
		super(InventurFacBean) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_INVENTUR) 
	public InventurDto[] inventurFindOffene(String mandantCNr)
			throws NamingException, EJBExceptionLP {
		return getFac().inventurFindOffene(mandantCNr) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_INVENTUR) 
	public InventurDto[] inventurFindOffene() throws NamingException,
			EJBExceptionLP {
		return inventurFindOffene(globalInfo.getMandant()) ;
	}

}
