package com.heliumv.api.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.api.item.InventoryEntry;
import com.heliumv.api.item.InventoryEntryMapper;
import com.heliumv.factory.IInventurCall;
import com.heliumv.factory.ILagerCall;
import com.lp.server.artikel.service.InventurDto;

@Service("hvInventory")
@Path("/api/v1/inventory")
public class InventoryApi extends BaseApi implements IInventoryApi {
	@Autowired
	private IInventurCall inventurCall ;
	
	@Autowired
	private ILagerCall lagerCall ;

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
		InventoryEntryMapper inventoryMapper = new InventoryEntryMapper() ;
		
		for (InventurDto inventurDto : inventurs) {
			if(inventurDto.getLagerIId() == null) continue ;
			if(lagerCall.hatRolleBerechtigungAufLager(inventurDto.getLagerIId())) {
				openEntries.add(inventoryMapper.mapEntry(inventurDto)) ;
			}
		}
		
		return openEntries ;
	}
}
