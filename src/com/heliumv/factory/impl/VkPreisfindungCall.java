package com.heliumv.factory.impl;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IVkPreisfindungCall;
import com.lp.server.artikel.service.VkPreisfindungFac;
import com.lp.server.artikel.service.VkpfartikelpreislisteDto;
import com.lp.util.EJBExceptionLP;

public class VkPreisfindungCall extends BaseCall<VkPreisfindungFac> implements IVkPreisfindungCall {

	protected VkPreisfindungCall() {
		super(VkPreisfindungFacBean) ;
	}

	@Override
	public VkpfartikelpreislisteDto vkpfartikelpreislisteFindByPrimaryKey(
			Integer preislisteId) throws RemoteException, NamingException {
		try {
			VkpfartikelpreislisteDto preislisteDto = getFac().vkpfartikelpreislisteFindByPrimaryKey(preislisteId) ;
			return preislisteDto ;
		} catch(EJBExceptionLP e) {
			if(e.getCode() == EJBExceptionLP.FEHLER_BEI_FINDBYPRIMARYKEY) {
				return null ;
			}
			
			throw e ;
		}
	}
}
