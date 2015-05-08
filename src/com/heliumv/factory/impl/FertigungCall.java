/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
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
import com.lp.server.fertigung.service.LossollarbeitsplanDto;
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
	
	@Override
	public LosDto losFindByCNrMandantCNrOhneExc(String cNr) throws NamingException {
		return losFindByCNrMandantCNrOhneExc(cNr, globalInfo.getMandant()) ;
	}

	@Override
	public LosDto losFindByCNrMandantCNrOhneExc(String cNr, String mandantCNr) throws NamingException {
		return getFac().losFindByCNrMandantCNrOhneExc(cNr, mandantCNr) ;
	}
	
	@Override
	public LoslagerentnahmeDto[] loslagerentnahmeFindByLosIId(Integer losIId)
			throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().loslagerentnahmeFindByLosIId(losIId) ;
	}
	
	
	@Override
	public void gebeMaterialNachtraeglichAus(
			LossollmaterialDto lossollmaterialDto, LosistmaterialDto losistmaterialDto, 
			List<SeriennrChargennrMitMengeDto> listSnrChnr, boolean reduzierFehlmenge)
		throws NamingException, RemoteException, EJBExceptionLP {
		getFac().gebeMaterialNachtraeglichAus(lossollmaterialDto,
				losistmaterialDto, listSnrChnr, reduzierFehlmenge, globalInfo.getTheClientDto());		
	}
	
	@Override
	public LossollmaterialDto[] lossollmaterialFindByLosIIdOrderByISort(
			Integer losIId) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().lossollmaterialFindByLosIIdOrderByISort(losIId) ;
	}
	
	@Override
	public LosistmaterialDto[] losistmaterialFindByLossollmaterialIId(
			Integer lossollmaterialIId) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().losistmaterialFindByLossollmaterialIId(lossollmaterialIId) ;
	}
	
	@Override
	public void updateLosistmaterialMenge(Integer losistmaterialIId,
			BigDecimal bdMengeNeu) throws NamingException, RemoteException, EJBExceptionLP {
		getFac().updateLosistmaterialMenge(losistmaterialIId, bdMengeNeu, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public LosistmaterialDto createLosistmaterial(
			LosistmaterialDto losistmaterialDto, String sSerienChargennummer) throws NamingException, RemoteException,
			EJBExceptionLP {
		return getFac().createLosistmaterial(losistmaterialDto, sSerienChargennummer, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public LossollmaterialDto lossollmaterialFindByPrimaryKey(Integer iId)
			throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().lossollmaterialFindByPrimaryKeyOhneExc(iId) ;
	}
	
	@Override
	public void updateLossollmaterialMenge(Integer lossollmaterialId, BigDecimal mengeNeu) 
			throws NamingException, RemoteException, EJBExceptionLP {
		LossollmaterialDto dto = getFac().lossollmaterialFindByPrimaryKey(lossollmaterialId) ;
		dto.setNMenge(mengeNeu);
		getFac().updateLossollmaterial(dto, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public LossollarbeitsplanDto lossollarbeitsplanFindByPrimaryKey(Integer iId) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().lossollarbeitsplanFindByPrimaryKey(iId)	;	
	}
	
	public LossollarbeitsplanDto updateLossollarbeitsplan(
			LossollarbeitsplanDto lossollarbeitsplanDto) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().updateLossollarbeitsplan(lossollarbeitsplanDto, globalInfo.getTheClientDto()) ;
	}
}
