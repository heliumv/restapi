package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.partner.service.PartnerDto;
import com.lp.util.EJBExceptionLP;

public interface IPartnerCall {
	PartnerDto partnerFindByPrimaryKey(Integer partnerId) throws NamingException, RemoteException, EJBExceptionLP ;
	
	PartnerDto partnerFindByAnsprechpartnerId(Integer ansprechpartnerId) 
			throws EJBExceptionLP, NamingException, RemoteException ; 
	Integer partnerIdFindByAnsprechpartnerId(Integer ansprechpartnerId) throws NamingException, RemoteException ;
}
