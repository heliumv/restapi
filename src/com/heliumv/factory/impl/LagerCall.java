package com.heliumv.factory.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.ILagerCall;
import com.heliumv.factory.legacy.AllLagerEntry;
import com.lp.server.artikel.service.LagerDto;
import com.lp.server.artikel.service.LagerFac;
import com.lp.util.EJBExceptionLP;

public class LagerCall extends BaseCall<LagerFac> implements ILagerCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public LagerCall() {
		super(LagerFacBean) ;
	}
	
	public Integer artikelIdFindBySeriennummerOhneExc(String serialnumber) throws NamingException, RemoteException {
		Integer itemId = getFac().getArtikelIIdUeberSeriennummer(serialnumber, globalInfo.getTheClientDto()) ;
		if(itemId == null) {
			itemId = getFac().getArtikelIIdUeberSeriennummerAbgang(serialnumber, globalInfo.getTheClientDto()) ;
		}

		return itemId ;
	}

	@Override
	public BigDecimal  getGemittelterGestehungspreisEinesLagers(
			Integer itemId, Integer lagerId) throws NamingException, RemoteException {
		return getFac().getGemittelterGestehungspreisEinesLagers(itemId, lagerId, globalInfo.getTheClientDto()) ;
	}

	@Override
	public LagerDto lagerFindByPrimaryKeyOhneExc(Integer lagerIId) throws NamingException {
		LagerDto lagerDto = getFac().lagerFindByPrimaryKeyOhneExc(lagerIId) ;
		if(lagerDto != null) {
			if(!globalInfo.getTheClientDto().getMandant().equals(lagerDto.getMandantCNr())) {
				lagerDto = null ;
			}
		}

		return lagerDto ;
	}

	@Override
	public BigDecimal getLagerstandOhneExc(Integer itemId, Integer lagerIId)
			throws NamingException, RemoteException {
		return getFac().getLagerstandOhneExc(itemId, lagerIId, globalInfo.getTheClientDto());
	}

	@Override
	public boolean hatRolleBerechtigungAufLager(Integer lagerIId)
			throws NamingException {
		return getFac().hatRolleBerechtigungAufLager(lagerIId, globalInfo.getTheClientDto()) ;
	}

	@Override
	public List<AllLagerEntry> getAllLager() throws NamingException,
			RemoteException, EJBExceptionLP {
		List<AllLagerEntry> stocks = new ArrayList<AllLagerEntry>() ;
		Map<Integer,String> m = (Map<Integer,String>) getFac().getAllLager(globalInfo.getTheClientDto()) ;
		for (Entry<Integer, String> entry : m.entrySet()) {
			stocks.add(new AllLagerEntry(entry.getKey(), entry.getValue())) ;
		}
		return stocks ;
	}

	@Override
	public BigDecimal getLagerstandsVeraenderungOhneInventurbuchungen(
			Integer artikelIId, Integer lagerIId, Timestamp tVon, Timestamp tBis)
			throws NamingException, RemoteException {
		return getFac().getLagerstandsVeraenderungOhneInventurbuchungen(
				artikelIId, lagerIId, tVon, tBis, globalInfo.getTheClientDto());
	}
}
