package com.heliumv.factory.impl;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IJudgeCall;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.server.system.service.TheJudgeFac;

public class JudgeCall extends BaseCall<TheJudgeFac> implements IJudgeCall {
	public JudgeCall() {
		super(TheJudgeFacBean) ;
	}

	protected boolean hatRechtImpl(String rechtCnr, TheClientDto theClientDto) throws NamingException {
		return getFac().hatRecht(rechtCnr, theClientDto) ;
	}
	
	public boolean hasPersZeiteingabeNurBuchen(TheClientDto theClientDto) throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_PERS_ZEITEINGABE_NUR_BUCHEN, theClientDto) ;
	}

	public boolean hasFertDarfLosErledigen(TheClientDto theClientDto) throws NamingException {
		return hatRechtImpl(RechteFac.RECHT_FERT_DARF_LOS_ERLEDIGEN, theClientDto) ;
	}
	
}
