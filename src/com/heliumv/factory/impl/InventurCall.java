package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.annotation.HvModul;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IInventurCall;
import com.lp.server.artikel.service.InventurDto;
import com.lp.server.artikel.service.InventurFac;
import com.lp.server.artikel.service.InventurlisteDto;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class InventurCall extends BaseCall<InventurFac> implements IInventurCall {
	@Autowired
	private IGlobalInfo globalInfo ;

	public InventurCall() {
		super(InventurFacBean) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_INVENTUR) 
	public Integer createInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge, TheClientDto theClientDto)
			throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().createInventurliste(inventurlisteDto, bPruefeAufZuGrosseMenge, theClientDto) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_INVENTUR) 
	public Integer createInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().createInventurliste(inventurlisteDto, bPruefeAufZuGrosseMenge, globalInfo.getTheClientDto()) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_INVENTUR) 
	public InventurDto[] inventurFindOffene(String mandantCNr)
			throws NamingException, EJBExceptionLP {
		return getFac().inventurFindOffene(mandantCNr) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_INVENTUR) 
	public InventurDto[] inventurFindOffene() throws NamingException,
			EJBExceptionLP {
		return inventurFindOffene(globalInfo.getMandant()) ;
	}

	@Override
	@HvModul(modul=LocaleFac.BELEGART_INVENTUR) 
	public InventurDto inventurFindByPrimaryKey(Integer inventurId) throws NamingException, RemoteException {
		return getFac().findByPrimaryKeyOhneExc(inventurId);
	}

	@Override
	public InventurlisteDto[] inventurlisteFindByInventurIIdLagerIIdArtikelIId(
			Integer inventurIId, Integer lagerIId, Integer artikelIId) throws NamingException, RemoteException,
			EJBExceptionLP {
		return getFac().inventurlisteFindByInventurIIdLagerIIdArtikelIId(
				inventurIId, lagerIId, artikelIId, globalInfo.getTheClientDto());
	}

	@Override
	public Integer updateInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge) throws NamingException,
			RemoteException, EJBExceptionLP {
		return getFac().updateInventurliste(inventurlisteDto, bPruefeAufZuGrosseMenge, globalInfo.getTheClientDto()) ;
	}
}
