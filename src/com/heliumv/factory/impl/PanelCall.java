package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IPanelCall;
import com.heliumv.factory.legacy.PaneldatenPair;
import com.lp.server.system.service.PanelFac;
import com.lp.server.system.service.PanelbeschreibungDto;
import com.lp.server.system.service.PaneldatenDto;
import com.lp.util.EJBExceptionLP;

public class PanelCall extends BaseCall<PanelFac> implements IPanelCall {
	public PanelCall() {
		super(PanelFacBean) ;
	}

	@Override
	public PaneldatenDto[] paneldatenFindByPanelCNrCKey(String panelCNr,
			String cKey) throws RemoteException, NamingException,
			EJBExceptionLP {
		return getFac().paneldatenFindByPanelCNrCKey(panelCNr, cKey) ;
	}
	
	public List<PaneldatenPair> paneldatenFindByPanelCNrCKeyBeschreibung(String panelCNr, String cKey) 
			throws RemoteException, NamingException, EJBExceptionLP {
		List<PaneldatenPair> entries = new ArrayList<PaneldatenPair>() ;
		PaneldatenDto[] datenDto = getFac().paneldatenFindByPanelCNrCKey(panelCNr, cKey) ;
		if(datenDto == null) return entries ;
		
		for (PaneldatenDto paneldatenDto : datenDto) {
			PanelbeschreibungDto beschreibungDto = getFac()
					.panelbeschreibungFindByPrimaryKey(paneldatenDto.getPanelbeschreibungIId()) ;
			
			PaneldatenPair entry = new PaneldatenPair(paneldatenDto, beschreibungDto) ;
			entries.add(entry) ;
		}
		
		return entries ;
	}
}
