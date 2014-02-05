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
