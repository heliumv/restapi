package com.heliumv.factory;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.util.EJBExceptionLP;

public interface IFehlmengeCall {
	BigDecimal getAnzahlFehlmengeEinesArtikels(Integer itemId) throws NamingException, RemoteException, EJBExceptionLP ;
}
