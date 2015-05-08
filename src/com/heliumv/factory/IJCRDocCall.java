package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.system.jcr.service.JCRDocDto;
import com.lp.server.system.jcr.service.JCRRepoInfo;
import com.lp.server.system.jcr.service.docnode.DocPath;
import com.lp.util.EJBExceptionLP;

public interface IJCRDocCall {
	void addNewDocumentOrNewVersionOfDocument(JCRDocDto jcrDocDto) throws NamingException, RemoteException;

	void addNewDocumentOrNewVersionOfDocumentWithinTransaction(
			JCRDocDto jcrDocDto) throws NamingException, RemoteException;

	JCRRepoInfo checkIfNodeExists(DocPath docPath) throws NamingException, RemoteException, EJBExceptionLP;
}
