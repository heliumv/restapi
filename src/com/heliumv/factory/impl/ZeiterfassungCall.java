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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.heliumv.api.worktime.DocumentType;
import com.heliumv.api.worktime.SpecialActivity;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.IZeiterfassungCall;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.personal.service.TaetigkeitDto;
import com.lp.server.personal.service.ZeitdatenDto;
import com.lp.server.personal.service.ZeiterfassungFac;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

@Component
public class ZeiterfassungCall extends BaseCall<ZeiterfassungFac> implements IZeiterfassungCall {

	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Autowired
	private IJudgeCall judgeCall ;
	
	private Map<String, Integer> cachedTaetigkeitIds = new HashMap<String, Integer>() ;
	
	public ZeiterfassungCall()  {
		super(ZeiterfassungFacBean) ;
	}
	
	
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITERFASSUNG_R)
	public TaetigkeitDto taetigkeitFindByCNr(String cNr) throws NamingException {
		return getFac().taetigkeitFindByCNr(cNr, globalInfo.getTheClientDto()) ;
	}

	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITERFASSUNG_R)	
	public TaetigkeitDto taetigkeitFindByCNrSmall(String cnr) throws NamingException {
	 	return getFac().taetigkeitFindByCNrSmallOhneExc(cnr) ;
	}
	
	private Timestamp updateTimeWithNow(Timestamp theirsTs) {
		if(theirsTs == null) return null ;
		
		Calendar theirs = Calendar.getInstance() ;
		theirs.setTimeInMillis(theirsTs.getTime()) ;
		
		Calendar mine = Calendar.getInstance() ;
		theirs.set(Calendar.HOUR, mine.get(Calendar.HOUR)) ;
		theirs.set(Calendar.MINUTE, mine.get(Calendar.MINUTE)) ;
		theirs.set(Calendar.SECOND, 0) ;
		
		return new Timestamp(theirs.getTimeInMillis()) ;		
	}
	
	private Integer getCachedTaetigkeitId(String taetigkeitCnr) throws NamingException {
		Integer foundId = cachedTaetigkeitIds.get(taetigkeitCnr) ;
		if(foundId == null) {
			TaetigkeitDto tDto = taetigkeitFindByCNrSmall(ZeiterfassungFac.TAETIGKEIT_KOMMT) ;
			if(tDto != null) {
				foundId = tDto.getIId() ;
				cachedTaetigkeitIds.put(taetigkeitCnr, foundId) ;
			}
		}
		return foundId ;
	}
	

	private void modifyKommtGehtToNow(ZeitdatenDto zDto) throws RemoteException, NamingException, EJBExceptionLP {
		Integer taetigkeitId = zDto.getTaetigkeitIId() ;
		if(taetigkeitId.equals(getCachedTaetigkeitId(ZeiterfassungFac.TAETIGKEIT_KOMMT)) ||
		   taetigkeitId.equals(getCachedTaetigkeitId(ZeiterfassungFac.TAETIGKEIT_GEHT))  ||
		   taetigkeitId.equals(getCachedTaetigkeitId(ZeiterfassungFac.TAETIGKEIT_UNTER))) {

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);
			zDto.setTZeit(new Timestamp(c.getTimeInMillis())) ;
		}
	}
	
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITEREFASSUNG_CUD) 
	public Integer createZeitdaten(ZeitdatenDto zeitdatenDto,
			boolean bBucheAutoPausen, boolean bBucheMitternachtssprung,
			boolean bZeitverteilen)
			throws EJBExceptionLP, NamingException, RemoteException {
		if(judgeCall.hasPersZeiteingabeNurBuchen()) {
			zeitdatenDto.setTZeit(updateTimeWithNow(zeitdatenDto.getTZeit())) ;
		}
		if(!judgeCall.hasPersDarfKommtGehtAendern()) {
			modifyKommtGehtToNow(zeitdatenDto) ;
		}
		return getFac().createZeitdaten(zeitdatenDto, bBucheAutoPausen,
				bBucheMitternachtssprung, bZeitverteilen, globalInfo.getTheClientDto()) ;
	}

	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG) 
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITEREFASSUNG_CUD) 
	public Integer createAuftragZeitdaten(ZeitdatenDto zeitdatenDto,
			boolean bBucheAutoPausen, boolean bBucheMitternachtssprung,
			boolean bZeitverteilen)
			throws EJBExceptionLP, NamingException, RemoteException {
		zeitdatenDto.setCBelegartnr(LocaleFac.BELEGART_AUFTRAG) ;
		return createZeitdaten(zeitdatenDto, bBucheAutoPausen, bBucheMitternachtssprung, bZeitverteilen) ;
	}
	
	public List<SpecialActivity> getAllSprSondertaetigkeitenNurBDEBuchbar(String language) throws NamingException, RemoteException {
		Map<?, ?> m =  getFac().getAllSprSondertaetigkeitenNurBDEBuchbar(language) ;
		return convertFromActivities(m) ;
	}
	
	public List<SpecialActivity> getAllSprSondertaetigkeiten(String language) throws NamingException, RemoteException {
		Map<?, ?> m =  getFac().getAllSprSondertaetigkeiten(language) ;
		return convertFromActivities(m) ;
	}
	
	
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	public List<DocumentType> getBebuchbareBelegarten(TheClientDto theClientDto) throws NamingException {
		Map<String, String> m = getFac().getBebuchbareBelegarten(theClientDto) ;
		return convertFromBelegarten(m) ;
	}

	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITERFASSUNG_R)
	public ZeitdatenDto zeitdatenFindByPrimaryKey(Integer id) throws NamingException, RemoteException {
//		return getFac().zeitdatenFindByPrimaryKeyOhneExc(id) ;
		return getFac().zeitdatenFindByPrimaryKey(id, globalInfo.getTheClientDto()) ;
	}
	
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITEREFASSUNG_CUD) 
	public void removeZeitdaten(ZeitdatenDto zeitdatenDto) throws NamingException, RemoteException, EJBExceptionLP {
		getFac().removeZeitdaten(zeitdatenDto, globalInfo.getTheClientDto()) ;
	}
	
	private List<SpecialActivity> convertFromActivities(Map<?, ?> allActivities) {
		List<SpecialActivity> activities = new ArrayList<SpecialActivity>() ;
		for (Entry<?, ?> entry : allActivities.entrySet()) {
			activities.add(new SpecialActivity((Integer)entry.getKey(), (String) entry.getValue())) ;
		}
		
		return activities ;
	}

	private List<DocumentType> convertFromBelegarten(Map<String, String> allBelegarten) {
		List<DocumentType> documents = new ArrayList<DocumentType>() ;
		for (Entry<String, String> entry : allBelegarten.entrySet()) {
			documents.add(new DocumentType(entry.getKey(), entry.getValue())) ;
		}
		
		return documents ;
	}
}
