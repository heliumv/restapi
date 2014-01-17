package com.heliumv.api.item;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
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

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.ILagerCall;
import com.heliumv.factory.IPanelCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.legacy.AllLagerEntry;
import com.heliumv.factory.legacy.PaneldatenPair;
import com.heliumv.factory.loader.IArtikelLoaderCall;
import com.heliumv.factory.loader.IItemLoaderAttribute;
import com.heliumv.factory.loader.ItemLoaderComments;
import com.heliumv.factory.loader.ItemLoaderStockinfoSummary;
import com.heliumv.factory.query.ItemQuery;
import com.heliumv.tools.FilterHelper;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtgruDto;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.artikel.service.LagerDto;
import com.lp.server.system.service.PanelFac;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.util.EJBExceptionLP;

/**
 * Funktionalit&auml;t rund um die Resource <b>Artikel</b></br>
 * <p>Grundvoraussetzung f&uuml;r eine erfolgreiche Benutzung dieser Resource ist,
 * dass der HELIUM V Mandant das Modul "Artikel" installiert hat. F&uuml;r praktisch
 * alle Zugriffe auf den Artikel muss der API Benutzer zumindest Leserechte auf
 * den Artikel haben.
 * </p>
 * 
 * @author Gerold
 */
@Service("hvItem")
@Path("/api/v1/item")
public class ItemApi extends BaseApi implements IItemApi {
	@Autowired
	private IArtikelCall artikelCall ;

	@Autowired
	private IArtikelLoaderCall artikelLoaderCall ;
	@Autowired
	private ItemLoaderComments itemloaderComments ;
	@Autowired
	private ItemLoaderStockinfoSummary itemloaderStockinfoSummary ;
	
	@Autowired
	private ILagerCall lagerCall ;

	@Autowired
	private ItemQuery itemQuery ;
	
	@Autowired
	private IParameterCall parameterCall ;
	
	@Autowired
	private IPanelCall panelCall ;
	
	@Autowired
	private ItemGroupEntryMapper itemgroupEntryMapper ;
	
	@Autowired
	private ItemPropertyEntryMapper itempropertyEntryMapper ;
	
