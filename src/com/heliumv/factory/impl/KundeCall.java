package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IKundeCall;
import com.lp.server.partner.service.KundeDto;
import com.lp.server.partner.service.KundeFac;

public class KundeCall extends BaseCall<KundeFac> implements IKundeCall  {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public KundeCall() {
		super(KundeFacBean) ;
	}
	
	public List<KundeDto> kundeFindByKbezMandantCnr(String kbez) throws RemoteException, NamingException {
		return getFac().kundeFindByKbezMandantCnr(kbez, globalInfo.getTheClientDto()) ;
	}
	
	public KundeDto kundeFindByPrimaryKeyOhneExc(Integer customerId) throws RemoteException, NamingException {
		return getFac().kundeFindByPrimaryKeyOhneExc(customerId, globalInfo.getTheClientDto()) ;
	}
}
