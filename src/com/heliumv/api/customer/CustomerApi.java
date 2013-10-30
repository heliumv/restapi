package com.heliumv.api.customer;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IKundeCall;
import com.heliumv.factory.IKundeReportCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IPersonalCall;
import com.heliumv.factory.IVkPreisfindungCall;
import com.heliumv.factory.query.CustomerQuery;
import com.heliumv.tools.FilterHelper;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtgruDto;
import com.lp.server.artikel.service.ArtklaDto;
import com.lp.server.artikel.service.VkpfartikelpreislisteDto;
import com.lp.server.partner.service.CustomerPricelistReportDto;
import com.lp.server.partner.service.KundeDto;
import com.lp.server.partner.service.KundeFac;
import com.lp.server.partner.service.KundenpreislisteParams;
import com.lp.server.partner.service.PartnerFac;
import com.lp.server.personal.service.PersonalDto;
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
	@Autowired
	private IParameterCall parameterCall ;
	@Autowired
	private IArtikelCall artikelCall ;
	@Autowired
	private IVkPreisfindungCall vkpreisfindungCall ;
	@Autowired
	private IPersonalCall personalCall ;
	@Autowired
	private CustomerEntryMapper customerEntryMapper ;
	
	@Override
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<CustomerEntry> getCustomers(
			@QueryParam(Param.USERID) String userId, 
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex,
			@QueryParam("filter_company") String filterCompany,
			@QueryParam("filter_city") String filterCity,
			@QueryParam("filter_extendedSearch") String filterExtendedSearch,
			@QueryParam("filter_withCustomers") Boolean filterWithCustomers,
			@QueryParam("filter_withProspectiveCustomers") Boolean filterWithProspectiveCustomers,
			@QueryParam(Filter.HIDDEN) Boolean filterWithHidden) {
		List<CustomerEntry> customerEntries = new ArrayList<CustomerEntry>() ;
		
		try {
			if(connectClient(userId) == null) return customerEntries ;
			
			if(filterWithCustomers == null) filterWithCustomers = true ;
			if(filterWithProspectiveCustomers == null) filterWithProspectiveCustomers = true ;
			
			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(buildFilterCompanyName(filterCompany)) ;
			collector.add(buildFilterCity(filterCity)) ;
			collector.add(buildFilterExtendedSearch(filterExtendedSearch)) ;
			collector.add(buildFilterWithCustomers(filterWithCustomers, filterWithProspectiveCustomers)) ;
			collector.add(buildFilterWithHidden(filterWithHidden)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND") ;
			
			QueryParameters params = customerQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
			
			QueryResult result = customerQuery.setQuery(params) ;
			customerEntries = customerQuery.getResultList(result) ;
		} catch(NamingException e) {
			respondUnavailable(e);
		} catch(RemoteException e) {
			respondUnavailable(e);
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
		
		return customerEntries ;
	}

	
	@Override
	@GET
	@Path("/{" + Param.CUSTOMERID + "}")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public CustomerDetailEntry getCustomer(
			@QueryParam(Param.USERID) String userId,
			@PathParam(Param.CUSTOMERID)  Integer customerId) {
		try {
			if(connectClient(userId) == null) return null ;
			
			KundeDto kundeDto = kundeCall.kundeFindByPrimaryKeyOhneExc(customerId) ;
			if(kundeDto == null ) {
				respondNotFound(Param.CUSTOMERID, customerId.toString());
				return null ;
			}
			
			PersonalDto personalDto = getPersonal(kundeDto.getPersonaliIdProvisionsempfaenger()) ;
			VkpfartikelpreislisteDto preislisteDto = getPreisliste(kundeDto.getVkpfArtikelpreislisteIIdStdpreisliste()) ;			
			return customerEntryMapper.mapDetailEntry(kundeDto, personalDto, preislisteDto) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e);
		}

		return null;
	}


	@GET
	@Path("/pricelist")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public CustomerPricelistReportDto getPriceListCustomerShortName(
			@QueryParam(Param.USERID) String userId,
			@QueryParam("customer_shortname") String customerShortSign,
			@QueryParam("filter_itemgroupcnr") String filterItemgroupCnr,
			@QueryParam("filter_itemgroupid") Integer filterItemgroupId,
			@QueryParam("filter_itemclasscnr") String filterItemclassCnr,
			@QueryParam("filter_itemclassid") Integer filterItemclassId,
			@QueryParam("filter_start_itemcnr") String filterItemRangeFrom,
			@QueryParam("filter_stop_itemcnr") String filterItemRangeTo,
			@QueryParam("filter_validitydate") String filterValidityDate,			
			@QueryParam("filter_withHidden") Boolean filterWithHidden,
			@QueryParam("filter_onlySpecialcondition") Boolean filterOnlySpecialcondition,
			@QueryParam("filter_withClientLanguage") Boolean filterWithClientLanguage,
			@QueryParam("filter_onlyForWebshop") Boolean filterOnlyForWebshop) {
		
		try {
			if(connectClient(userId) == null) return null ;
			if(StringHelper.isEmpty(customerShortSign)) {
				respondBadRequestValueMissing("customer_shortsign");
				return null ;
			}
	
			List<KundeDto> kundeDtos = kundeCall.kundeFindByKbezMandantCnr(customerShortSign) ;
			if(kundeDtos == null || kundeDtos.size() == 0) {
				respondNotFound("customer_shortname", customerShortSign);
				return null ;
			}
			
			Integer customerId = kundeDtos.get(0).getIId() ;
			return getPriceListImpl(customerId, filterItemgroupCnr, filterItemgroupId,
					filterItemclassCnr, filterItemclassId, filterItemRangeFrom, 
					filterItemRangeTo, filterValidityDate, filterWithHidden, 
					filterOnlySpecialcondition, filterWithClientLanguage, 
					filterOnlyForWebshop) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e);
		}
		
		return null ;
	}
	@Override
	@GET
	@Path("/{" + Param.CUSTOMERID + "}/pricelist")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public CustomerPricelistReportDto getPriceList(
			@QueryParam(Param.USERID) String userId,
			@PathParam(Param.CUSTOMERID) Integer customerId,
			@QueryParam("filter_itemgroupcnr") String filterItemgroupCnr,
			@QueryParam("filter_itemgroupid") Integer filterItemgroupId,
			@QueryParam("filter_itemclasscnr") String filterItemclassCnr,
			@QueryParam("filter_itemclassid") Integer filterItemclassId,
			@QueryParam("filter_start_itemcnr") String filterItemRangeFrom,
			@QueryParam("filter_stop_itemcnr") String filterItemRangeTo,
			@QueryParam("filter_validitydate") String filterValidityDate,			
			@QueryParam("filter_withHidden") Boolean filterWithHidden,
			@QueryParam("filter_onlySpecialcondition") Boolean filterOnlySpecialcondition,
			@QueryParam("filter_withClientLanguage") Boolean filterWithClientLanguage,
			@QueryParam("filter_onlyForWebshop") Boolean filterOnlyForWebshop) {

		if(connectClient(userId) == null) return null ;
			
		return getPriceListImpl(customerId, filterItemgroupCnr, filterItemgroupId,
				filterItemclassCnr, filterItemclassId, filterItemRangeFrom, 
				filterItemRangeTo, filterValidityDate, filterWithHidden, 
				filterOnlySpecialcondition, filterWithClientLanguage, 
				filterOnlyForWebshop) ;
//			
//			KundenpreislisteParams params = new KundenpreislisteParams(customerId) ;
//			if(!StringHelper.isEmpty(filterItemRangeFrom)) params.setItemCnrVon(filterItemRangeFrom) ;
//			if(!StringHelper.isEmpty(filterItemRangeTo)) params.setItemCnrBis(filterItemRangeTo) ;
//			if(!setupParamItemgroup(params, filterItemgroupId, filterItemgroupCnr)) return null ;
//			if(!setupParamItemclass(params, filterItemclassId, filterItemclassCnr)) return null ;
//			if(!setupParamValidityDate(params, filterValidityDate)) return null ;
//			if(filterWithHidden != null) params.setMitVersteckten(filterWithHidden) ;
//			if(filterOnlySpecialcondition != null) params.setNurSonderkonditionen(filterOnlySpecialcondition) ;
//			if(filterWithClientLanguage != null) params.setMitMandantensprache(filterWithClientLanguage) ;
//			if(filterOnlyForWebshop != null) params.setNurWebshop(filterOnlyForWebshop) ;
//			
//			return kundeReportCall.getKundenpreisliste(params);			
	}

	private CustomerPricelistReportDto getPriceListImpl(
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
		Boolean filterOnlyForWebshop) {
	
		try {
			KundenpreislisteParams params = new KundenpreislisteParams(customerId) ;
			if(!StringHelper.isEmpty(filterItemRangeFrom)) params.setItemCnrVon(filterItemRangeFrom) ;
			if(!StringHelper.isEmpty(filterItemRangeTo)) params.setItemCnrBis(filterItemRangeTo) ;
			if(!setupParamItemgroup(params, filterItemgroupId, filterItemgroupCnr)) return null ;
			if(!setupParamItemclass(params, filterItemclassId, filterItemclassCnr)) return null ;
			if(!setupParamValidityDate(params, filterValidityDate)) return null ;
			if(filterWithHidden != null) params.setMitVersteckten(filterWithHidden) ;
			if(filterOnlySpecialcondition != null) params.setNurSonderkonditionen(filterOnlySpecialcondition) ;
			if(filterWithClientLanguage != null) params.setMitMandantensprache(filterWithClientLanguage) ;
			if(filterOnlyForWebshop != null) params.setNurWebshop(filterOnlyForWebshop) ;
			
			return kundeReportCall.getKundenpreisliste(params);			
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			if(e.getCode() == EJBExceptionLP.FEHLER_KUNDE_IID_IN_MANDANT_NICHT_VORHANDEN)
				respondNotFound() ;
			else
				respondBadRequest(e) ;
		} catch(Throwable t) {
			System.out.println("uups") ;
		}
		
		return null ;		
	}
	
	private boolean setupParamValidityDate(KundenpreislisteParams params, String filterValidityDate) {
		if(StringHelper.isEmpty(filterValidityDate)) return true ;
		
		try {
			Calendar c = DatatypeConverter.parseDateTime(filterValidityDate) ;
			params.setGueltigkeitsDatum(new Date(c.getTimeInMillis())) ;
			return true ;
		} catch(IllegalArgumentException e) {
			System.out.println("illegalargument" + e.getMessage()) ;
		}

		respondBadRequest("filter_validitydate", filterValidityDate);
		return false ;
	}
	
	
	private boolean setupParamItemgroup(KundenpreislisteParams params, Integer filterItemgroupId, String filterItemgroupCnr) throws RemoteException, NamingException {
		if(filterItemgroupId != null) {
			ArtgruDto artgruDto = artikelCall.artikelgruppeFindByPrimaryKeyOhneExc(filterItemgroupId) ;
			if(artgruDto == null) {
				respondNotFound("filter_itemgroupid", filterItemgroupId.toString());
				return false ;
			}

			params.setArtikelgruppeId(artgruDto.getIId());
			return true ;
		}
		
		if(!StringHelper.isEmpty(filterItemgroupCnr)) {
			ArtgruDto artgruDto = artikelCall.artikelgruppeFindByCnrOhneExc(filterItemgroupCnr) ;
			if(artgruDto == null) {
				respondNotFound("filter_itemgroupcnr", filterItemgroupCnr) ;
				return false ;
			}
			
			params.setArtikelgruppeId(artgruDto.getIId()) ;
			return true ;
		}
		
		return true ;
	}

	private boolean setupParamItemclass(KundenpreislisteParams params, Integer filterItemclassId, String filterItemclassCnr) throws RemoteException, NamingException {
		if(filterItemclassId != null) {
			ArtklaDto artklaDto = artikelCall.artikelklasseFindByPrimaryKeyOhneExc(filterItemclassId) ;
			if(artklaDto == null) {
				respondNotFound("filter_itemclassid", filterItemclassId.toString()) ;
				return false ;
			}
			
			params.setArtikelklasseId(artklaDto.getIId()) ;
			return true ;
		}
		
		if(!StringHelper.isEmpty(filterItemclassCnr)) {
			ArtklaDto artklaDto = artikelCall.artikelklasseFindByCnrOhneExc(StringHelper.removeSqlDelimiters(filterItemclassCnr)) ;
			if(artklaDto == null) {
				respondNotFound("filter_itemclasscnr", filterItemclassCnr);
				return false ;
			}

			params.setArtikelklasseId(artklaDto.getIId()) ;
			return true ;
		}
		
		return true ;
	}
	
	private FilterKriterium buildFilterCompanyName(String companyName) throws RemoteException, NamingException {
		if(StringHelper.isEmpty(companyName)) return null ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				KundeFac.FLR_PARTNER_NAME1NACHNAMEFIRMAZEILE1, 
				StringHelper.removeSqlDelimiters(companyName),
				FilterKriterium.OPERATOR_LIKE, "",
				parameterCall.isPartnerSucheWildcardBeidseitig() ?
						FilterKriteriumDirekt.PROZENT_BOTH : FilterKriteriumDirekt.PROZENT_TRAILING, 
				true, true, Facade.MAX_UNBESCHRAENKT); 		
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
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

		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				PartnerFac.PARTNERQP1_ERWEITERTE_SUCHE, StringHelper.removeSqlDelimiters(extendedSearch),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_BOTH, true, true,
				Facade.MAX_UNBESCHRAENKT);
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}

	
	private FilterKriterium buildFilterWithCustomers(Boolean withCustomers, Boolean withProspectiveCustomers) {
		if(withCustomers == null) withCustomers = true ;
		if(withProspectiveCustomers == null) withProspectiveCustomers = true ;		
		if(withCustomers == false && withProspectiveCustomers == false) withCustomers = true ;

		String value = "" ;
		if(withCustomers && withProspectiveCustomers) {
			value = "1" ;
		} else {
			value = withCustomers ? "2" : "3" ;
		}
		
		return new FilterKriterium(
				"KUNDE_INTERESSENT", true, value, FilterKriterium.OPERATOR_EQUAL, false) ;
	}
	
	
	private FilterKriterium buildFilterWithHidden(Boolean withHidden) {
		return FilterHelper.createWithHidden(withHidden,
				KundeFac.FLR_PARTNER + "." + PartnerFac.FLR_PARTNER_VERSTECKT) ;
	}

	private VkpfartikelpreislisteDto getPreisliste(Integer preislisteId) throws NamingException, RemoteException {
		if(preislisteId == null) return null ;			
		return vkpreisfindungCall.vkpfartikelpreislisteFindByPrimaryKey(preislisteId) ;
	}
	
	private PersonalDto getPersonal(Integer personalId) throws NamingException, RemoteException {
		if(personalId == null) return null ;
		return personalCall.byPrimaryKeySmall(personalId) ;
	}
}
