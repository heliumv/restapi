/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
package com.heliumv.api.item;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.annotation.HvFlrMapper;
import com.heliumv.factory.IKundeCall;
import com.heliumv.factory.IVkPreisfindungCall;
import com.heliumv.factory.legacy.AllArtikelgruppeEntry;
import com.heliumv.factory.loader.IItemLoaderAttribute;
import com.heliumv.factory.query.ItemV11Query;
import com.heliumv.factory.query.ShopGroupQuery;
import com.heliumv.feature.FeatureFactory;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.ArtikellisteHandlerFeature;
import com.lp.server.artikel.service.VerkaufspreisDto;
import com.lp.server.artikel.service.VkpreisfindungDto;
import com.lp.server.partner.service.KundeDto;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryParametersFeatures;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.server.util.fastlanereader.service.query.SortierKriterium;
import com.lp.util.EJBExceptionLP;

/**
 * Funktionalit&auml;t rund um die Resource <b>Artikel</b></br>
 * <p>Grundvoraussetzung f&uuml;r eine erfolgreiche Benutzung dieser Resource ist,
 * dass der HELIUM V Mandant das Modul "Artikel" installiert hat. F&uuml;r praktisch
 * alle Zugriffe auf den Artikel muss der API Benutzer zumindest Leserechte auf
 * den Artikel haben.
 * </p>
 * <p>&Auml;nderungen in dieser Version<p>
 * <p>Die Lagerst&auml;nde <code>stockslist</code> werden nun als typisiertes Ergebnis geliefert</p>
 * @author Gerold
 */
@Service("hvItemV1_1")
@Path("/api/v11/item")
public class ItemApiV11 extends ItemApi implements IItemApiV11 {	
	@Autowired
	private FeatureFactory featureFactory ;
		
	@Autowired
	private IKundeCall kundeCall ;
	
	@Autowired
	private IVkPreisfindungCall vkpreisfindungCall ;
	
	@Autowired
	private ItemV11Query itemV11Query ;
	
	@Autowired
	private ShopGroupQuery shopGroupQuery ;
	
