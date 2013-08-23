package com.heliumv.factory;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.artikel.service.LagerDto;

public interface ILagerCall {
	BigDecimal getGemittelterGestehungspreisEinesLagers(
			Integer itemId, Integer lagerId) throws NamingException, RemoteException;

	LagerDto lagerFindByPrimaryKeyOhneExc(Integer lagerIId)
			throws NamingException;

	BigDecimal getLagerstandOhneExc(Integer itemId, Integer lagerIId)
			throws NamingException, RemoteException;

	boolean hatRolleBerechtigungAufLager(Integer lagerIId)
			throws NamingException;
}
