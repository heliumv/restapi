package com.heliumv.api.customer;

import java.util.Date;
import java.util.List;

import com.lp.server.partner.service.CustomerPricelistReportDto;

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
	
	CustomerPricelistReportDto getPriceList(
			String userId,
			Integer customerId,
			String filterItemgroupCnr,
			Integer filterItemgroupId,
			String filterItemclassCnr,
			Integer filterItemclassId,
			String filterItemRangeFrom,
			String filterItemRangeTo,
			String filterValidityDate,
			Boolean filterWithHidden,
			Boolean filterOnlySpecialcondition,
			Boolean filterWithClientLanguage,
			Boolean filterOnlyForWebshop) ;
}
