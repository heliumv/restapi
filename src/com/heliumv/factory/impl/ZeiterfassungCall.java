package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.worktime.DocumentType;
import com.heliumv.api.worktime.SpecialActivity;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IZeiterfassungCall;
import com.lp.server.personal.service.TaetigkeitDto;
import com.lp.server.personal.service.ZeitdatenDto;
import com.lp.server.personal.service.ZeiterfassungFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class ZeiterfassungCall extends BaseCall<ZeiterfassungFac> implements IZeiterfassungCall {

	@Autowired
	private IGlobalInfo globalInfo ;
	
	public ZeiterfassungCall()  {
		super(ZeiterfassungFacBean) ;
	}
	
	public TaetigkeitDto taetigkeitFindByCNr(String cNr) throws NamingException {
		return getFac().taetigkeitFindByCNr(cNr, globalInfo.getTheClientDto()) ;
	}

	public TaetigkeitDto taetigkeitFindByCNrSmall(String cnr) {
		return null ;
//	 	return getFac().taetigkeitFindByCNrSmallOhneExc(cnr) ;
	}
	
	public Integer createZeitdaten(ZeitdatenDto zeitdatenDto,
			boolean bBucheAutoPausen, boolean bBucheMitternachtssprung,
			boolean bZeitverteilen, TheClientDto theClientDto)
			throws EJBExceptionLP, NamingException, RemoteException {
		return getFac().createZeitdaten(zeitdatenDto, bBucheAutoPausen, bBucheMitternachtssprung, bZeitverteilen, theClientDto) ;
	}
	
	public List<SpecialActivity> getAllSprSondertaetigkeitenNurBDEBuchbar(String language) throws NamingException, RemoteException {
		Map<?, ?> m =  getFac().getAllSprSondertaetigkeitenNurBDEBuchbar(language) ;
		return convertFromActivities(m) ;
	}
	
	public List<SpecialActivity> getAllSprSondertaetigkeiten(String language) throws NamingException, RemoteException {
		Map<?, ?> m =  getFac().getAllSprSondertaetigkeiten(language) ;
		return convertFromActivities(m) ;
	}
	
	
	public List<DocumentType> getBebuchbareBelegarten(TheClientDto theClientDto) throws NamingException {
		Map<String, String> m = getFac().getBebuchbareBelegarten(theClientDto) ;
		return convertFromBelegarten(m) ;
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
