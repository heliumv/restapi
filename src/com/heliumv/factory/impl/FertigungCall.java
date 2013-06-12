package com.heliumv.factory.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IFertigungCall;
import com.lp.server.fertigung.service.BucheSerienChnrAufLosDto;
import com.lp.server.fertigung.service.FertigungFac;
import com.lp.server.fertigung.service.LosDto;
import com.lp.server.fertigung.service.LosablieferungDto;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class FertigungCall extends BaseCall<FertigungFac> implements IFertigungCall {
	public FertigungCall() {
		super(FertigungFacBean) ;
	}

	
	@Override
	public void bucheMaterialAufLos(LosDto losDto, BigDecimal menge)
			throws RemoteException {
		bucheMaterialAufLos(losDto, menge, false, false, true, null, false) ;		
	}


	@Override
	public void bucheMaterialAufLos(LosDto losDto, BigDecimal menge,
			boolean bHandausgabe,
			boolean bNurFehlmengenAnlegenUndReservierungenLoeschen,
			boolean bUnterstuecklistenAbbuchen, 
			ArrayList<BucheSerienChnrAufLosDto> bucheSerienChnrAufLosDtos,
			boolean throwExceptionWhenCreate) throws RemoteException {
		try {
			getFac().bucheMaterialAufLos(losDto, menge, bHandausgabe, 
					bNurFehlmengenAnlegenUndReservierungenLoeschen, bUnterstuecklistenAbbuchen, 
					Globals.getTheClientDto(), bucheSerienChnrAufLosDtos, throwExceptionWhenCreate) ;
		} catch(NamingException e) {
			e.printStackTrace() ;
		}
	}
	
	
	@Override
	public LosablieferungDto createLosablieferung(
			LosablieferungDto losablieferungDto, boolean bErledigt) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().createLosablieferung(losablieferungDto, Globals.getTheClientDto(), bErledigt) ;
	}


	@Override
	public LosDto losFindByPrimaryKeyOhneExc(Integer losId) {
		LosDto losDto = null ;
		try {
			losDto = getFac().losFindByPrimaryKeyOhneExc(losId) ;
			if(losDto != null) {
				if(!losDto.getMandantCNr().equals(Globals.getTheClientDto().getMandant())) {
					losDto = null ;
				}
			}
		} catch(NamingException e) {
		}
		return losDto ;
	}	
}
