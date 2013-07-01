package com.heliumv.factory;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.heliumv.annotation.HvJudge;
import com.heliumv.annotation.HvModul;
import com.heliumv.api.worktime.DocumentType;
import com.heliumv.api.worktime.SpecialActivity;
import com.lp.server.benutzer.service.RechteFac;
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

	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITEREFASSUNG_CUD) 
	void removeZeitdaten(ZeitdatenDto zeitdatenDto) throws NamingException, RemoteException, EJBExceptionLP ;
	
	@HvModul(modul=LocaleFac.BELEGART_ZEITERFASSUNG)
	@HvJudge(recht=RechteFac.RECHT_PERS_ZEITERFASSUNG_R)	
	ZeitdatenDto zeitdatenFindByPrimaryKey(Integer id) throws NamingException, RemoteException ;
}
