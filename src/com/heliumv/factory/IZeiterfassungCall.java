package com.heliumv.factory;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import com.heliumv.api.worktime.DocumentType;
import com.heliumv.api.worktime.SpecialActivity;
import com.lp.server.personal.service.TaetigkeitDto;
import com.lp.server.personal.service.ZeitdatenDto;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public interface IZeiterfassungCall {
	TaetigkeitDto taetigkeitFindByCNr(String cnr, TheClientDto theClientDto)  throws NamingException;
	
	TaetigkeitDto taetigkeitFindByCNrSmall(String cnr) ;	
	
	Integer createZeitdaten(ZeitdatenDto zeitdatenDto,
			boolean bBucheAutoPausen, boolean bBucheMitternachtssprung,
			boolean bZeitverteilen, TheClientDto theClientDto)
			throws EJBExceptionLP, NamingException, RemoteException ;

	List<SpecialActivity> getAllSprSondertaetigkeitenNurBDEBuchbar(String language) throws NamingException, RemoteException ; 
	
	List<SpecialActivity> getAllSprSondertaetigkeiten(String language) throws NamingException, RemoteException ;
	
	List<DocumentType> getBebuchbareBelegarten(TheClientDto theClientDto) throws NamingException ;
}
