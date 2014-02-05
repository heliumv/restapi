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
import com.heliumv.factory.ILieferscheinCall;
import com.lp.server.lieferschein.service.ILieferscheinAviso;
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
	public ILieferscheinAviso createLieferscheinAviso(Integer lieferscheinId,
			TheClientDto theClientDto) throws NamingException, RemoteException {
		return getFac().createLieferscheinAviso(lieferscheinId, theClientDto) ;
	}

	@Override
	public String getLieferscheinAvisoAsString(LieferscheinDto lieferscheinDto, ILieferscheinAviso lieferscheinAviso,
			TheClientDto theClientDto) throws NamingException, RemoteException {
		return getFac().lieferscheinAvisoToString(lieferscheinDto, lieferscheinAviso, theClientDto) ;
//		return getFac().sendLieferscheinAviso(lieferscheinDto, theClientDto) ;
	}
	
	@Override
	public String createLieferscheinAvisoPost(Integer lieferscheinId,
			TheClientDto theClientDto) throws RemoteException, NamingException {
		return getFac().createLieferscheinAvisoPost(lieferscheinId, theClientDto);		
	}	
	
	@Override
	public String createLieferscheinAvisoToString(
			Integer lieferscheinId, TheClientDto theClientDto)
			throws RemoteException, NamingException {
		return getFac().createLieferscheinAvisoToString(lieferscheinId, theClientDto) ;
	}
}
