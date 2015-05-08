package com.heliumv.api.item;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.tools.FilterHelper;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;

/**
 * Kann FilterKriterien zusammenstellen</br>
 * <p>Allgemeine Bedienungshinweise:</p>
 * <p>Zuerst mittels "addFilter...()" die gew&uuml;nschten Filter hinzuf&uuml;gen und dann mittels
 * {@link #andFilterBlock()} oder auch {@link #orFilterBlock()} jenen FilterBlock zusammenstellen,
 * den anyQuery.getDefaultQueryParameters(filterblock) ben&ouml;tigt.</p>
 *
 * @author Gerold
 */
public class ItemListBuilder {
	@Autowired
	private IParameterCall parameterCall ;
	@Autowired
	private IArtikelCall artikelCall ;
	
	private FilterKriteriumCollector filterCollector ;
	
	public ItemListBuilder() {
		clear() ;
	}
	
	public ItemListBuilder clear() {
		filterCollector = new FilterKriteriumCollector() ;
		return this ;
	}
	
	/**
	 * Der DefaultFilter verkn&uuml;pft die Bedingungen mit AND
	 * @return den FilterBlock
	 */
	public FilterBlock filterBlock() {
		return andFilterBlock() ;
	}
	
	public FilterBlock andFilterBlock() {
		return new FilterBlock(getFilterCollector().asArray(), "AND") ;
	}
	
	public FilterBlock orFilterBlock() {
		return new FilterBlock(getFilterCollector().asArray(), "OR") ;
	}
	
	public FilterKriteriumCollector getFilterCollector() {
		return filterCollector;
	}

	public void setFilterCollector(FilterKriteriumCollector filterCollector) {
		this.filterCollector = filterCollector;
	}

	public ItemListBuilder addFilterCnr(String filterCnr) throws NamingException, RemoteException {
		return addFilter(buildFilterCnr(filterCnr)) ;
	}

	public ItemListBuilder addFilterTextSearch(String filterTextSearch) throws RemoteException, NamingException {
		return addFilter(buildFilterTextSearch(filterTextSearch)) ;
	}

	public ItemListBuilder addFilterDeliveryCnr(String deliveryCnr) throws RemoteException, NamingException {
		return addFilter(buildFilterDeliveryCnr(deliveryCnr)) ;
	}

	public ItemListBuilder addFilterItemGroupClass(String itemGroupClass) throws RemoteException, NamingException {
		return addFilter(buildFilterItemGroupClass(itemGroupClass)) ;
	}
	
	public ItemListBuilder addFilterItemGroupClass(Integer itemgroupId) throws RemoteException, NamingException {
		return addFilter(buildFilterItemGroupId(itemgroupId)) ;
//		ArtgruDto artgruDto = artikelCall.artikelgruppeFindByPrimaryKeyOhneExc(itemgroupId) ;
//		if(artgruDto != null) {
//			addFilter(buildFilterItemGroupClass(artgruDto.getCNr())) ;
//		}
//		return this ;
	}
	
	public ItemListBuilder addFilterItemGroupId(Integer itemgroupId) throws RemoteException, NamingException {
		return addFilter(buildFilterItemGroupId(itemgroupId)) ;
	}
	
	public ItemListBuilder addFilterItemGroupIds(ShopGroupIdList itemgroupIds) throws RemoteException, NamingException {
		return addFilter(buildFilterItemGroupIds(itemgroupIds)) ;
	}
	
	public ItemListBuilder addFilterItemReferenceNr(String itemReferenceNr) throws RemoteException, NamingException {
		return addFilter(buildFilterItemReferenceNr(itemReferenceNr)) ;
	}
	
	public ItemListBuilder addFilterWithHidden(Boolean withHidden) throws RemoteException, NamingException {
		return addFilter(buildFilterWithHidden(withHidden)) ;
	}
	
	public ItemListBuilder addFilterIdentArtikel() throws RemoteException, NamingException {
		return addFilter(buildFilterIdentArtikel()) ;
	}

