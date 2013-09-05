package com.heliumv.api.customer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IKundeCall;
import com.heliumv.factory.IKundeReportCall;
import com.heliumv.factory.KundenpreislisteParams;
import com.heliumv.factory.query.CustomerQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.partner.service.KundeFac;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.util.EJBExceptionLP;

@Service("hvCustomer")
@Path("/api/v1/customer/")
public class CustomerApi extends BaseApi implements ICustomerApi {
	@Autowired
	private CustomerQuery customerQuery ;
	
	@Autowired
	private IKundeCall kundeCall ;
	@Autowired
	private IKundeReportCall kundeReportCall ;
	
	@Override
	@GET
	@Path("/{userid}")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<CustomerEntry> getCustomers(
			@PathParam("userid") String userId, 
			@QueryParam("limit") Integer limit,
			@QueryParam("startIndex") Integer startIndex,
			@QueryParam("filter_company") String filterCompany,
			@QueryParam("filter_city") String filterCity,
			@QueryParam("filter_extendedSearch") String filterExtendedSearch,
			@QueryParam("filter_withCustomers") Boolean filterWithCustomers,
			@QueryParam("filter_withProspectiveCustomers") Boolean filterWithProspectiveCustomers,
			@QueryParam("filter_withHidden") Boolean filterWithHidden) {
		List<CustomerEntry> customerEntries = new ArrayList<CustomerEntry>() ;
		
		try {
			if(connectClient(userId) == null) return customerEntries ;
			
			if(filterWithCustomers == null) filterWithCustomers = true ;
			if(filterWithProspectiveCustomers == null) filterWithProspectiveCustomers = true ;
			
			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(buildFilterCompanyName(filterCompany)) ;
			collector.add(buildFilterCity(filterCity)) ;
			collector.add(buildFilterExtendedSearch(filterExtendedSearch)) ;
			collector.add(buildFilterWithCustomers(filterWithCustomers)) ;
			collector.add(buildFilterWithProspectiveCustomers(filterWithProspectiveCustomers)) ;
			collector.add(buildFilterWithHidden(filterWithHidden)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND") ;
			
			QueryParameters params = customerQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
			
			QueryResult result = customerQuery.setQuery(params) ;
			customerEntries = customerQuery.getResultList(result) ;
//		} catch(NamingException e) {
//			respondUnavailable(e);
//		} catch(RemoteException e) {
//			respondUnavailable(e);
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
		
		return customerEntries ;
	}
	
	@Override
	@GET
	@Path("/{userid}/{customerid}/pricelist")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<PriceListEntry> getPriceList(
			@PathParam("userid") String userId,
			@PathParam("customerid") Integer customerId) {
		List<PriceListEntry> entries = new ArrayList<PriceListEntry>() ;
		try {
			if(connectClient(userId) == null) return entries ;
			
			KundenpreislisteParams params = new KundenpreislisteParams(customerId) ;
			kundeReportCall.printKundenpreisliste(params);
			
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		}
		return entries ;
	}
	
	private FilterKriterium buildFilterCompanyName(String companyName) {
		if(StringHelper.isEmpty(companyName)) return null ;
		
		return null ;
	}
	
	private FilterKriterium buildFilterCity(String city) {
		if(StringHelper.isEmpty(city)) return null ;

		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				KundeFac.FLR_PARTNER_LANDPLZORT_ORT_NAME, StringHelper.removeSqlDelimiters(city),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_BOTH, true, true,
				Facade.MAX_UNBESCHRAENKT);
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	private FilterKriterium buildFilterExtendedSearch(String extendedSearch) {
		if(StringHelper.isEmpty(extendedSearch)) return null ;
		return null ;
	}

	
	private FilterKriterium buildFilterWithCustomers(Boolean withCustomers) {
		return null ;
	}
	
	private FilterKriterium buildFilterWithProspectiveCustomers(Boolean withProspectiveCustomers) {
		return null ;
	}
	
	private FilterKriterium buildFilterWithHidden(Boolean withHidden) {
		return null ;
	}
}
