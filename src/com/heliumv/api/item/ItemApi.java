package com.heliumv.api.item;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.ILagerCall;
import com.heliumv.factory.legacy.AllLagerEntry;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.LagerDto;
import com.lp.util.EJBExceptionLP;

@Service("hvItem")
@Path("/api/v1/item")
public class ItemApi extends BaseApi implements IItemApi {
	@Autowired
	private IArtikelCall artikelCall ;

	@Autowired
	private ILagerCall lagerCall ;
	
	@Override
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})
	public ItemEntry findItemByCnr(
			@QueryParam("userid") String userId,
			@QueryParam("itemCnr") String cnr) {

		if(StringHelper.isEmpty(cnr)) {
			respondBadRequest("itemCnr", cnr) ;
			return null ;
		}

		if(connectClient(userId) == null) return null ;

		try {
			ItemEntry itemEntry = findItemEntryByCnrImpl(cnr) ;
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
			@QueryParam("userid") String userId, 
			@QueryParam("itemCnr") String itemCnr,
			@QueryParam("returnItemInfo") Boolean returnItemInfo) {
		List<StockAmountEntry> stockEntries = new ArrayList<StockAmountEntry>() ;
 		if(StringHelper.isEmpty(itemCnr)) {
			respondBadRequest("itemCnr", "null/empty") ;
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


	private ItemEntry findItemEntryByCnrImpl(String cnr) throws RemoteException, NamingException {
		ArtikelDto artikelDto = artikelCall.artikelFindByCNrOhneExc(cnr) ;
		if(artikelDto == null) return null ;

		ItemEntryMapper mapper = new ItemEntryMapper() ;
		return mapper.mapEntry(artikelDto) ;
	}
}
