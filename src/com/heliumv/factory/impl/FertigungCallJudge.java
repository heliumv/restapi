package com.heliumv.factory.impl;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.IFertigungCallJudge;
import com.heliumv.factory.IJudgeCall;
import com.lp.server.artikel.service.SeriennrChargennrMitMengeDto;
import com.lp.server.fertigung.service.LosistmaterialDto;
import com.lp.server.fertigung.service.LossollmaterialDto;
import com.lp.util.EJBExceptionLP;

public class FertigungCallJudge extends FertigungCall implements IFertigungCallJudge {
	@Autowired
	IJudgeCall judgeCall ;
	
	@Override
	public boolean darfGebeMaterialNachtraeglichAus() throws NamingException {
		return judgeCall.hasFertDarfIstmaterialManuellNachbuchen() ;
	}

	@Override
	public void gebeMaterialNachtraeglichAus(
			LossollmaterialDto lossollmaterialDto, LosistmaterialDto losistmaterialDto, 
			List<SeriennrChargennrMitMengeDto> listSnrChnr, boolean reduzierFehlmenge)
		throws NamingException, RemoteException, EJBExceptionLP {
		if(!darfGebeMaterialNachtraeglichAus()) {
			throw new EJBExceptionLP(EJBExceptionLP.FEHLER_UNZUREICHENDE_RECHTE, "darfGebeMaterialNachtraeglichAus") ;
		}
		
		super.gebeMaterialNachtraeglichAus(lossollmaterialDto, losistmaterialDto, listSnrChnr, reduzierFehlmenge);
	}	
}
