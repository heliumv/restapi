/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
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
import com.lp.server.fertigung.service.LossollarbeitsplanDto;
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
	
	LosistmaterialDto createLosistmaterial(LosistmaterialDto losistmaterialDto,
			String sSerienChargennummer) throws NamingException, RemoteException, EJBExceptionLP ; 	
	
	LossollmaterialDto lossollmaterialFindByPrimaryKey(Integer iId)
			throws NamingException, RemoteException, EJBExceptionLP ;
	
	/**
	 * Nur die Menge des Lossollmaterials auf die neue Menge &auml;ndern
	 * 
	 * @param lossollmaterialId
	 * @param mengeNeu
	 * @throws NamingException
	 * @throws RemoteException
	 * @throws EJBExceptionLP
	 */
	void updateLossollmaterialMenge(Integer lossollmaterialId, BigDecimal mengeNeu) 
			throws NamingException, RemoteException, EJBExceptionLP ;
	
	/**
	 * Den Arbeitsplan mittels Id ermitteln
	 * 
	 * @param iId
	 * @return
	 */
	LossollarbeitsplanDto lossollarbeitsplanFindByPrimaryKey(Integer iId) throws NamingException, RemoteException, EJBExceptionLP ;
	
	LossollarbeitsplanDto updateLossollarbeitsplan(
			LossollarbeitsplanDto lossollarbeitsplanDto) throws NamingException, RemoteException, EJBExceptionLP ;
}
