package com.heliumv.api.customer;

import com.heliumv.api.BaseFLRTransformer;

public class CustomerEntryTransformer extends BaseFLRTransformer<CustomerEntry> {

	@Override
	public CustomerEntry transformOne(Object[] flrObject) {
		CustomerEntry entry = new CustomerEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setAddressType((String) flrObject[1]) ;
		entry.setName1((String) flrObject[2]) ;
		entry.setSign((String) flrObject[3]) ; 
		entry.setPhone((String) flrObject[4]) ;
		entry.setClassification((String) flrObject[5]) ;
		entry.setDeliveryAllowed(flrObject[6] == null) ;
		entry.setCountryCode((String) flrObject[7]) ;
		entry.setZipcode((String) flrObject[8]) ;
		entry.setCity((String) flrObject[9]) ;
		entry.setRepresentativeSign((String) flrObject[10]) ;
		return entry ;
	}
}
