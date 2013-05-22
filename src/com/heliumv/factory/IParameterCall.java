package com.heliumv.factory;

import java.rmi.RemoteException;

public interface IParameterCall {
	
	boolean isZeitdatenAufErledigteBuchbar() throws RemoteException ;
	boolean isPartnerSucheWildcardBeidseitig() throws RemoteException ;
}
