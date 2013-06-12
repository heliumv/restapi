package com.heliumv.factory;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.naming.NamingException;

import com.lp.server.fertigung.service.BucheSerienChnrAufLosDto;
import com.lp.server.fertigung.service.LosDto;
import com.lp.server.fertigung.service.LosablieferungDto;
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
}
