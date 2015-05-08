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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.ISystemCall;
import com.heliumv.factory.legacy.AllEinheitEntry;
import com.lp.server.system.service.SystemFac;

public class SystemCall extends BaseCall<SystemFac> implements ISystemCall {
	@Autowired 
	private IGlobalInfo globalInfo ;
	
	public SystemCall() {
		super(SystemFacBean) ;
	}

	@Override
	public String getHauptmandant() throws NamingException {
		return getFac().getHauptmandant() ;
	}

	@Override
	public String getServerVersion() throws NamingException {
		return getFac().getServerVersion() ;
	}

	@Override
	public Integer getServerBuildNumber() throws NamingException {
		return getFac().getServerBuildNumber() ;
	}

	@Override
	public Timestamp getServerTimestamp() throws RemoteException, NamingException {
		return getFac().getServerTimestamp() ;
	}
	
	@Override
	public List<AllEinheitEntry> getAllEinheiten() throws RemoteException, NamingException {
		List<AllEinheitEntry> allEinheiten = new ArrayList<AllEinheitEntry>() ;
		@SuppressWarnings("unchecked")
		Map<String,String> m = (Map<String, String>)getFac()
				.getAllEinheit(globalInfo.getTheClientDto()) ;		
 		for (Entry<String, String> entry : m.entrySet()) {
			allEinheiten.add(new AllEinheitEntry(entry.getKey(), entry.getValue()));
		}

		return allEinheiten ;
	}
}
