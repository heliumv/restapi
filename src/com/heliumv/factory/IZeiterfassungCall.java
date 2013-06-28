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
	@HvModul(name=LocaleFac.BELEGART_ZEITERFASSUNG)
	TaetigkeitDto taetigkeitFindByCNr(String cnr)  throws NamingException;
	
	TaetigkeitDto taetigkeitFindByCNrSmall(String cnr) ;	
	
	@HvModul(name=LocaleFac.BELEGART_ZEITERFASSUNG)
	Integer createZeitdaten(ZeitdatenDto zeitdatenDto,
			boolean bBucheAutoPausen, boolean bBucheMitternachtssprung,
			boolean bZeitverteilen, TheClientDto theClientDto)
			throws EJBExceptionLP, NamingException, RemoteException ;

	List<SpecialActivity> getAllSprSondertaetigkeitenNurBDEBuchbar(String language) throws NamingException, RemoteException ; 
	
	List<SpecialActivity> getAllSprSondertaetigkeiten(String language) throws NamingException, RemoteException ;
	
	@HvModul(name=LocaleFac.BELEGART_ZEITERFASSUNG)
	List<DocumentType> getBebuchbareBelegarten(TheClientDto theClientDto) throws NamingException ;

	@HvJudge(name=RechteFac.RECHT_PERS_ZEITEREFASSUNG_CUD) 
	void removeZeitdaten(ZeitdatenDto zeitdatenDto) throws NamingException, RemoteException, EJBExceptionLP ;
	
	@HvJudge(name=RechteFac.RECHT_PERS_ZEITERFASSUNG_R)	
	ZeitdatenDto zeitdatenFindByPrimaryKey(Integer id) throws NamingException, RemoteException ;
}