	/**
	 * Einschr&auml;nkung auf St&uuml;cklisten des Partners</br>
	 * <p>Es werden nur noch St&uuml;cklisten geliefert, die entweder dem angegebenen Partner
	 * geh&ouml;ren, oder solche die keine Partnerzuordnung haben.</p>
	 * <p>Achtung: Am Server wird die genaue Einschr&auml;nkung durchgef&uuml;hrt</p>
	 *  
	 * @param partnerId
	 * @return Filterkriterium eingeschr&auml;nkt auf die Stuecklisten des Partners
	 * @throws RemoteException
	 * @throws NamingException
	 */
	public ItemListBuilder addFilterStuecklistenPartner(Integer partnerId) throws RemoteException, NamingException {
		return addFilter(buildFilterStuecklistenPartner(partnerId)) ;
	}
	
	public ItemListBuilder addFilterCustomerItemCnr(String customerItemCnr) throws RemoteException, NamingException {
		return addFilter(buildFilterCustomerItemCnr(customerItemCnr));
	}
	/**
	 * Einen Filter (FilterKriterium) anh&auml;ngen</br>
	 * <p>Diese Funktion ist f&uuml;r jene gedacht, die wissen was sie tun. Die anderen nutzen bitte
	 * die spezifischen Filtermethoden "addFilter*()".</p>
	 * @param criterium das Filterkriterium das hinzugef&uuml;gt werden soll. Es darf auch null sein
	 *  und wird dann ignoriert.
	 * @return mich
	 */
	public ItemListBuilder addFilter(FilterKriterium criterium) {
		filterCollector.add(criterium) ;
		return this ;
	}
	
	public ItemListBuilder addFilter(List<FilterKriterium> criterias) {
		filterCollector.addAll(criterias) ;
		return this ;
	}
	
