package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IPartnerCall;
import com.lp.server.partner.service.PartnerDto;
import com.lp.server.partner.service.PartnerFac;
import com.lp.util.EJBExceptionLP;

public class PartnerCall extends BaseCall<PartnerFac> implements IPartnerCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public PartnerCall() {
		super(PartnerFacBean) ;
	}

	public PartnerDto partnerFindByPrimaryKey(Integer partnerId) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().partnerFindByPrimaryKeyOhneExc(partnerId, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public PartnerDto partnerFindByAnsprechpartnerId(Integer ansprechpartnerId) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().partnerFindByAnsprechpartnerId(ansprechpartnerId, globalInfo.getTheClientDto()) ;
	}

	@Override
	public Integer partnerIdFindByAnsprechpartnerId(Integer ansprechpartnerId)
			throws NamingException, RemoteException {
		return getFac().partnerIdFindByAnsprechpartnerId(ansprechpartnerId);
	}
	
}