	@Override
	@GET
	@Path("/stockslist")
	@Produces({FORMAT_JSON, FORMAT_XML})	
	public StockAmountEntryList getStockAmountList(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.ITEMCNR) String itemCnr, 
			@QueryParam("returnItemInfo") Boolean returnItemInfo) {
		StockAmountEntryList stockAmounts = new StockAmountEntryList() ;
		stockAmounts.setEntries(getStockAmountImpl(userId, itemCnr, returnItemInfo));
		return stockAmounts ;
	}
	
	@Override
	@GET
	@Path("/itemv1")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public ItemV1Entry findItemV1ByAttributes(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.ITEMCNR) String cnr, 
			@QueryParam("itemSerialnumber") String serialnumber,
			@QueryParam("addComments") Boolean addComments, 
			@QueryParam("addStockAmountInfos") Boolean addStockAmountInfos) {

		if(StringHelper.isEmpty(cnr) && StringHelper.isEmpty(serialnumber)) {
			respondBadRequest("itemCnr", cnr) ;
			return null ;
		}

		if(connectClient(userId) == null) return null ;

		Set<IItemLoaderAttribute> attributes = getAttributes(addComments, addStockAmountInfos) ;		
		ItemEntryInternal itemEntryInternal = findItemBySerialnumberOrCnr(serialnumber, cnr, attributes) ;
		if(itemEntryInternal == null) return null ;
		
		return mapFromInternalV1(itemEntryInternal) ;
	}

	protected ItemV1Entry mapFromInternalV1(ItemEntryInternal itemEntryInternal) {
		if(itemEntryInternal == null) return null ;
		
		ItemV1Entry itemEntry = getModelMapper().map(itemEntryInternal, ItemV1Entry.class) ;
		return itemEntry ;
	}
	
	@GET
	@Path("/{" + Param.ITEMID + "}/price")
	public BigDecimal getPrice(
			@QueryParam(Param.USERID) String userId,
			@PathParam(Param.ITEMID) Integer itemId,
			@QueryParam(Param.CUSTOMERID) Integer customerId,
			@QueryParam("amount") BigDecimal amount,
			@QueryParam("unit") String unitCnr) throws NamingException, RemoteException, EJBExceptionLP {
		if(connectClient(userId) == null) return null ;

		ArtikelDto itemDto = artikelCall.artikelFindByPrimaryKeySmallOhneExc(itemId) ;
		if(itemDto == null) {
			respondNotFound(Param.ITEMID, itemId.toString());
			return null ;
		}
		
		KundeDto customerDto = null ;
		if(customerId == null) {
			if(!featureFactory.hasCustomerPartlist()) {
				respondBadRequestValueMissing(Param.CUSTOMERID);
				return null ;
			}
			customerDto = kundeCall.kundeFindByAnsprechpartnerIdcNrMandantOhneExc(
					featureFactory.getAnsprechpartnerId()) ;
		} else {
			customerDto = kundeCall.kundeFindByPrimaryKeyOhneExc(customerId) ;
		}

		if(customerDto == null || !globalInfo.getMandant().equals(customerDto.getMandantCNr())) {
			respondNotFound();
			return null ;
		}
		
		// TODO Umrechnen in die Lagereinheit		
		java.sql.Date d = new Date(System.currentTimeMillis()) ;
		
		Integer mwstsatzbeId = itemDto.getMwstsatzbezIId() ;
		if(mwstsatzbeId == null) {
			mwstsatzbeId = customerDto.getMwstsatzbezIId() ;
		}
		
		VkpreisfindungDto vkpreisfindungDto = vkpreisfindungCall.verkaufspreisfindung(
				itemDto.getIId(), customerDto.getIId(), amount, d,
				customerDto.getVkpfArtikelpreislisteIIdStdpreisliste(), 
				mwstsatzbeId, customerDto.getCWaehrung()) ;

		BigDecimal p = getPriceFromPreisfindung(vkpreisfindungDto) ;
		return p ;
		
	}
	
	private BigDecimal getMinimumPrice(BigDecimal minimum, VerkaufspreisDto priceDto) {
		if(priceDto != null && priceDto.nettopreis != null) {
			return null == minimum ? priceDto.nettopreis : minimum.min(priceDto.nettopreis) ; 
		}
		
		return minimum;		
	}
	
	private BigDecimal getPriceFromPreisfindung(VkpreisfindungDto vkPreisDto) {
		BigDecimal p = getMinimumPrice(null, vkPreisDto.getVkpStufe3()) ;
		if(p != null) return p ;

		p = getMinimumPrice(null, vkPreisDto.getVkpStufe2()) ;
		if(p != null) return p ;

		p = getMinimumPrice(null, vkPreisDto.getVkpStufe1()) ;
		if(p != null) return p ;

		p = getMinimumPrice(null, vkPreisDto.getVkpPreisbasis())  ;
		if(p != null) return p ;
		
		return BigDecimal.ZERO ;
	}
	
	@Override
	@GET
	@Path("/list")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public ItemEntryList getItemsList(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex, 
			@QueryParam(Filter.CNR) String filterCnr, 
			@QueryParam("filter_textsearch") String filterTextSearch,
			@QueryParam("filter_deliverycnr") String filterDeliveryCnr,
			@QueryParam("filter_itemgroupclass") String filterItemGroupClass,
			@QueryParam("filter_itemreferencenr") String filterItemReferenceNr,
			@QueryParam(Filter.HIDDEN) Boolean filterWithHidden,
			@QueryParam("filter_itemgroupid") Integer filterItemgroupId,
			@QueryParam("filter_customeritemcnr") String filterCustomerItemCnr,
			@QueryParam("filter_itemgroupids") String filterItemgroupIds,
			@QueryParam(Param.ORDERBY) String orderBy) throws RemoteException, NamingException, EJBExceptionLP, Exception {
		if(connectClient(userId) == null) return new ItemEntryList() ;
		ShopGroupIdList shopGroupIdList = null ;
		if(StringHelper.hasContent(filterItemgroupIds)) {
			String[] ids = filterItemgroupIds.split("\\,") ;
			ArrayList<Integer> integerIds = new ArrayList<Integer>() ;
			for (String stringId : ids) {
				try {
					integerIds.add(Integer.parseInt(stringId) ) ;
				} catch(NumberFormatException e) {
					respondBadRequest("filter_itemgroupids", "[" + stringId + "] not numeric or parseable. (" + filterItemgroupIds + ")");
					return new ItemEntryList() ;
				}
			}
			shopGroupIdList = new ShopGroupIdList(integerIds) ;
		}
		return getItemsListImpl(limit, startIndex, filterCnr, filterTextSearch,
				filterDeliveryCnr, filterItemGroupClass, filterItemReferenceNr,
				filterWithHidden, filterItemgroupId, filterCustomerItemCnr, shopGroupIdList, orderBy) ;
	}
	
	public ItemEntryList getItemsListImpl(
			Integer limit,
			Integer startIndex, 
			String filterCnr, 
			String filterTextSearch,
			String filterDeliveryCnr,
			String filterItemGroupClass,
			String filterItemReferenceNr,
			Boolean filterWithHidden,
			Integer filterItemgroupId, 
			String filterCustomerItemCnr,
			ShopGroupIdList filterItemgroupIds, String orderBy) throws RemoteException, NamingException, EJBExceptionLP, Exception {
		
		featureFactory.getObject().applyItemListFilter(itemlistBuilder, filterCnr,
				filterTextSearch, filterDeliveryCnr, filterItemGroupClass, filterItemReferenceNr, 
				filterWithHidden, filterItemgroupId, filterCustomerItemCnr, filterItemgroupIds);
		
		QueryParametersFeatures params = itemV11Query.getFeatureQueryParameters(itemlistBuilder.andFilterBlock()) ;
		params.setLimit(limit) ;
		params.setKeyOfSelectedRow(startIndex) ;
		params.addFeature(ArtikellisteHandlerFeature.EINHEIT_CNR) ;

		if(featureFactory.hasCustomerPartlist()) {
			params.addFeatureValue(ArtikellisteHandlerFeature.KUNDENARTIKELNUMMER_CNR,
					featureFactory.getPartnerIdFromAnsprechpartnerId().toString());
			params.addFeature(ArtikellisteHandlerFeature.SOKO);
		}
		
		params.setSortKrit(buildOrderBy(orderBy, ItemEntry.class));
		QueryResult result = itemV11Query.setQuery(params) ;
		
		ItemEntryList entryList = new ItemEntryList() ;
		entryList.setEntries(itemV11Query.getResultList(result)) ;	
		entryList.setRowCount(result.getRowCount());
		
		return entryList ;		
	}
	
	private SortierKriterium[] buildOrderBy(String orderBy, Class<?> theClass) {
		if(StringHelper.isEmpty(orderBy)) return null ;

		String[] orderbyTokens = orderBy.split(",") ;
		if(orderbyTokens.length == 0) return null ;

		List<SortierKriterium> crits = new ArrayList<SortierKriterium>() ;
		Method[] methods = theClass.getMethods() ;
		
		for (String orderbyToken : orderbyTokens) {
			String[] token = orderbyToken.split(" ") ;
			boolean ascending = true ;
			if(token.length > 1) {
				ascending = "asc".equals(token[1]) ;
			}
			
			for(Method theMethod : methods) {
				if(!theMethod.getName().startsWith("set")) continue ;

				String variable = theMethod.getName().substring(3).toLowerCase() ;
				if(token[0].toLowerCase().equals(variable)) {
					
					HvFlrMapper flrMapper = theMethod.getAnnotation(HvFlrMapper.class) ;
					String sortName = flrMapper != null ? flrMapper.flrFieldName() : variable ;
					if(sortName.length() == 0) {
						throw new IllegalArgumentException("Unknown mapping for '" + token[0] + "'") ;
					}
					
					SortierKriterium crit = new SortierKriterium(sortName, true, 
							ascending ? SortierKriterium.SORT_ASC : SortierKriterium.SORT_DESC) ;
					crits.add(crit) ;
				}
			}
		}		
		
		return crits.toArray(new SortierKriterium[0]) ;
	}
	
	@Override
	@GET
	@Path("/grouplist")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public ItemGroupEntryList getItemGroupsList(
			@QueryParam(Param.USERID) String userId) throws RemoteException, NamingException, EJBExceptionLP, Exception {
		if(connectClient(userId) == null) return new ItemGroupEntryList() ;
		List<AllArtikelgruppeEntry> entries = artikelCall.getAllArtikelgruppeSpr() ;
		
		List<ItemGroupEntry> groupEntries = new ArrayList<ItemGroupEntry>();
		for (AllArtikelgruppeEntry artikelGruppeEntry : entries) {
			ItemGroupEntry groupEntry = new ItemGroupEntry() ;
			groupEntry.setId(artikelGruppeEntry.getId());
			groupEntry.setCnr(artikelGruppeEntry.getKey());
			groupEntry.setDescription(artikelGruppeEntry.getDescription());
			
			groupEntries.add(groupEntry) ;
		}
		
		ItemGroupEntryList entryList = new ItemGroupEntryList() ;
		entryList.setEntries(groupEntries);
		return entryList ;
	}
	
	
	@Override
	@GET
	@Path("/shopgrouplist")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public ShopGroupEntryList getShopGroupsList(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex) throws RemoteException, NamingException, EJBExceptionLP, Exception {
		if(connectClient(userId) == null) return new ShopGroupEntryList() ;

		QueryParameters params = shopGroupQuery.getDefaultQueryParameters() ;
		params.setLimit(limit) ;
		params.setKeyOfSelectedRow(startIndex) ;

		QueryResult result = shopGroupQuery.setQuery(params) ;
		List<ShopGroupEntry> entries = shopGroupQuery.getResultList(result) ;		
		ShopGroupEntryList entryList = new ShopGroupEntryList(entries) ;
		return entryList ;		
	}
}
