package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJCRDocCall;
import com.lp.server.system.jcr.service.JCRDocDto;
import com.lp.server.system.jcr.service.JCRDocFac;
import com.lp.server.system.jcr.service.JCRRepoInfo;
import com.lp.server.system.jcr.service.docnode.DocPath;
import com.lp.util.EJBExceptionLP;

public class JcrDocCall extends BaseCall<JCRDocFac> implements IJCRDocCall {
	@Autowired
	private IGlobalInfo globalInfo ;
	
	public JcrDocCall() {
		super(JcrDocFacBean) ;
	}
	
	@Override
	public void addNewDocumentOrNewVersionOfDocument(JCRDocDto jcrDocDto) throws NamingException, RemoteException {
		getFac().addNewDocumentOrNewVersionOfDocument(jcrDocDto, globalInfo.getTheClientDto());
	}
	
	@Override
	public void addNewDocumentOrNewVersionOfDocumentWithinTransaction(
			JCRDocDto jcrDocDto) throws NamingException, RemoteException {
		getFac().addNewDocumentOrNewVersionOfDocumentWithinTransaction(jcrDocDto, globalInfo.getTheClientDto());
	}

	@Override
	public JCRRepoInfo checkIfNodeExists(DocPath docPath) throws NamingException,
			RemoteException, EJBExceptionLP {
		return getFac().checkIfNodeExists(docPath);
	}
}
