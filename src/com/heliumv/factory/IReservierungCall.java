package com.heliumv.factory;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.util.EJBExceptionLP;

public interface IReservierungCall {
	BigDecimal getAnzahlReservierungen(Integer itemId) throws NamingException, RemoteException, EJBExceptionLP ;
}
