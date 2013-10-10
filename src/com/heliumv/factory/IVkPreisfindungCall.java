package com.heliumv.factory;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.server.artikel.service.VkpfartikelpreislisteDto;

public interface IVkPreisfindungCall {

	VkpfartikelpreislisteDto vkpfartikelpreislisteFindByPrimaryKey(Integer preislisteId) throws RemoteException, NamingException ;
}
