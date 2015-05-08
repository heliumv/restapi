package com.heliumv.factory.impl;

import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IRechnungCall;
import com.lp.server.rechnung.service.RechnungFac;

public class RechnungCall extends BaseCall<RechnungFac> implements
		IRechnungCall {
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public RechnungCall() {
		super(RechnungFacBean) ;
	}
	
	@Override
	public void repairRechnungZws2276(Integer rechnungId) throws NamingException {
		getFac().repairRechnungZws2276(rechnungId, globalInfo.getTheClientDto()) ;
	}

	@Override
	public List<Integer> repairRechnungZws2276GetList() throws NamingException {
		return getFac().repairRechnungZws2276GetList(globalInfo.getTheClientDto());
	}

}
