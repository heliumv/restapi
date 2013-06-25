package com.heliumv.factory.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IFertigungCall;
import com.heliumv.factory.IGlobalInfo;
import com.lp.server.artikel.service.SeriennrChargennrMitMengeDto;
import com.lp.server.fertigung.service.BucheSerienChnrAufLosDto;
import com.lp.server.fertigung.service.FertigungFac;
import com.lp.server.fertigung.service.LosDto;
import com.lp.server.fertigung.service.LosablieferungDto;
import com.lp.server.fertigung.service.LosistmaterialDto;
import com.lp.server.fertigung.service.LoslagerentnahmeDto;
import com.lp.server.fertigung.service.LossollmaterialDto;
import com.lp.util.EJBExceptionLP;

public class FertigungCall extends BaseCall<FertigungFac> implements IFertigungCall {

	@Autowired
	private IGlobalInfo globalInfo ;
	
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
					globalInfo.getTheClientDto(), bucheSerienChnrAufLosDtos, throwExceptionWhenCreate) ;
		} catch(NamingException e) {
			e.printStackTrace() ;
		}
	}
	
	
	@Override
	public LosablieferungDto createLosablieferung(
			LosablieferungDto losablieferungDto, boolean bErledigt) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().createLosablieferung(losablieferungDto, globalInfo.getTheClientDto(), bErledigt) ;
	}


	@Override
	public LosDto losFindByPrimaryKeyOhneExc(Integer losId) {
		LosDto losDto = null ;
		try {
			losDto = getFac().losFindByPrimaryKeyOhneExc(losId) ;
			if(losDto != null) {
				if(!losDto.getMandantCNr().equals(globalInfo.getMandant())) {
					losDto = null ;
				}
			}
		} catch(NamingException e) {
		}
		return losDto ;
	}	
	
	public LosDto losFindByCNrMandantCNrOhneExc(String cNr) throws NamingException {
		return losFindByCNrMandantCNrOhneExc(cNr, globalInfo.getMandant()) ;
	}

	public LosDto losFindByCNrMandantCNrOhneExc(String cNr, String mandantCNr) throws NamingException {
		return getFac().losFindByCNrMandantCNrOhneExc(cNr, mandantCNr) ;
	}
	
	public LoslagerentnahmeDto[] loslagerentnahmeFindByLosIId(Integer losIId)
			throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().loslagerentnahmeFindByLosIId(losIId) ;
	}
	
	
	public void gebeMaterialNachtraeglichAus(
			LossollmaterialDto lossollmaterialDto, LosistmaterialDto losistmaterialDto, 
			List<SeriennrChargennrMitMengeDto> listSnrChnr, boolean reduzierFehlmenge)
		throws NamingException, RemoteException, EJBExceptionLP {
		getFac().gebeMaterialNachtraeglichAus(lossollmaterialDto,
				losistmaterialDto, listSnrChnr, reduzierFehlmenge, globalInfo.getTheClientDto());		
	}
}
