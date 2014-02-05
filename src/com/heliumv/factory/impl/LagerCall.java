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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.ILagerCall;
import com.heliumv.factory.legacy.AllLagerEntry;
import com.lp.server.artikel.service.LagerDto;
import com.lp.server.artikel.service.LagerFac;
import com.lp.util.EJBExceptionLP;

public class LagerCall extends BaseCall<LagerFac> implements ILagerCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public LagerCall() {
		super(LagerFacBean) ;
	}
	
	public Integer artikelIdFindBySeriennummerOhneExc(String serialnumber) throws NamingException, RemoteException {
		Integer itemId = getFac().getArtikelIIdUeberSeriennummer(serialnumber, globalInfo.getTheClientDto()) ;
		if(itemId == null) {
			itemId = getFac().getArtikelIIdUeberSeriennummerAbgang(serialnumber, globalInfo.getTheClientDto()) ;
		}

		return itemId ;
	}

	@Override
	public BigDecimal  getGemittelterGestehungspreisEinesLagers(
			Integer itemId, Integer lagerId) throws NamingException, RemoteException {
		return getFac().getGemittelterGestehungspreisEinesLagers(itemId, lagerId, globalInfo.getTheClientDto()) ;
	}

	@Override
	public LagerDto lagerFindByPrimaryKeyOhneExc(Integer lagerIId) throws NamingException {
		LagerDto lagerDto = getFac().lagerFindByPrimaryKeyOhneExc(lagerIId) ;
		if(lagerDto != null) {
			if(!globalInfo.getTheClientDto().getMandant().equals(lagerDto.getMandantCNr())) {
				lagerDto = null ;
			}
		}

		return lagerDto ;
	}

	@Override
	public BigDecimal getLagerstandOhneExc(Integer itemId, Integer lagerIId)
			throws NamingException, RemoteException {
		return getFac().getLagerstandOhneExc(itemId, lagerIId, globalInfo.getTheClientDto());
	}

	@Override
	public BigDecimal getLagerstandAllerLagerEinesMandanten(Integer itemId, Boolean mitKonsignationsLager) throws NamingException, RemoteException {
		return getFac().getLagerstandAllerLagerEinesMandanten(itemId, mitKonsignationsLager, globalInfo.getTheClientDto()) ;
	}
	
	@Override
	public BigDecimal getPaternosterLagerstand(Integer itemId) throws NamingException, RemoteException {
		return getFac().getPaternosterLagerstand(itemId) ;
	}
	
	@Override
	public boolean hatRolleBerechtigungAufLager(Integer lagerIId)
			throws NamingException {
		return getFac().hatRolleBerechtigungAufLager(lagerIId, globalInfo.getTheClientDto()) ;
	}

	@Override
	public List<AllLagerEntry> getAllLager() throws NamingException,
			RemoteException, EJBExceptionLP {
		List<AllLagerEntry> stocks = new ArrayList<AllLagerEntry>() ;
		Map<Integer,String> m = (Map<Integer,String>) getFac().getAllLager(globalInfo.getTheClientDto()) ;
		for (Entry<Integer, String> entry : m.entrySet()) {
			stocks.add(new AllLagerEntry(entry.getKey(), entry.getValue())) ;
		}
		return stocks ;
	}

	@Override
	public BigDecimal getLagerstandsVeraenderungOhneInventurbuchungen(
			Integer artikelIId, Integer lagerIId, Timestamp tVon, Timestamp tBis)
			throws NamingException, RemoteException {
		return getFac().getLagerstandsVeraenderungOhneInventurbuchungen(
				artikelIId, lagerIId, tVon, tBis, globalInfo.getTheClientDto());
	}
}
