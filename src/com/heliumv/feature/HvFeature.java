package com.heliumv.feature;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.heliumv.api.customer.CustomerDetailLoggedOnEntry;
import com.heliumv.api.item.ItemListBuilder;
import com.heliumv.api.item.ShopGroupIdList;
import com.heliumv.api.partlist.PartlistEmailEntry;
import com.lp.util.EJBExceptionLP;

public interface HvFeature {
	/**
	 * Das Kennwort ab&auml;ndern
	 * @param password ist das neue Kennwort
	 * @throws NamingException
	 * @throws RemoteException
	 */
	void changePassword(String password) throws NamingException, RemoteException ;

	CustomerDetailLoggedOnEntry getLoggedOnCustomerDetail() throws NamingException, RemoteException ;
	
	void applyItemListFilter(ItemListBuilder builder, String filterCnr, String filterTextSearch, String filterDeliveryCnr, 
			String filterItemGroupClass, String filterItemReferenceNr, Boolean filterWithHidden, 
			Integer filterItemgroupId, String filterCustomerItemCnr, ShopGroupIdList filterItemgroupIds) throws NamingException, RemoteException ;
	
	void sendEmailToProvisionAccount(Integer partlistId, PartlistEmailEntry emailEntry) throws NamingException, RemoteException, EJBExceptionLP ;
}
