package com.heliumv.api.customer;

import java.util.List;

public interface ICustomerApi {
	List<CustomerEntry> getCustomers(
			String userId, 
			Integer limit,
			Integer startIndex,
			String filterCompany,
			String filterCity,
			String filterExtendedSearch,
			Boolean filterWithCustomers,
			Boolean filterWithProspectiveCustomers,
			Boolean filterWithHidden) ;
	
	List<PriceListEntry> getPriceList(
			String userId, Integer customerId) ;
}
