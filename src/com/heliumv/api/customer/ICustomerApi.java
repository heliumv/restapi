package com.heliumv.api.customer;

import java.util.List;

import com.lp.server.partner.service.CustomerPricelistReportDto;

public interface ICustomerApi {
	/**
	 * Eine Liste aller - den Filterkriterien entsprechenden - Kunden ermitteln.
	 * 
	 * @param userId ist der Token der durch die Anmeldung (<code>login</code>) erhalten wurde
	 * @param limit die Anzahl der zu liefernden Datens&auml;tze. Default sind 50
	 * @param startIndex der x-te Eintrag ab dem die Datens&auml;tze geliefert werden sollen
	 * @param filterCompany schr&auml;nkt die Ausgabe auf jene Kunden ein, die den Firmennamen 
	 *  (<code>filterCompany</code>) beinhalten
	 * @param filterCity schr&auml;nkt die Ausgabe auf jene Kunden ein, deren Adresse sich in der 
	 * angegebenen Stadt befindet
	 * @param filterExtendedSearch schr&auml;nkt die Ausgabe auf jene Kunden ein, bei denen mit
	 * der erweiterten Suche der angegebene Text enthalten ist
	 * @param filterWithCustomers selektiert jene Eintr&auml;ge, bei denen der Datensatz als 
	 *  <code>Kunde</code> qualifiziert sind
	 * @param filterWithProspectiveCustomers selektiert jene Eintr&auml;ge, bei denen es sich um
	 * einen <code>Interessenten</code> handelt.
	 * @param filterWithHidden mit <true> werden auch versteckte Kunden selektiert
	 * @return
	 */
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
	
	/**
	 * Die Kundenpreisliste &uuml;ber die Id des Kunden ermitteln
	 * 
	 * @param userId ist der Token der durch die Anmeldung (<code>login</code>) erhalten wurde
	 * @param customerId die Id des Kunden f&uuml;r den die Preisliste ermittelt werden soll
	 * @param filterItemgroupCnr optional nur Artikel mit dieser Artikelgruppe selektieren
	 * @param filterItemgroupId optional nur Artikel mit dieser ArtikelgruppenId selektieren
	 * @param filterItemclassCnr optional nur Artikel mit dieser Artikelklasse
	 * @param filterItemclassId
	 * @param filterItemRangeFrom
	 * @param filterItemRangeTo
	 * @param filterValidityDate
	 * @param filterWithHidden
	 * @param filterOnlySpecialcondition
	 * @param filterWithClientLanguage
	 * @param filterOnlyForWebshop
	 * @return
	 */	CustomerPricelistReportDto getPriceList(
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

	/**
	 * Die Kundenpreisliste &uuml;ber das Kurzzeichen des Kunden ermitteln
	 * 
	 * @param userId
	 * @param customerShortSign
	 * @param filterItemgroupCnr
	 * @param filterItemgroupId
	 * @param filterItemclassCnr
	 * @param filterItemclassId
	 * @param filterItemRangeFrom
	 * @param filterItemRangeTo
	 * @param filterValidityDate
	 * @param filterWithHidden
	 * @param filterOnlySpecialcondition
	 * @param filterWithClientLanguage
	 * @param filterOnlyForWebshop
	 * @return
	 */
	CustomerPricelistReportDto getPriceListCustomerShortName(
			String userId,
			String customerShortSign,
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
	
	/**
	 * Die Kundendaten eines Kunden ermitteln, der &uuml;ber seine Id bekannt ist.
	 * 
	 * @param userId
	 * @param customerId
	 * @return
	 */
	CustomerDetailEntry getCustomer(String userId, Integer customerId) ;
}
