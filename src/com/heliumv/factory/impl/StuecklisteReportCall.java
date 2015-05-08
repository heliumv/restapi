package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IStuecklisteReportCall;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.stueckliste.service.StuecklisteReportFac;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.server.util.report.JasperPrintLP;

public class StuecklisteReportCall extends BaseCall<StuecklisteReportFac>
		implements IStuecklisteReportCall {

	public StuecklisteReportCall() {
		super(StuecklisteReportFacBean) ;
	}
	
	@Override
	@HvModul(modul=LocaleFac.BELEGART_STUECKLISTE)
	@HvJudge(rechtOder={RechteFac.RECHT_STK_STUECKLISTE_R, RechteFac.RECHT_STK_STUECKLISTE_R})
	public JasperPrintLP printStuecklisteAllgemeinMitPreis(
			Integer stuecklisteIId, Integer iOptionPreis,
			Boolean bMitPositionskommentar, Boolean bMitStuecklistenkommentar,
			Boolean bUnterstuecklistenEinbinden,
			Boolean bGleichePositionenZusammenfassen,
			Integer iOptionSortierungUnterstuecklisten,
			boolean bUnterstklstrukurBelassen, TheClientDto theClientDto,
			Integer iOptionSortierungStuecklisteGesamt1,
			Integer iOptionSortierungStuecklisteGesamt2,
			Integer iOptionSortierungStuecklisteGesamt3) throws NamingException, RemoteException {
		return getFac().printStuecklisteAllgemeinMitPreis(
				stuecklisteIId, iOptionPreis, bMitPositionskommentar, 
				bMitStuecklistenkommentar, bUnterstuecklistenEinbinden, 
				bGleichePositionenZusammenfassen, iOptionSortierungUnterstuecklisten, 
				bUnterstklstrukurBelassen, theClientDto, iOptionSortierungStuecklisteGesamt1, 
				iOptionSortierungStuecklisteGesamt2, iOptionSortierungStuecklisteGesamt3);
	}
}
