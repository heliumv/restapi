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

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJudgeCall;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.LockMeDto;
import com.lp.server.system.service.TheClientDto;
import com.lp.server.system.service.TheJudgeFac;
import com.lp.util.EJBExceptionLP;

public class JudgeCall extends BaseCall<TheJudgeFac> implements IJudgeCall {
	@Autowired 
	private IGlobalInfo globalInfo ;
	
	public JudgeCall() {
		super(TheJudgeFacBean) ;
	}

	protected boolean hatRechtImpl(String rechtCnr, TheClientDto theClientDto) throws NamingException {
		return getFac().hatRecht(rechtCnr, theClientDto) ;
	}
	
	public boolean hatRecht(String rechtCnr) throws NamingException {
		return hatRechtImpl(rechtCnr, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public boolean hasPersZeiteingabeNurBuchen() throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_PERS_ZEITEINGABE_NUR_BUCHEN, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public boolean hasPersZeiteingabeNurBuchen(TheClientDto theClientDto) throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_PERS_ZEITEINGABE_NUR_BUCHEN, theClientDto) ;
	}

	@Override
	public boolean hasFertDarfLosErledigen() throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_FERT_DARF_LOS_ERLEDIGEN, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public boolean hasFertDarfLosErledigen(TheClientDto theClientDto) throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_FERT_DARF_LOS_ERLEDIGEN, theClientDto) ;
	}
	
	public boolean hasFertLosCUD() throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_FERT_LOS_CUD, globalInfo.getTheClientDto()) ;
	}
	
	
	@Override
	public boolean hasFertLosCUD(TheClientDto theClientDto) throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_FERT_LOS_CUD, theClientDto) ;
	}

	@Override
	public boolean hasFertDarfSollmaterialCUD() throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_FERT_DARF_SOLLMATERIAL_CUD, globalInfo.getTheClientDto()) ;
	}

	@Override
	public boolean hasFertDarfSollmaterialCUD(TheClientDto theClientDto)
			throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_FERT_DARF_SOLLMATERIAL_CUD, theClientDto) ;
	}

	@Override
	public boolean hasFertDarfIstmaterialManuellNachbuchen()
			throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_FERT_LOS_DARF_ISTMATERIAL_MANUELL_NACHBUCHEN, globalInfo.getTheClientDto()) ;
	}

	@Override
	public boolean hasFertDarfIstmaterialManuellNachbuchen(
			TheClientDto theClientDto) throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_FERT_LOS_DARF_ISTMATERIAL_MANUELL_NACHBUCHEN, theClientDto) ;
	}

	@Override
	public boolean hasPersSichtbarkeitAbteilung() throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_PERS_SICHTBARKEIT_ABTEILUNG, globalInfo.getTheClientDto());
	}

	@Override
	public boolean hasPersSichtbarkeitAbteilung(TheClientDto theClientDto) throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_PERS_SICHTBARKEIT_ABTEILUNG, theClientDto);		
	}
	
	@Override
	public boolean hasPersSichtbarkeitAlle() throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_PERS_SICHTBARKEIT_ALLE, globalInfo.getTheClientDto());
	}
	
	@Override
	public boolean hasPersSichtbarkeitAlle(TheClientDto theClientDto) throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_PERS_SICHTBARKEIT_ALLE, theClientDto);
	}

	@Override
	public boolean hasPersDarfKommtGehtAendern() throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_PERS_DARF_KOMMT_GEHT_AENDERN, globalInfo.getTheClientDto());
	}

	@Override
	public boolean hasPersDarfKommtGehtAendern(TheClientDto theClientDto)
			throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_PERS_DARF_KOMMT_GEHT_AENDERN, theClientDto) ;
	}	
	
	/**
	 * Versucht den Datensatz mit der angegebenen Id in der Tabelle zu sperren.</br>
	 * <p>Wirft Exception wenn bereits ein Lock vorhanden ist</p>
	 * 
	 * @param lockedTable HelperClient.LOCKME_***
	 * @param id
	 * @throws NamingException
	 * @throws EJBExceptionLP
	 */
	public void addLock(String lockedTable, Integer id) throws NamingException, RemoteException, EJBExceptionLP {
		LockMeDto lockMeDto = buildLockMeDto(lockedTable, id) ;
		getFac().addLockedObject(lockMeDto, globalInfo.getTheClientDto());
	}
	
	public void removeLock(String lockedTable, Integer id) throws NamingException, RemoteException, EJBExceptionLP {
		LockMeDto lockmeDto = buildLockMeDto(lockedTable, id) ;
		getFac().removeLockedObject(lockmeDto);
		
	}
	
	private LockMeDto buildLockMeDto(String lockedTable, Integer id) {
		LockMeDto lockmeDto = new LockMeDto(
				lockedTable, id.toString(), globalInfo.getTheClientDto().getIDUser()) ;
		lockmeDto.setPersonalIIdLocker(globalInfo.getTheClientDto().getIDPersonal()) ;
		return lockmeDto ;
	}
}
