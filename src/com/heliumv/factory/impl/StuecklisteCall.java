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

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IStuecklisteCall;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.stueckliste.service.KundenStuecklistepositionDto;
import com.lp.server.stueckliste.service.MontageartDto;
import com.lp.server.stueckliste.service.StuecklisteDto;
import com.lp.server.stueckliste.service.StuecklisteFac;
import com.lp.server.stueckliste.service.StuecklistepositionDto;
import com.lp.server.system.service.LocaleFac;
import com.lp.util.EJBExceptionLP;

public class StuecklisteCall extends BaseCall<StuecklisteFac> implements IStuecklisteCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public StuecklisteCall() {
		super(StuecklisteFacBean) ;
	}

	@Override
	public StuecklisteDto stuecklisteFindByPrimaryKey(Integer stuecklisteId)
			throws RemoteException, NamingException {
		return getFac().stuecklisteFindByPrimaryKey(stuecklisteId, globalInfo.getTheClientDto());
	}

	@Override
	public MontageartDto[] montageartFindByMandantCNr() throws RemoteException,
			NamingException, EJBExceptionLP {
		return getFac().montageartFindByMandantCNr(globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public StuecklistepositionDto[] stuecklistepositionFindByStuecklisteIId(
			Integer stuecklisteIId) throws RemoteException, NamingException {
		return getFac().stuecklistepositionFindByStuecklisteIId(
				stuecklisteIId, globalInfo.getTheClientDto()) ;
	}	
	
	@Override
	public List<KundenStuecklistepositionDto> stuecklistepositionFindByStuecklisteIIdAllData(
			Integer stuecklisteIId) throws RemoteException, NamingException {
		return getFac().stuecklistepositionFindByStuecklisteIIdAllData(
				stuecklisteIId, false, globalInfo.getTheClientDto()) ;
	}

	@Override
	public List<KundenStuecklistepositionDto> stuecklistepositionFindByStuecklisteIIdAllData(
			Integer stuecklisteIId, boolean withPrice) throws RemoteException, NamingException {
		return getFac().stuecklistepositionFindByStuecklisteIIdAllData(
				stuecklisteIId, withPrice, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public StuecklistepositionDto stuecklistepositionFindByPrimaryKey(
			Integer iId) throws RemoteException, NamingException, EJBExceptionLP {
		return getFac().stuecklistepositionFindByPrimaryKey(iId, globalInfo.getTheClientDto());
	}
	
	@Override
	@HvModul(modul=LocaleFac.BELEGART_STUECKLISTE)
	@HvJudge(recht=RechteFac.RECHT_STK_STUECKLISTE_CUD)
	public Integer createStuecklisteposition(
			StuecklistepositionDto stuecklistepositionDto)
			throws EJBExceptionLP, NamingException, RemoteException {
		return getFac().createStuecklisteposition(
				stuecklistepositionDto, globalInfo.getTheClientDto());
	}
	
	@Override
	@HvModul(modul=LocaleFac.BELEGART_STUECKLISTE)
	@HvJudge(recht=RechteFac.RECHT_STK_STUECKLISTE_CUD)
	public void updateStuecklisteposition(StuecklistepositionDto originalDto,
			StuecklistepositionDto aenderungDto) throws EJBExceptionLP,
			RemoteException, NamingException {
		getFac().updateStuecklisteposition(originalDto, aenderungDto, globalInfo.getTheClientDto());
	}

	@Override
	public void removeStuecklisteposition(StuecklistepositionDto originalDto,
			StuecklistepositionDto removePositionDto) throws EJBExceptionLP, RemoteException, NamingException {
		getFac().removeStuecklisteposition(originalDto, removePositionDto, globalInfo.getTheClientDto());
	}	
}
