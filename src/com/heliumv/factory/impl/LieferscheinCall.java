package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.ILieferscheinCall;
import com.lp.server.lieferschein.service.LieferscheinDto;
import com.lp.server.lieferschein.service.LieferscheinFac;
import com.lp.server.system.service.TheClientDto;

public class LieferscheinCall extends BaseCall<LieferscheinFac> implements ILieferscheinCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public LieferscheinCall() {
		super(LieferscheinFacBean) ;
	}

	@Override
	public LieferscheinDto lieferscheinFindByPrimaryKey(Integer lieferscheinId) throws NamingException, RemoteException {
		return lieferscheinFindByPrimaryKey(lieferscheinId, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public LieferscheinDto lieferscheinFindByPrimaryKey(
			Integer lieferscheinId, TheClientDto theClientDto) throws NamingException, RemoteException {
		LieferscheinDto lieferscheinDto = getFac()
				.lieferscheinFindByPrimaryKeyOhneExc(lieferscheinId) ;
		if(lieferscheinDto == null) return lieferscheinDto ;
		
		if(!theClientDto.getMandant().equals(lieferscheinDto.getMandantCNr())) {
			return null ;
		}
		
		return lieferscheinDto ;
	}
	

	@Override
	public LieferscheinDto lieferscheinFindByCNr(String cnr) throws NamingException, RemoteException {
		return getFac().lieferscheinFindByCNrMandantCNr(cnr, globalInfo.getTheClientDto().getMandant()) ;
	}

	@Override
	public LieferscheinDto lieferscheinFindByCNr(
			String cnr, String clientCnr) throws NamingException, RemoteException {
		return getFac().lieferscheinFindByCNrMandantCNr(cnr, clientCnr) ;
	}
	
	@Override
	public String createLieferscheinAviso(LieferscheinDto lieferscheinDto,
			TheClientDto theClientDto) throws NamingException, RemoteException {
		return getFac().createLieferscheinAviso(lieferscheinDto, theClientDto) ;
	}

	@Override
	public void sendLieferscheinAviso(LieferscheinDto lieferscheinDto,
			TheClientDto theClientDto) throws NamingException, RemoteException {
//		return getFac().sendLieferscheinAviso(lieferscheinDto, theClientDto) ;
	}
}
