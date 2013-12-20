package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IAuftragCall;
import com.heliumv.factory.IGlobalInfo;
import com.lp.server.auftrag.service.AuftragDto;
import com.lp.server.auftrag.service.AuftragFac;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class AuftragCall extends BaseCall<AuftragFac> implements IAuftragCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public AuftragCall() {
		super(AuftragFacBean) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(rechtOder={RechteFac.RECHT_AUFT_AUFTRAG_R, RechteFac.RECHT_AUFT_AUFTRAG_CUD})	
	public AuftragDto auftragFindByPrimaryKeyOhneExc(Integer orderId) throws NamingException {
		return getFac().auftragFindByPrimaryKey(orderId) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(rechtOder={RechteFac.RECHT_AUFT_AUFTRAG_R, RechteFac.RECHT_AUFT_AUFTRAG_CUD})	
	public AuftragDto auftragFindByCnr(String cnr) throws NamingException, RemoteException {
		return getFac().auftragFindByMandantCNrCNrOhneExc(globalInfo.getTheClientDto().getMandant(), cnr);
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(rechtOder={RechteFac.RECHT_AUFT_AUFTRAG_R, RechteFac.RECHT_AUFT_AUFTRAG_CUD})	
	public AuftragDto auftragFindByCnr(String cnr, TheClientDto theClientDto)
			throws NamingException, RemoteException {
		return getFac().auftragFindByMandantCNrCNrOhneExc(theClientDto.getMandant(), cnr);
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(recht=RechteFac.RECHT_AUFT_AUFTRAG_CUD)	
	public String createOrderResponseToString(AuftragDto auftragDto)
			throws NamingException, RemoteException {
		return getFac().createOrderResponseToString(auftragDto, globalInfo.getTheClientDto());
	}
	
	@Override
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(recht=RechteFac.RECHT_AUFT_AUFTRAG_CUD)	
	public String createOrderResponseToString(AuftragDto auftragDto,
			TheClientDto theClientDto) throws NamingException, RemoteException {
		return getFac().createOrderResponseToString(auftragDto, theClientDto);
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(recht=RechteFac.RECHT_AUFT_AUFTRAG_CUD)	
	public String createOrderResponsePost(AuftragDto auftragDto)
			throws RemoteException, NamingException, EJBExceptionLP {
		return getFac().createOrderResponsePost(auftragDto, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(recht=RechteFac.RECHT_AUFT_AUFTRAG_CUD)	
	public String createOrderResponsePost(AuftragDto auftragDto,
			TheClientDto theClientDto) throws RemoteException, NamingException,
			EJBExceptionLP {
		return getFac().createOrderResponsePost(auftragDto, theClientDto);
	}
}
