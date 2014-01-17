package com.heliumv.api.inventory;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.api.item.InventoryEntry;
import com.heliumv.api.item.InventoryStockEntryMapper;
import com.heliumv.factory.GlobalInfo;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IInventurCall;
import com.heliumv.factory.ILagerCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.InventurDto;
import com.lp.server.artikel.service.InventurlisteDto;
import com.lp.server.artikel.service.LagerDto;
import com.lp.util.EJBExceptionLP;

@Service("hvInventory")
@Path("/api/v1/inventory")
public class InventoryApi extends BaseApi implements IInventoryApi {
	@Autowired
	private IArtikelCall artikelCall ;
	
	@Autowired
	private IInventurCall inventurCall ;
	
	@Autowired
	private ILagerCall lagerCall ;

	@Autowired
	private GlobalInfo globalInfo ;
	
	@Override
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<InventoryEntry> getOpenInventories(
			@QueryParam("userid") String userId) {
		List<InventoryEntry> entries = new ArrayList<InventoryEntry>() ;
		
		if(connectClient(userId) == null) return entries ;
		
		try {
			entries = findOpenInventories() ;
 		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
		return entries ;
	}

	@PUT
	@Path("/{inventoryid}/entry")
	@Consumes({"application/json", "application/xml"})
	public void updateInventoryDataEntry(
		@PathParam("inventoryid") Integer inventoryId,
		@QueryParam("userid") String userId,
		InventoryDataEntry inventoryEntry,
		@QueryParam("changeAmountTo") Boolean changeAmountTo		
	) {
		if(StringHelper.isEmpty(inventoryEntry.getItemCnr())) {
			respondBadRequest("itemCnr", inventoryEntry.getItemCnr()) ;
			return ;
		}
		if(inventoryEntry.getAmount() == null || inventoryEntry.getAmount().signum() <= 0) {
			respondBadRequest("amount", "null/<0") ;
			return ;
		}

		if(connectClient(userId) == null) return ;

		try {
			InventurDto inventurDto = findInventurDtoById(inventoryId) ;
			if(inventurDto == null) return ;
			
			ArtikelDto itemDto = artikelCall.artikelFindByCNrOhneExc(inventoryEntry.getItemCnr()) ;
			if(itemDto == null) {
				respondNotFound("itemCnr", inventoryEntry.getItemCnr()) ;
				return ;
			}
			Integer itemId = itemDto.getIId() ;
			
			updateInventoryEntryImpl(inventoryId, itemId, inventoryEntry.getAmount(), changeAmountTo, inventurDto);
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
	}
	
	
	@PUT
	@Path("/{inventoryid}/entry/{itemid}/{amount}")
	public void updateInventoryEntry(
			@PathParam("inventoryid") Integer inventoryId,
			@PathParam("itemid") Integer itemId,
			@PathParam("amount") BigDecimal amount,
			@QueryParam("userid") String userId,
			@QueryParam("changeAmountTo") Boolean changeAmountTo) {		
		if(connectClient(userId) == null) return ;
		
		try {
			InventurDto inventurDto = findInventurDtoById(inventoryId) ;
			if(inventurDto == null) return ;
			
			updateInventoryEntryImpl(inventoryId, itemId, amount, changeAmountTo, inventurDto);
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
	}

	
	@POST
	@Path("/{inventoryid}/entry")
	@Consumes({"application/json", "application/xml"})
	public void createInventoryDataEntry(
		@PathParam("inventoryid") Integer inventoryId,
		InventoryDataEntry inventoryEntry,
		@QueryParam("userid") String userId,
		@QueryParam("largeDifference") Boolean largeDifference
	) {
		if(StringHelper.isEmpty(inventoryEntry.getItemCnr())) {
			respondBadRequest("itemCnr", inventoryEntry.getItemCnr()) ;
			return ;
		}
		if(inventoryEntry.getAmount() == null || inventoryEntry.getAmount().signum() <= 0) {
			respondBadRequest("amount", "null/<0") ;
			return ;
		}		

		if(connectClient(userId) == null) return ;
		
		try {
			InventurDto inventurDto = findInventurDtoById(inventoryId) ;
			if(inventurDto == null) return ;
			
			ArtikelDto itemDto = artikelCall.artikelFindByCNrOhneExc(inventoryEntry.getItemCnr()) ;
			if(itemDto == null) {
				respondNotFound("itemCnr", inventoryEntry.getItemCnr()) ;
				return ;
			}
			Integer itemId = itemDto.getIId() ;
			
			InventurlisteDto[] inventurlisteDtos = inventurCall
					.inventurlisteFindByInventurIIdLagerIIdArtikelIId(inventoryId, inventurDto.getLagerIId(), itemId) ;
			if(inventurlisteDtos != null && inventurlisteDtos.length > 0) {
				respondUnprocessableEntity("amount", inventurlisteDtos[0].getNInventurmenge().toPlainString());
				return ;
			}
			
			createInventurlisteImpl(inventoryId, inventurDto, itemId, inventoryEntry.getAmount(), largeDifference);
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;			
		}
	}
	
	@POST
	@Path("/{inventoryid}/entry/{itemid}/{amount}")
	public void createInventoryEntry(
			@PathParam("inventoryid") Integer inventoryId,
			@PathParam("itemid") Integer itemId,
			@PathParam("amount") BigDecimal amount,
			@QueryParam("userid") String userId,
			@QueryParam("largeDifference") Boolean largeDifference) {
		if(connectClient(userId) == null) return ;
		
		try {
			InventurDto inventurDto = inventurCall.inventurFindByPrimaryKey(inventoryId) ;
			if(inventurDto == null) {
				respondNotFound("inventoryId", inventoryId.toString());
				return ;
			}
			
			InventurlisteDto[] inventurlisteDtos = inventurCall
					.inventurlisteFindByInventurIIdLagerIIdArtikelIId(inventoryId, inventurDto.getLagerIId(), itemId) ;
			if(inventurlisteDtos != null && inventurlisteDtos.length > 0) {
				respondUnprocessableEntity("amount", inventurlisteDtos[0].getNInventurmenge().toPlainString());
				return ;
			}

			createInventurlisteImpl(inventoryId, inventurDto, itemId, amount, largeDifference);
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
	}

	private boolean isDifferenceToLarge(BigDecimal baseAmount, BigDecimal newAmount) {
		BigDecimal percentAllowed = new BigDecimal(10) ;
		BigDecimal amplitude = baseAmount.movePointLeft(2).multiply(percentAllowed) ;
		if(amplitude.compareTo(BigDecimal.ONE) <= 0) {
			amplitude = BigDecimal.ONE ;
		}
		return baseAmount.subtract(newAmount).abs().compareTo(amplitude) > 0 ;
	}
	
	
	private InventurDto findInventurDtoById(Integer inventoryId) throws NamingException, RemoteException {
		InventurDto inventurDto = inventurCall.inventurFindByPrimaryKey(inventoryId) ;
		if(inventurDto == null) {
			respondNotFound("inventoryId", inventoryId.toString());
			return null ;
		}
		if(inventurDto.getLagerIId() == null) {
			respondNotFound("inventoryId", inventoryId.toString());
			return null ;
		}
		
		if(!lagerCall.hatRolleBerechtigungAufLager(inventurDto.getLagerIId())) {
			respondNotFound("inventoryId", inventoryId.toString());
			return null ;
		}
		
		return inventurDto ;
	}
	
	private void createInventurlisteImpl(Integer inventoryId, InventurDto inventurDto, Integer itemId,
			BigDecimal amount, Boolean largeDifference) throws NamingException, RemoteException {	    
		BigDecimal lagerstandVeraenderung = lagerCall.
	          getLagerstandsVeraenderungOhneInventurbuchungen(
	              itemId, inventurDto.getLagerIId(), inventurDto.getTInventurdatum(),
	              new java.sql.Timestamp(System.currentTimeMillis()));

	    if (amount.subtract(lagerstandVeraenderung).signum() < 0) {
			respondBadRequest("amount", "negativeInventoryAmount");
			return ;
	    }
	    
		BigDecimal stockAmount = lagerCall.getLagerstandOhneExc(itemId, inventurDto.getLagerIId()) ;
		if(isDifferenceToLarge(stockAmount, amount)) {
			if(largeDifference == null || !largeDifference) {
				respondBadRequest("amount", "largeDifference");
				return ;
			}
		}
		
		InventurlisteDto entry = new InventurlisteDto() ;
		entry.setArtikelIId(itemId) ;
		entry.setInventurIId(inventoryId) ;
		entry.setLagerIId(inventurDto.getLagerIId()) ;
		entry.setNInventurmenge(amount) ;
		
		inventurCall.createInventurliste(entry, false, globalInfo.getTheClientDto()) ;
	}
	
	private void updateInventoryEntryImpl(Integer inventoryId, Integer itemId,
			BigDecimal amount, Boolean changeAmountTo, InventurDto inventurDto)
			throws NamingException, RemoteException {
		
		InventurlisteDto[] inventurlisteDtos = inventurCall
				.inventurlisteFindByInventurIIdLagerIIdArtikelIId(inventoryId, inventurDto.getLagerIId(), itemId) ;
		if(inventurlisteDtos == null || inventurlisteDtos.length == 0) {
			createInventurlisteImpl(inventoryId, inventurDto, itemId, amount, false) ;
			return ;
		}

		if(changeAmountTo == null) {
			respondBadRequest("changeAmountTo", "missing") ;
			return ;
		}

		BigDecimal newAmount = amount ;
		
		if(!changeAmountTo) {
			BigDecimal oldAmount = inventurlisteDtos[0].getNInventurmenge() ;
			newAmount = oldAmount.add(amount) ;
		}
		if(newAmount.signum() < 0) {
			respondBadRequest("amount", "<0") ;
			return ;
		}
		
		inventurlisteDtos[0].setNInventurmenge(newAmount) ;			
		inventurCall.updateInventurliste(inventurlisteDtos[0], false) ;
	}
	
	/**
	 * Offene Inventuren eingeschr&auml;nkt auf Inventuren die einem bestimmten Lager zugeordnet sind
	 * Ausserdem muss der abfragende Zugriff auf das Lager haben.
	 * 
	 * @return eine (leere) Liste aller Inventuren die noch offen sind
	 * @throws NamingException
	 */
	private List<InventoryEntry> findOpenInventories() throws NamingException {
		InventurDto[] inventurs = inventurCall.inventurFindOffene() ;
		List<InventoryEntry> openEntries = new ArrayList<InventoryEntry>();
		InventoryStockEntryMapper inventoryMapper = new InventoryStockEntryMapper() ;
		
		for (InventurDto inventurDto : inventurs) {
			if(inventurDto.getLagerIId() == null) continue ;
			if(lagerCall.hatRolleBerechtigungAufLager(inventurDto.getLagerIId())) {
				LagerDto lagerDto = lagerCall.lagerFindByPrimaryKeyOhneExc(inventurDto.getLagerIId()) ;
				openEntries.add(inventoryMapper.mapEntry(inventurDto, lagerDto)) ;
			}
		}
		
		return openEntries ;
	}
}
