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
package com.heliumv.factory;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Locale;

import javax.naming.NamingException;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.heliumv.api.worktime.DayTypeEntry;
import com.heliumv.api.worktime.DocumentType;
import com.heliumv.api.worktime.SpecialActivity;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.personal.service.MaschineDto;
import com.lp.server.personal.service.TaetigkeitDto;
import com.lp.server.personal.service.ZeitdatenDto;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public interface IZeiterfassungCall {
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITERFASSUNG_R)	
	TaetigkeitDto taetigkeitFindByCNr(String cnr)  throws NamingException;
	
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITERFASSUNG_R)	
	TaetigkeitDto taetigkeitFindByCNrSmall(String cnr) throws NamingException ;	
	
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITEREFASSUNG_CUD) 
	Integer createZeitdaten(ZeitdatenDto zeitdatenDto,
			boolean bBucheAutoPausen, boolean bBucheMitternachtssprung,
			boolean bZeitverteilen)
			throws EJBExceptionLP, NamingException, RemoteException ;

	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	Integer createAuftragZeitdaten(ZeitdatenDto zeitdatenDto,
			boolean bBucheAutoPausen, boolean bBucheMitternachtssprung,
			boolean bZeitverteilen)
			throws EJBExceptionLP, NamingException, RemoteException ;
	
	List<SpecialActivity> getAllSprSondertaetigkeitenNurBDEBuchbar(String language) throws NamingException, RemoteException ; 
	
	List<SpecialActivity> getAllSprSondertaetigkeiten(String language) throws NamingException, RemoteException ;
	
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	List<DocumentType> getBebuchbareBelegarten(TheClientDto theClientDto) throws NamingException ;
	List<DocumentType> getBebuchbareBelegarten() throws NamingException ;

	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITEREFASSUNG_CUD) 
	void removeZeitdaten(ZeitdatenDto zeitdatenDto) throws NamingException, RemoteException, EJBExceptionLP ;
	
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITERFASSUNG_R)	
	ZeitdatenDto zeitdatenFindByPrimaryKey(Integer id) throws NamingException, RemoteException ;
	
	List<DayTypeEntry> getAllSprTagesarten() throws NamingException, RemoteException ;
	List<DayTypeEntry> getAllSprTagesarten(Locale locale) throws NamingException, RemoteException ;
	
	MaschineDto maschineFindByPrimaryKey(Integer maschineId) throws NamingException, RemoteException ;
}