	protected FilterKriterium buildFilterCnr(String filterCnr) throws RemoteException, NamingException {
		if(StringHelper.isEmpty(filterCnr)) return null ;
		
		int itemCnrLength = parameterCall.getMaximaleLaengeArtikelnummer() ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"artikelliste.c_nr",StringHelper.removeSqlDelimiters(filterCnr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING,
				true, 
				true, itemCnrLength); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	protected FilterKriterium buildFilterTextSearch(String filterCnr) throws RemoteException, NamingException {
/*
		return new FilterKriteriumDirekt(
				ArtikelFac.FLR_ARTIKELLISTE_C_VOLLTEXT, "",
				FilterKriterium.OPERATOR_LIKE, LPMain.getInstance()
						.getTextRespectUISPr("lp.textsuche"),
				FilterKriteriumDirekt.EXTENDED_SEARCH, false, true,
				Facade.MAX_UNBESCHRAENKT); // wrapWithSingleQuotes
		
 */
		
		
		if(StringHelper.isEmpty(filterCnr)) return null ;
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				ArtikelFac.FLR_ARTIKELLISTE_C_VOLLTEXT,StringHelper.removeSqlDelimiters(filterCnr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.EXTENDED_SEARCH,
				false, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	protected FilterKriterium buildFilterDeliveryCnr(String deliveryCnr) throws RemoteException, NamingException {
		if(StringHelper.isEmpty(deliveryCnr)) return null ;
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				ArtikelFac.FLR_ARTIKELLIEFERANT_C_ARTIKELNRLIEFERANT, StringHelper.removeSqlDelimiters(deliveryCnr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_BOTH,
				true, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	protected FilterKriterium buildFilterItemGroupClass(String itemGroupClass) throws RemoteException, NamingException {
		if(StringHelper.isEmpty(itemGroupClass)) return null ;
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"akag", StringHelper.removeSqlDelimiters(itemGroupClass),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_BOTH,
				true, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}

	protected FilterKriterium buildFilterItemReferenceNr(String itemReferenceNr) throws RemoteException, NamingException {
		if(StringHelper.isEmpty(itemReferenceNr)) return null ;
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"artikelliste."+ ArtikelFac.FLR_ARTIKELLISTE_C_REFERENZNR, StringHelper.removeSqlDelimiters(itemReferenceNr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_BOTH,
				true, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	protected FilterKriterium buildFilterWithHidden(Boolean withHidden) {
		return FilterHelper.createWithHidden(withHidden, ArtikelFac.FLR_ARTIKELLISTE_B_VERSTECKT) ;
	}
	
	protected FilterKriterium buildFilterIdentArtikel() {
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"artikelliste."+ ArtikelFac.FLR_ARTIKELLISTE_ARTIKELART_C_NR, ArtikelFac.ARTIKELART_ARTIKEL,
				FilterKriterium.OPERATOR_EQUAL, "",
				FilterKriteriumDirekt.PROZENT_BOTH,
				true, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
		
	}
	
	protected FilterKriterium buildFilterStuecklistenPartner(Integer partnerId) {
		if(partnerId == null) return null ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"artikelliste."+ ArtikelFac.FLR_ARTIKELLISTE_STUECKLISTE_PARTNER_ID, partnerId.toString(),
				FilterKriterium.OPERATOR_EQUAL, "",
				FilterKriteriumDirekt.PROZENT_NONE,
				false, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;		
	}
	
	protected FilterKriterium buildFilterItemGroupId(Integer itemgroupId) throws RemoteException, NamingException {
		if(itemgroupId == null) return null ;
		if(itemgroupId == 0 || itemgroupId == 1) {
			FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
					ArtikelFac.FLR_ARTIKELLISTE_SHOPGRUPPE_ID, "[" + itemgroupId + "]", FilterKriterium.OPERATOR_EQUAL, "",
					FilterKriteriumDirekt.PROZENT_NONE, false, false, Facade.MAX_UNBESCHRAENKT) ;
			return fk ;
		}
		return new FilterKriteriumDirekt(
						ArtikelFac.FLR_ARTIKELLISTE_SHOPGRUPPE_ID, "" + itemgroupId, FilterKriterium.OPERATOR_EQUAL, "",
						FilterKriteriumDirekt.PROZENT_NONE, false, false, Facade.MAX_UNBESCHRAENKT) ;
//		if(itemgroupId == null) return null ; 
//		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
//				"ag.i_id", ""+itemgroupId,
//				FilterKriterium.OPERATOR_EQUAL, "",
//				FilterKriteriumDirekt.PROZENT_NONE,
//				false, 
//				false, Facade.MAX_UNBESCHRAENKT); 
//		fk.wrapWithProzent() ;
//		fk.wrapWithSingleQuotes() ;
//		return fk ;
	}

	protected FilterKriterium buildFilterItemGroupIds(ShopGroupIdList itemgroupIds) throws RemoteException, NamingException {
		if(itemgroupIds == null || itemgroupIds.getEntries() == null || itemgroupIds.getEntries().size() == 0) return null ;
		if(itemgroupIds.getEntries().size() == 1) return buildFilterItemGroupId(itemgroupIds.getEntries().get(0)) ;
		String ids = "" ;
		for (Integer id : itemgroupIds.getEntries()) {
			ids = ids + (ids.length() > 0 ? "," : "") + id ;
		}
		return new FilterKriteriumDirekt(
						ArtikelFac.FLR_ARTIKELLISTE_SHOPGRUPPE_ID, "(" + ids + ")", FilterKriterium.OPERATOR_IN, "",
						FilterKriteriumDirekt.PROZENT_NONE, false, false, Facade.MAX_UNBESCHRAENKT) ;
	}
	
	protected FilterKriterium buildFilterCustomerItemCnr(String customerItemCnr) throws RemoteException, NamingException {
		if(StringHelper.isEmpty(customerItemCnr)) return null ;
			
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"soko.c_kundeartikelnummer", StringHelper.removeSqlDelimiters(customerItemCnr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING,
				true, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}	
}
