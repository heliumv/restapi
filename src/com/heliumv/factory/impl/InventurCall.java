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
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IInventurCall;
import com.lp.server.artikel.service.InventurDto;
import com.lp.server.artikel.service.InventurFac;
import com.lp.server.artikel.service.InventurlisteDto;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class InventurCall extends BaseCall<InventurFac> implements IInventurCall {
	@Autowired
	private IGlobalInfo globalInfo ;

	public InventurCall() {
		super(InventurFacBean) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_ARTIKEL)
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public Integer createInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge, TheClientDto theClientDto)
			throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().createInventurliste(inventurlisteDto, bPruefeAufZuGrosseMenge, theClientDto) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_ARTIKEL)
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public Integer createInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().createInventurliste(inventurlisteDto, bPruefeAufZuGrosseMenge, globalInfo.getTheClientDto()) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_ARTIKEL)
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public InventurDto[] inventurFindOffene(String mandantCNr)
			throws NamingException, EJBExceptionLP {
		return getFac().inventurFindOffene(mandantCNr) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_ARTIKEL)
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public InventurDto[] inventurFindOffene() throws NamingException,
			EJBExceptionLP {
		return inventurFindOffene(globalInfo.getMandant()) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_ARTIKEL)
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public InventurDto inventurFindByPrimaryKey(Integer inventurId) throws NamingException, RemoteException {
		return getFac().findByPrimaryKeyOhneExc(inventurId);
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_ARTIKEL)
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public InventurlisteDto[] inventurlisteFindByInventurIIdLagerIIdArtikelIId(
			Integer inventurIId, Integer lagerIId, Integer artikelIId) throws NamingException, RemoteException,
			EJBExceptionLP {
		return getFac().inventurlisteFindByInventurIIdLagerIIdArtikelIId(
				inventurIId, lagerIId, artikelIId, globalInfo.getTheClientDto());
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_ARTIKEL)
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public Integer updateInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge) throws NamingException,
			RemoteException, EJBExceptionLP {
		return getFac().updateInventurliste(inventurlisteDto, bPruefeAufZuGrosseMenge, globalInfo.getTheClientDto()) ;
	}
}
