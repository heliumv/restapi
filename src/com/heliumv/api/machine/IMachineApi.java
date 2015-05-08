package com.heliumv.api.machine;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.util.EJBExceptionLP;

public interface IMachineApi {
	/**
	 * Eine Liste aller verf&uuml;gbaren Maschinen</br>
	 * 
	 * @param userId
	 * @param limit
	 * @param startIndex
	 * @param filterWithHidden
	 * @return
	 * @throws RemoteException
	 * @throws NamingException
	 * @throws EJBExceptionLP
	 */
	MachineEntryList getMachines(
			String userId,
			Integer limit,
			Integer startIndex,			
			Boolean filterWithHidden) throws RemoteException, NamingException, EJBExceptionLP ;

	/**
	 * Eine Liste aller Maschinengruppen</br>
	 * 
	 * @param userId
	 * @param limit
	 * @param startIndex
	 * @return
	 * @throws RemoteException
	 * @throws NamingException
	 * @throws EJBExceptionLP
	 */
	MachineGroupEntryList getMachineGroups(
			String userId,
			Integer limit,
			Integer startIndex) throws RemoteException, NamingException, EJBExceptionLP ;	

}
