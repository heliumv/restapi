package com.heliumv.factory.impl;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJudgeCall;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.server.system.service.TheJudgeFac;

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
}
