package com.heliumv.api.production;

import java.util.List;

public interface IProductionApi {
	 List<ProductionEntry> getProductions(
			String userId,
			Integer limit,
			Integer startIndex,
			String filterCnr,
			String filterCustomer, 
			String filterProject,
			String filterItemCnr,
			Boolean filterWithHidden) ;

}
