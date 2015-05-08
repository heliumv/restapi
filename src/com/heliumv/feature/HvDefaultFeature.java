package com.heliumv.feature;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.api.customer.CustomerDetailLoggedOnEntry;
import com.heliumv.api.item.ItemListBuilder;
import com.heliumv.api.item.ShopGroupIdList;
import com.heliumv.api.partlist.PartlistEmailEntry;
import com.lp.util.EJBExceptionLP;

public class HvDefaultFeature implements HvFeature {

	@Override
	public void changePassword(String password) throws NamingException,
			RemoteException {
	}
	
	@Override
	public CustomerDetailLoggedOnEntry getLoggedOnCustomerDetail()
			throws NamingException, RemoteException {
		return null;
	}
	
	@Override
	public void applyItemListFilter(ItemListBuilder builder, String filterCnr,
			String filterTextSearch, String filterDeliveryCnr,
			String filterItemGroupClass, String filterItemReferenceNr,
			Boolean filterWithHidden, Integer filterItemgroupId, 
			String filterCustomerItemCnr, ShopGroupIdList filterShopGroupIdsList) throws NamingException, RemoteException  {
		builder.clear()
			.addFilterCnr(filterCnr)
			.addFilterTextSearch(filterTextSearch)
			.addFilterDeliveryCnr(filterDeliveryCnr)
			.addFilterItemGroupClass(filterItemGroupClass)
			.addFilterItemReferenceNr(filterItemReferenceNr)
			.addFilterWithHidden(filterWithHidden)
			.addFilterItemGroupClass(filterItemgroupId);		
	}
	
	@Override
	public void sendEmailToProvisionAccount(Integer partlistId, PartlistEmailEntry emailEntry)
			throws NamingException, RemoteException, EJBExceptionLP {
	}
}
