package com.heliumv.factory;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.lp.server.artikel.service.SeriennrChargennrMitMengeDto;
import com.lp.server.fertigung.service.BucheSerienChnrAufLosDto;
import com.lp.server.fertigung.service.LosDto;
import com.lp.server.fertigung.service.LosablieferungDto;
import com.lp.server.fertigung.service.LosistmaterialDto;
import com.lp.server.fertigung.service.LoslagerentnahmeDto;
import com.lp.server.fertigung.service.LossollmaterialDto;
import com.lp.util.EJBExceptionLP;

public interface IFertigungCall {
	
	void bucheMaterialAufLos(LosDto losDto, BigDecimal menge) throws RemoteException ;

	void bucheMaterialAufLos(LosDto losDto, BigDecimal menge,
			boolean bHandausgabe,
			boolean bNurFehlmengenAnlegenUndReservierungenLoeschen,
			boolean bUnterstuecklistenAbbuchen,
			ArrayList<BucheSerienChnrAufLosDto> bucheSerienChnrAufLosDtos,
			boolean throwExceptionWhenCreate) throws RemoteException ;

	
	LosablieferungDto createLosablieferung(LosablieferungDto losablieferungDto,
			boolean bErledigt) throws NamingException, RemoteException, EJBExceptionLP ;
	
	/**
	 * Ein Los ueber seine IId finden.
	 * 
	 * @param losId ist die gesuchte Los-IId
	 * @return null wenn das Los nicht vorhanden ist, ansonsten das dto
	 */
	LosDto losFindByPrimaryKeyOhneExc(Integer losId) ;

	/**
	 * Los ueber seine Nummer im angemeldeten Mandanten finden
	 * 
	 * @param cNr
	 * @return
	 * @throws NamingException
	 */
	LosDto losFindByCNrMandantCNrOhneExc(String cNr) throws NamingException ;

	/**
	 * Los ueber seine Nummer in einem beliebigen Mandanten finden
	 * @param cNr
	 * @param mandantCNr
	 * @return
	 * @throws NamingException
	 */
	LosDto losFindByCNrMandantCNrOhneExc(String cNr, String mandantCNr) throws NamingException ;
	
	LoslagerentnahmeDto[] loslagerentnahmeFindByLosIId(Integer losIId) throws NamingException, RemoteException, EJBExceptionLP ;
	
	void gebeMaterialNachtraeglichAus(
			LossollmaterialDto lossollmaterialDto, LosistmaterialDto losistmaterialDto, 
			List<SeriennrChargennrMitMengeDto> listSnrChnr, boolean reduzierFehlmenge)
		throws NamingException, RemoteException, EJBExceptionLP ;
	
	LossollmaterialDto[] lossollmaterialFindByLosIIdOrderByISort(
			Integer losIId) throws NamingException, RemoteException, EJBExceptionLP ;
	
	LosistmaterialDto[] losistmaterialFindByLossollmaterialIId(
			Integer lossollmaterialIId) throws NamingException, RemoteException, EJBExceptionLP ;
	
	void updateLosistmaterialMenge(Integer losistmaterialIId, BigDecimal bdMengeNeu) 
			throws NamingException, RemoteException, EJBExceptionLP ;	
	
}
