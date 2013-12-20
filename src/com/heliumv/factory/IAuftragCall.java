package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.lp.server.auftrag.service.AuftragDto;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public interface IAuftragCall {
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(rechtOder={RechteFac.RECHT_AUFT_AUFTRAG_R, RechteFac.RECHT_AUFT_AUFTRAG_CUD})	
	AuftragDto auftragFindByPrimaryKeyOhneExc(Integer orderId) throws NamingException ;
	
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(rechtOder={RechteFac.RECHT_AUFT_AUFTRAG_R, RechteFac.RECHT_AUFT_AUFTRAG_CUD})	
	AuftragDto auftragFindByCnr(String cnr) throws NamingException, RemoteException ;

	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(rechtOder={RechteFac.RECHT_AUFT_AUFTRAG_R, RechteFac.RECHT_AUFT_AUFTRAG_CUD})	
	AuftragDto auftragFindByCnr(String cnr, TheClientDto theClientDto) throws NamingException, RemoteException ;
	
	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(recht=RechteFac.RECHT_AUFT_AUFTRAG_CUD)	
	String createOrderResponseToString(AuftragDto auftragDto) throws NamingException, RemoteException ;	

	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(recht=RechteFac.RECHT_AUFT_AUFTRAG_CUD)	
	String createOrderResponseToString(AuftragDto auftragDto, TheClientDto theClientDto) 
			throws NamingException, RemoteException ;	

	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(recht=RechteFac.RECHT_AUFT_AUFTRAG_CUD)	
	String createOrderResponsePost(AuftragDto auftragDto) throws RemoteException, NamingException, EJBExceptionLP ;

	@HvModul(modul=LocaleFac.BELEGART_AUFTRAG)
	@HvJudge(recht=RechteFac.RECHT_AUFT_AUFTRAG_CUD)	
	String createOrderResponsePost(
			AuftragDto auftragDto, TheClientDto theClientDto) throws RemoteException, NamingException, EJBExceptionLP ;	
}
