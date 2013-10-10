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
	 * Die Kundenpreisliste &uuml;ber die Id des Kunden ermitteln</br>
	 * <p>Abh&auml;ngig vom Umfang der Artikel bzw. der Preisliste kann dieser Aufruf auch 
	 * mehrere Sekunden oder sogar Minuten dauern. Die R&uuml;ckgabe kann auch mehrere MB gro&szlig; sein.
	 * 
	 * @param userId ist der Token der durch die Anmeldung (<code>login</code>) erhalten wurde
	 * @param customerId die Id des Kunden f&uuml;r den die Preisliste ermittelt werden soll
	 * @param filterItemgroupCnr optional nur Artikel mit dieser Artikelgruppe selektieren
	 * @param filterItemgroupId optional nur Artikel mit dieser ArtikelgruppenId selektieren
	 * @param filterItemclassCnr optional nur Artikel mit dieser Artikelklassenummer
	 * @param filterItemclassId optional nur Artikel mit dieser ArtikelklassenId
	 * @param filterItemRangeFrom optional nur Artikel ab dieser Artikelnummer
	 * @param filterItemRangeTo optional nur Artikel bis (einschlie&szlig;lich) zu dieser Artikelnummer
	 * @param filterValidityDate optional mit dem angebenen Preisg&uuml;ltigkeitsdatum im ISO8601 Format 
	 * (Beispiel: JJJJ-MM-TT"T"hh:mm ... 2013-12-31T14:00Z oder 2013-12-31T14:59+01:00)
	 * @param filterWithHidden mit <code>true</code> werden auch versteckte Artikel in die Preisliste aufgenommen
	 * @param filterOnlySpecialcondition mit <code>true</code> werden nur die Artikel mit Sonderkondition in die
	 * Preisliste aufgenommen
	 * @param filterWithClientLanguage mit <code>true</code> werden die Artikelbezeichnungen zus&auml;tzlich in der
	 * Mandantensprache ausgegeben
	 * @param filterOnlyForWebshop mit <code>true</code> werden nur jene Artikel in die Preisliste aufgenommen, die
	 * explizit im Webshop verf&uuml;gbar sein sollen, also eine beliebige Shopgruppe innerhalb HELIUM V haben
	 * @return
	 */	
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


	/**
	 * Die Kundenpreisliste &uuml;ber das Kurzzeichen des Kunden ermitteln
	 * <p>Abh&auml;ngig vom Umfang der Artikel bzw. der Preisliste kann dieser Aufruf auch 
	 * mehrere Sekunden oder sogar Minuten dauern. Die R&uuml;ckgabe kann auch mehrere MB gro&szlig; sein.
	 * 
	 * @param userId ist der Token der durch die Anmeldung (<code>login</code>) erhalten wurde
	 * @param customerShortSign ist das Kurzzeichen jenes Kunden f&uuml;r den die Preisliste ermittelt
	 * werden soll. Gibt es f&uuml;r das angegebene Kurzzeichen mehr als einen Kunden, wird der "erste"
	 * (beliebige Reihenfolge) Kunde verwendet
	 * @param filterItemgroupCnr optional nur Artikel mit dieser Artikelgruppe selektieren
	 * @param filterItemgroupId optional nur Artikel mit dieser ArtikelgruppenId selektieren
	 * @param filterItemclassCnr optional nur Artikel mit dieser Artikelklassenummer
	 * @param filterItemclassId optional nur Artikel mit dieser ArtikelklassenId
	 * @param filterItemRangeFrom optional nur Artikel ab dieser Artikelnummer
	 * @param filterItemRangeTo optional nur Artikel bis (einschlie&szlig;lich) zu dieser Artikelnummer
	 * @param filterValidityDate optional mit dem angebenen Preisg&uuml;ltigkeitsdatum im ISO8601 Format 
	 * (Beispiel: JJJJ-MM-TT"T"hh:mm ... 2013-12-31T14:00Z oder 2013-12-31T14:59+01:00)
	 * @param filterWithHidden mit <code>true</code> werden auch versteckte Artikel in die Preisliste aufgenommen
	 * @param filterOnlySpecialcondition mit <code>true</code> werden nur die Artikel mit Sonderkondition in die
	 * Preisliste aufgenommen
	 * @param filterWithClientLanguage mit <code>true</code> werden die Artikelbezeichnungen zus&auml;tzlich in der
	 * Mandantensprache ausgegeben
	 * @param filterOnlyForWebshop mit <code>true</code> werden nur jene Artikel in die Preisliste aufgenommen, die
	 * explizit im Webshop verf&uuml;gbar sein sollen, also eine beliebige Shopgruppe innerhalb HELIUM V haben
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
	 * @param userId ist der Token der durch die Anmeldung (<code>login</code>) erhalten wurde
	 * @param customerId ist die Id des Kunden f&uuml;r den die Daten ermittelt werden sollen.
	 * @return
	 */
	CustomerDetailEntry getCustomer(String userId, Integer customerId) ;
}