	@Override
	@GET
	@Path("/list")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<ItemEntry> getItems(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex, 
			@QueryParam(Filter.CNR) String filterCnr, 
			@QueryParam("filter_textsearch") String filterTextSearch,
			@QueryParam("filter_deliverycnr") String filterDeliveryCnr,
			@QueryParam("filter_itemgroupclass") String filterItemGroupClass,
			@QueryParam("filter_itemreferencenr") String filterItemReferenceNr,
			@QueryParam(Filter.HIDDEN) Boolean filterWithHidden) {
		List<ItemEntry> itemEntries = new ArrayList<ItemEntry>() ;
		try {
			if(connectClient(userId) == null) return itemEntries ;

			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(buildFilterCnr(filterCnr)) ;
			collector.add(buildFilterTextSearch(filterTextSearch)) ;
			collector.add(buildFilterDeliveryCnr(filterDeliveryCnr)) ;
			collector.add(buildFilterItemGroupClass(filterItemGroupClass)) ;
			collector.add(buildFilterItemReferenceNr(filterItemReferenceNr)) ;
			
			collector.add(buildFilterWithHidden(filterWithHidden)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND") ;
			
			QueryParameters params = itemQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
			
			QueryResult result = itemQuery.setQuery(params) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
			
			itemEntries = itemQuery.getResultList(result) ;	
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
		
		return itemEntries ;
	}


	@Override
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})
	public ItemEntry findItemByAttributes(
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

		Set<IItemLoaderAttribute> attributes = new HashSet<IItemLoaderAttribute>() ;
		if(addComments != null && addComments) {
			attributes.add(itemloaderComments) ;
		}
		if(addStockAmountInfos != null && addStockAmountInfos) {
			attributes.add(itemloaderStockinfoSummary) ;
		}
		
		try {
			if(!StringHelper.isEmpty(serialnumber)) {
				ItemEntry itemEntry = findItemEntryBySerialnumberCnr(serialnumber, cnr, attributes) ;
				if(itemEntry == null) {
					respondNotFound() ;				
				}
				return itemEntry ;
			}

			ItemEntry itemEntry = findItemEntryByCnrImpl(cnr, attributes) ;
			if(itemEntry == null) {
				respondNotFound() ;				
			}
			return itemEntry ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
		
		return null;
	}
	

	@Override
	@GET
	@Path("/stocks")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<StockAmountEntry> getStockAmount(
			@QueryParam(Param.USERID) String userId, 
			@QueryParam(Param.ITEMCNR) String itemCnr,
			@QueryParam("returnItemInfo") Boolean returnItemInfo) {
		return getStockAmountImpl(userId, itemCnr, returnItemInfo);
	}


	protected List<StockAmountEntry> getStockAmountImpl(String userId,
			String itemCnr, Boolean returnItemInfo) {
		List<StockAmountEntry> stockEntries = new ArrayList<StockAmountEntry>() ;
 		if(StringHelper.isEmpty(itemCnr)) {
			respondBadRequestValueMissing("itemCnr") ;
			return stockEntries ;
		}
		if(connectClient(userId) == null) return stockEntries ;
		if(returnItemInfo == null) returnItemInfo = false ;
		
		try {
			ArtikelDto itemDto = artikelCall.artikelFindByCNrOhneExc(itemCnr) ;
			if(itemDto == null) {
				respondNotFound("itemCnr", itemCnr);
				return stockEntries ;
			}

			StockEntryMapper stockMapper = new StockEntryMapper() ;
			ItemEntryMapper itemMapper = new ItemEntryMapper() ;
			List<AllLagerEntry> stocks = lagerCall.getAllLager() ;
			for (AllLagerEntry allLagerEntry : stocks) {
				if(lagerCall.hatRolleBerechtigungAufLager(allLagerEntry.getStockId())) {
					LagerDto lagerDto = lagerCall.lagerFindByPrimaryKeyOhneExc(allLagerEntry.getStockId()) ;
					BigDecimal amount = lagerCall.getLagerstandOhneExc(itemDto.getIId(), lagerDto.getIId()) ;
					if(amount.signum() == 1) {
						StockAmountEntry stockAmountEntry = new StockAmountEntry(
								returnItemInfo ? itemMapper.mapEntry(itemDto) : null,
								stockMapper.mapEntry(lagerDto), amount) ;
						stockEntries.add(stockAmountEntry) ;
					}
				}
			}
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
		return stockEntries ;
	}


	@Override
	@GET
	@Path("/groups")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public ItemGroupEntryList getItemGroups(
			@QueryParam(Param.USERID) String userId) {
		ItemGroupEntryList itemgroups = new ItemGroupEntryList() ;
		if(connectClient(userId) == null) return itemgroups ;
		try {
			List<ArtgruDto> dtos = artikelCall.artikelgruppeFindByMandantCNr() ; 			
			itemgroups.setEntries(itemgroupEntryMapper.mapEntry(dtos)) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
		
		return itemgroups ;
	}


	@Override
	@GET
	@Path("/properties")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public ItemPropertyEntryList getItemProperties(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.ITEMCNR) String itemCnr) {
		ItemPropertyEntryList properties = new ItemPropertyEntryList() ;

		if(connectClient(userId) == null) return properties ;
		try {
			ArtikelDto itemDto = artikelCall.artikelFindByCNrOhneExc(itemCnr) ;
			if(itemDto == null) {
				respondNotFound(Param.ITEMCNR, itemCnr);
				return properties ;
			}

			properties.setEntries(getItemPropertiesFromIdImpl(itemDto.getIId()));
			return properties ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}

		return properties ;
	}
	
	@Override
	@GET
	@Path("/{" + Param.ITEMID + "}/properties")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public ItemPropertyEntryList getItemPropertiesFromId(
			@QueryParam(Param.USERID) String userId,
			@PathParam(Param.ITEMID) Integer itemId) {
		ItemPropertyEntryList properties = new ItemPropertyEntryList() ;

		if(connectClient(userId) == null) return properties ;
		try {
			properties.setEntries(getItemPropertiesFromIdImpl(itemId));
			return properties ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}

		return properties ;
	}

	private List<ItemPropertyEntry> getItemPropertiesFromIdImpl(Integer itemId) throws NamingException, RemoteException {
//		PaneldatenDto[] dtos = panelCall.paneldatenFindByPanelCNrCKey(
//				PanelFac.PANEL_ARTIKELEIGENSCHAFTEN, itemId.toString()) ;
//		List<ItemPropertyEntry> properties = itempropertyEntryMapper.mapEntry(dtos) ;
				
		List<PaneldatenPair> entries = panelCall.paneldatenFindByPanelCNrCKeyBeschreibung(PanelFac.PANEL_ARTIKELEIGENSCHAFTEN, itemId.toString()) ;
		List<ItemPropertyEntry> properties = itempropertyEntryMapper.mapEntry(entries) ;
		
		return properties ;	
	}
	
	private ItemEntry findItemEntryBySerialnumberCnr(String serialnumber, String cnr, Set<IItemLoaderAttribute> attributes) throws RemoteException, NamingException {
		Integer itemId = lagerCall.artikelIdFindBySeriennummerOhneExc(serialnumber) ;
		if(itemId == null) return null ;
		
		ArtikelDto artikelDto = artikelCall.artikelFindByPrimaryKeySmallOhneExc(itemId) ;
		if(artikelDto == null) return null ;
		
		if(!StringHelper.isEmpty(cnr)) {
			if(!artikelDto.getCNr().equals(cnr)) return null ;
		}
		
		return artikelLoaderCall.artikelFindByCNrOhneExc(artikelDto.getCNr(), attributes) ;
//		ItemEntryMapper mapper = new ItemEntryMapper() ;
//		return mapper.mapEntry(artikelDto) ;
	}
	
	private ItemEntry findItemEntryByCnrImpl(String cnr, 
			Set<IItemLoaderAttribute> attributes) throws RemoteException, NamingException {
		ItemEntry itemEntry = artikelLoaderCall.artikelFindByCNrOhneExc(cnr, attributes) ;
		return itemEntry ;
	}

	
	private FilterKriterium buildFilterCnr(String filterCnr) throws RemoteException, NamingException {
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

	private FilterKriterium buildFilterTextSearch(String filterCnr) throws RemoteException, NamingException {
		if(StringHelper.isEmpty(filterCnr)) return null ;
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				ArtikelFac.FLR_ARTIKELLISTE_C_VOLLTEXT,StringHelper.removeSqlDelimiters(filterCnr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_BOTH,
				true, 
				true, Facade.MAX_UNBESCHRAENKT); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	private FilterKriterium buildFilterDeliveryCnr(String deliveryCnr) throws RemoteException, NamingException {
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
	
	private FilterKriterium buildFilterItemGroupClass(String itemGroupClass) throws RemoteException, NamingException {
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

	private FilterKriterium buildFilterItemReferenceNr(String itemReferenceNr) throws RemoteException, NamingException {
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
	
	private FilterKriterium buildFilterWithHidden(Boolean withHidden) {
		return FilterHelper.createWithHidden(withHidden, ArtikelFac.FLR_ARTIKELLISTE_B_VERSTECKT) ;
	}	
}
