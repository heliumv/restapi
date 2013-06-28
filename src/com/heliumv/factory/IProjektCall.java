package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.projekt.service.ProjektDto;

public interface IProjektCall {

	/**
	 * Ein Projekt ueber seine IId finden.
	 * 
	 * @param projectId ist die gesuchte Projekt-IId
	 * @return null wenn das Los nicht vorhanden ist, ansonsten das dto
	 */
	ProjektDto projektFindByPrimaryKeyOhneExc(Integer projectId) throws NamingException, RemoteException ;
}
