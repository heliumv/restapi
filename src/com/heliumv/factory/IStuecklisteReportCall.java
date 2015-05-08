package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.system.service.TheClientDto;
import com.lp.server.util.report.JasperPrintLP;

public interface IStuecklisteReportCall {
	JasperPrintLP printStuecklisteAllgemeinMitPreis(
			Integer stuecklisteIId, Integer iOptionPreis,
			Boolean bMitPositionskommentar, Boolean bMitStuecklistenkommentar,
			Boolean bUnterstuecklistenEinbinden,
			Boolean bGleichePositionenZusammenfassen,
			Integer iOptionSortierungUnterstuecklisten,
			boolean bUnterstklstrukurBelassen, TheClientDto theClientDto,
			Integer iOptionSortierungStuecklisteGesamt1,
			Integer iOptionSortierungStuecklisteGesamt2,
			Integer iOptionSortierungStuecklisteGesamt3) throws NamingException, RemoteException;
}
