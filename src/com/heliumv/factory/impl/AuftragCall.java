package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IAuftragCall;
import com.heliumv.factory.IGlobalInfo;
import com.lp.server.auftrag.service.AuftragDto;
import com.lp.server.auftrag.service.AuftragFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class AuftragCall extends BaseCall<AuftragFac> implements IAuftragCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public AuftragCall() {
		super(AuftragFacBean) ;
	}

	@Override
	public AuftragDto auftragFindByPrimaryKeyOhneExc(Integer orderId) throws NamingException {
		return getFac().auftragFindByPrimaryKey(orderId) ;
	}

	@Override
	public AuftragDto auftragFindByCnr(String cnr) throws NamingException, RemoteException {
		return getFac().auftragFindByMandantCNrCNrOhneExc(globalInfo.getTheClientDto().getMandant(), cnr);
	}

	@Override
	public AuftragDto auftragFindByCnr(String cnr, TheClientDto theClientDto)
			throws NamingException, RemoteException {
		return getFac().auftragFindByMandantCNrCNrOhneExc(theClientDto.getMandant(), cnr);
	}

	@Override
	public String createOrderResponseToString(AuftragDto auftragDto)
			throws NamingException, RemoteException {
		return getFac().createOrderResponseToString(auftragDto, globalInfo.getTheClientDto());
	}
	
	@Override
	public String createOrderResponseToString(AuftragDto auftragDto,
			TheClientDto theClientDto) throws NamingException, RemoteException {
		return getFac().createOrderResponseToString(auftragDto, theClientDto);
	}

	@Override
	public String createOrderResponsePost(AuftragDto auftragDto)
			throws RemoteException, NamingException, EJBExceptionLP {
		return getFac().createOrderResponsePost(auftragDto, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public String createOrderResponsePost(AuftragDto auftragDto,
			TheClientDto theClientDto) throws RemoteException, NamingException,
			EJBExceptionLP {
		return getFac().createOrderResponsePost(auftragDto, theClientDto);
	}
}
