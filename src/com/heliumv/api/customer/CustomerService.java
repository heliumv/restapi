package com.heliumv.api.customer;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.IPersonalCall;
import com.heliumv.factory.IVkPreisfindungCall;
import com.lp.server.artikel.service.VkpfartikelpreislisteDto;
import com.lp.server.partner.service.KundeDto;
import com.lp.server.personal.service.PersonalDto;

public class CustomerService {
	@Autowired
	private IPersonalCall personalCall ;
	@Autowired
	private IVkPreisfindungCall vkpreisfindungCall ;
	@Autowired
	private CustomerEntryMapper customerEntryMapper ;
	
	public CustomerDetailEntry getCustomerDetailEntry(KundeDto kundeDto) throws RemoteException, NamingException {
		PersonalDto personalDto = getPersonal(kundeDto.getPersonaliIdProvisionsempfaenger()) ;
		VkpfartikelpreislisteDto preislisteDto = getPreisliste(kundeDto.getVkpfArtikelpreislisteIIdStdpreisliste()) ;			
		return customerEntryMapper.mapDetailEntry(kundeDto, personalDto, preislisteDto) ;		
	}
	
	private PersonalDto getPersonal(Integer personalId) throws NamingException, RemoteException {
		if(personalId == null) return null ;
		return personalCall.byPrimaryKeySmall(personalId) ;
	}	

	private VkpfartikelpreislisteDto getPreisliste(Integer preislisteId) throws NamingException, RemoteException {
		if(preislisteId == null) return null ;			
		return vkpreisfindungCall.vkpfartikelpreislisteFindByPrimaryKey(preislisteId) ;
	}
}
