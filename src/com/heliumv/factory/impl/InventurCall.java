package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.annotation.HvJudge;
import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IInventurCall;
import com.lp.server.artikel.service.InventurDto;
import com.lp.server.artikel.service.InventurFac;
import com.lp.server.artikel.service.InventurlisteDto;
import com.lp.server.benutzer.service.RechteFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

public class InventurCall extends BaseCall<InventurFac> implements IInventurCall {
	@Autowired
	private IGlobalInfo globalInfo ;

	public InventurCall() {
		super(InventurFacBean) ;
	}

	@Override
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public Integer createInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge, TheClientDto theClientDto)
			throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().createInventurliste(inventurlisteDto, bPruefeAufZuGrosseMenge, theClientDto) ;
	}

	@Override
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public Integer createInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge) throws NamingException, RemoteException, EJBExceptionLP {
		return getFac().createInventurliste(inventurlisteDto, bPruefeAufZuGrosseMenge, globalInfo.getTheClientDto()) ;
	}

	@Override
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public InventurDto[] inventurFindOffene(String mandantCNr)
			throws NamingException, EJBExceptionLP {
		return getFac().inventurFindOffene(mandantCNr) ;
	}

	@Override
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public InventurDto[] inventurFindOffene() throws NamingException,
			EJBExceptionLP {
		return inventurFindOffene(globalInfo.getMandant()) ;
	}

	@Override
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public InventurDto inventurFindByPrimaryKey(Integer inventurId) throws NamingException, RemoteException {
		return getFac().findByPrimaryKeyOhneExc(inventurId);
	}

	@Override
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public InventurlisteDto[] inventurlisteFindByInventurIIdLagerIIdArtikelIId(
			Integer inventurIId, Integer lagerIId, Integer artikelIId) throws NamingException, RemoteException,
			EJBExceptionLP {
		return getFac().inventurlisteFindByInventurIIdLagerIIdArtikelIId(
				inventurIId, lagerIId, artikelIId, globalInfo.getTheClientDto());
	}

	@Override
	@HvJudge(recht=RechteFac.RECHT_WW_ARTIKEL_CUD)
	public Integer updateInventurliste(InventurlisteDto inventurlisteDto,
			boolean bPruefeAufZuGrosseMenge) throws NamingException,
			RemoteException, EJBExceptionLP {
		return getFac().updateInventurliste(inventurlisteDto, bPruefeAufZuGrosseMenge, globalInfo.getTheClientDto()) ;
	}
}
