package com.heliumv.api.inventory;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.api.item.InventoryEntry;
import com.heliumv.api.item.InventoryStockEntryMapper;
import com.heliumv.factory.GlobalInfo;
import com.heliumv.factory.IInventurCall;
import com.heliumv.factory.ILagerCall;
import com.lp.server.artikel.service.InventurDto;
import com.lp.server.artikel.service.InventurlisteDto;
import com.lp.server.artikel.service.LagerDto;
import com.lp.util.EJBExceptionLP;

@Service("hvInventory")
@Path("/api/v1/inventory")
public class InventoryApi extends BaseApi implements IInventoryApi {
	@Autowired
	private IInventurCall inventurCall ;
	
	@Autowired
	private ILagerCall lagerCall ;

	@Autowired
	private GlobalInfo globalInfo ;
	
	@Override
	@GET
	@Path("/{userid}")
	@Produces({"application/json", "application/xml"})
	public List<InventoryEntry> getOpenInventories(
			@PathParam("userid") String userId) {
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
	@Path("{userid}/{inventoryid}/entry/{itemid}/{amount}")
	public void updateInventoryEntry(
			@PathParam("userid") String userId,
			@PathParam("inventoryid") Integer inventoryId,
			@PathParam("itemid") Integer itemId,
			@PathParam("amount") BigDecimal amount) {		
		if(connectClient(userId) == null) return ;
	}
	
	@POST
	@Path("{userid}/{inventoryid}/entry/{itemid}/{amount}")
	public void createInventoryEntry(
			@PathParam("userid") String userId,
			@PathParam("inventoryid") Integer inventoryId,
			@PathParam("itemid") Integer itemId,
			@PathParam("amount") BigDecimal amount) {
		if(connectClient(userId) == null) return ;
		
		try {
			InventurDto inventurDto = inventurCall.inventurFindByPrimaryKey(inventoryId) ;
			if(inventurDto == null) {
				respondNotFound("inventoryId", inventoryId.toString());
				return ;
			}
			
			BigDecimal stockAmount = lagerCall.getLagerstandOhneExc(itemId, inventurDto.getLagerIId()) ;
			System.out.println("stockAmount:" + stockAmount.toString() + ", givenAmount:" + amount.toString()) ;
			
			InventurlisteDto entry = new InventurlisteDto() ;
			entry.setArtikelIId(itemId) ;
			entry.setInventurIId(inventoryId) ;
			entry.setLagerIId(inventurDto.getLagerIId()) ;
			entry.setNInventurmenge(amount) ;
			
			inventurCall.createInventurliste(entry, true, globalInfo.getTheClientDto()) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
	}
	
	/**
	 * Offene Inventuren eingeschränkt auf Inventuren die einem bestimmten Lager zugeordnet sind
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
