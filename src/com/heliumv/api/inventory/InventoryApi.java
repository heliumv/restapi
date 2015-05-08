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
import com.heliumv.api.production.IdentityAmountEntry;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IGlobalInfo;
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
	private IGlobalInfo globalInfo ;
	
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
	@Consumes({FORMAT_JSON, FORMAT_XML})
	public void updateInventoryDataEntry(
		@PathParam("inventoryid") Integer inventoryId,
		@QueryParam(Param.USERID) String userId,
		InventoryDataEntry inventoryEntry,
		@QueryParam("changeAmountTo") Boolean changeAmountTo) {

		if(StringHelper.isEmpty(inventoryEntry.getItemCnr())) {
			respondBadRequestValueMissing("itemCnr") ;
			return ;
		}

		if(inventoryEntry.getAmount() == null || inventoryEntry.getAmount().signum() < 0) {
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
			
			updateInventoryEntryImpl(inventoryId, itemDto, inventoryEntry, changeAmountTo, inventurDto);
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
	}
	
	
	protected InventoryDataEntry getInventoryDataEntry(BigDecimal amount) {
		InventoryDataEntry inventoryEntry = new InventoryDataEntry() ;
		inventoryEntry.setIdentities(new ArrayList<IdentityAmountEntry>());
		inventoryEntry.setAmount(amount) ;
		return inventoryEntry ;
	}

	protected InventoryDataEntry getInventoryDataEntry(BigDecimal amount, String identity) {
		InventoryDataEntry inventoryEntry = getInventoryDataEntry(amount) ;
		if(!StringHelper.isEmpty(identity)) {
			IdentityAmountEntry entry = new IdentityAmountEntry() ;
			entry.setAmount(amount) ;
			entry.setIdentity(identity);
			inventoryEntry.getIdentities().add(entry) ;
		}
		return inventoryEntry ;
	}

	
	@PUT
	@Path("/{inventoryid}/entry/{itemid}/{amount}")
	public void updateInventoryEntry(
			@PathParam("inventoryid") Integer inventoryId,
			@PathParam(Param.ITEMID) Integer itemId,
			@PathParam("amount") BigDecimal amount,
			@QueryParam(Param.USERID) String userId,
			@QueryParam("changeAmountTo") Boolean changeAmountTo,
			@QueryParam("identity") String identity) {		
		if(connectClient(userId) == null) return ;
		
		try {
			InventurDto inventurDto = findInventurDtoById(inventoryId) ;
			if(inventurDto == null) return ;
			
			ArtikelDto itemDto = artikelCall.artikelFindByPrimaryKeySmallOhneExc(itemId) ;
			if(itemDto == null) {
				respondBadRequest(Param.ITEMID, itemId.toString());
				return ;
			}
			
			InventoryDataEntry inventoryEntry = getInventoryDataEntry(amount, 
					itemDto.istArtikelSnrOderchargentragend() ? identity : null) ;
			updateInventoryEntryImpl(inventoryId, itemDto, inventoryEntry, changeAmountTo, inventurDto);
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
	@Consumes({FORMAT_JSON, FORMAT_XML})
	public void createInventoryDataEntry(
		@PathParam("inventoryid") Integer inventoryId,
		InventoryDataEntry inventoryEntry,
		@QueryParam(Param.USERID) String userId,
		@QueryParam("largeDifference") Boolean largeDifference
	) {
		if(StringHelper.isEmpty(inventoryEntry.getItemCnr())) {
			respondBadRequestValueMissing("itemCnr") ;
			return ;
		}
		if(inventoryEntry.getAmount() == null || inventoryEntry.getAmount().signum() < 0) {
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
			
			InventurlisteDto[] inventurlisteDtos = inventurCall
					.inventurlisteFindByInventurIIdLagerIIdArtikelIId(inventoryId, inventurDto.getLagerIId(), itemDto.getIId()) ;
			if(itemDto.istArtikelSnrOderchargentragend()) {
				for (IdentityAmountEntry identityEntry : inventoryEntry.getIdentities()) {
					for(int i = 0 ; i < inventurlisteDtos.length; i++) {
						if(identityEntry.getIdentity().compareTo(inventurlisteDtos[i].getCSeriennrchargennr()) == 0) {
							respondUnprocessableEntity("identity exists", identityEntry.getIdentity()) ;
							return ;
						}
					}
				}
			} else {
				if(inventurlisteDtos != null && inventurlisteDtos.length > 0) {
					respondUnprocessableEntity("amount", inventurlisteDtos[0].getNInventurmenge().toPlainString());
					return ;
				}
			}
			
			createInventurlisteImpl(inventoryId, inventurDto, itemDto, inventoryEntry, largeDifference);
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
			@PathParam(Param.ITEMID) Integer itemId,
			@PathParam("amount") BigDecimal amount,
			@QueryParam(Param.USERID) String userId,
			@QueryParam("largeDifference") Boolean largeDifference,
			@QueryParam("identity") String identity) {
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

			ArtikelDto itemDto = artikelCall.artikelFindByPrimaryKeySmallOhneExc(itemId) ;
			if(itemDto == null) {
				respondBadRequest(Param.ITEMID, itemId.toString());
				return ;
			}
			
			InventoryDataEntry inventoryEntry = getInventoryDataEntry(
					amount, itemDto.istArtikelSnrOderchargentragend() ? identity : null)  ;
			createInventurlisteImpl(inventoryId, inventurDto, itemDto, inventoryEntry, largeDifference);
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
	
	/**
	 * Mengen &uuml;berpr&uuml;fen</b>
	 * <p>Die Gesamtsumme der identity.amount muss ident mit der angegebenen Menge sein<p>
	 * <p>Es d&uuml;rfen nur positive Mengen in den identities vorhanden sein.</p>
	 * <p>Seriennummernbehaftete Artikel d&uuml;rfen nur die Menge 1.0 haben</p>
	 * @param amount
	 * @param identities
	 * @return
	 */
	private boolean verifyAmounts(ArtikelDto itemDto, BigDecimal amount, List<IdentityAmountEntry> identities) {
		if(!itemDto.istArtikelSnrOderchargentragend()) return true ;
		if(identities == null || identities.size() == 0) {
			respondBadRequestValueMissing("identities");
			return false ;
		}
		
		BigDecimal amountIdentities = BigDecimal.ZERO ;
		for (IdentityAmountEntry entry : identities) {
			if(entry.getAmount() == null) {
				respondBadRequestValueMissing("amount");
				appendBadRequestData(entry.getIdentity(), "amount missing");
				return false ;
			}
			
			if(entry.getAmount().signum() != 1) {
				respondBadRequest("amount", "positive");
				appendBadRequestData(entry.getIdentity(), entry.getAmount().toPlainString());
				return false ;
			}

			if(itemDto.isSeriennrtragend()) {
				if(BigDecimal.ONE.compareTo(entry.getAmount()) != 0) {
					respondBadRequest("snr-amount", "1 (is: " + entry.getAmount().toPlainString() + ")");
					return false ;
				}
			}
			
			amountIdentities = amountIdentities.add(entry.getAmount()) ;
		}
		
		if(amountIdentities.compareTo(amount.abs()) != 0) {
			respondBadRequest("totalamount != identityamount", amount.toPlainString()) ;
			appendBadRequestData("identityamount", amountIdentities.toPlainString()) ;
			return false ;
		}
		
		return true ;
	}
	
	private void createInventurlisteImpl(Integer inventoryId, InventurDto inventurDto, ArtikelDto itemDto,
			InventoryDataEntry inventoryEntry, Boolean largeDifference) throws NamingException, RemoteException {
		if(!itemDto.istArtikelSnrOderchargentragend()) {
			BigDecimal lagerstandVeraenderung = lagerCall.
		          getLagerstandsVeraenderungOhneInventurbuchungen(
		              itemDto.getIId(), inventurDto.getLagerIId(), inventurDto.getTInventurdatum(),
		              new java.sql.Timestamp(System.currentTimeMillis()));
	
		    if (inventoryEntry.getAmount().subtract(lagerstandVeraenderung).signum() < 0) {
				respondBadRequest("amount", "negativeInventoryAmount");
				return ;
		    }
		}
		
		if(!itemDto.istArtikelSnrOderchargentragend()) {
			BigDecimal stockAmount = lagerCall.getLagerstandOhneExc(itemDto.getIId(), inventurDto.getLagerIId()) ;
			if(isDifferenceToLarge(stockAmount, inventoryEntry.getAmount())) {
				if(largeDifference == null || !largeDifference) {
					respondBadRequest("amount", "largeDifference");
					return ;
				}
			}
		}
		
		if(!verifyAmounts(itemDto, inventoryEntry.getAmount(), inventoryEntry.getIdentities())) {
			return ;
		}		

		if(itemDto.istArtikelSnrOderchargentragend()) {
			for (IdentityAmountEntry amountEntry : inventoryEntry.getIdentities()) {
				InventurlisteDto entry = new InventurlisteDto() ;
				entry.setArtikelIId(itemDto.getIId()) ;
				entry.setInventurIId(inventoryId) ;
				entry.setLagerIId(inventurDto.getLagerIId()) ;
				entry.setNInventurmenge(amountEntry.getAmount()) ;
				entry.setCSeriennrchargennr(amountEntry.getIdentity());
				
				inventurCall.createInventurliste(entry, false, globalInfo.getTheClientDto()) ;				
			}
		} else {
			InventurlisteDto entry = new InventurlisteDto() ;
			entry.setArtikelIId(itemDto.getIId()) ;
			entry.setInventurIId(inventoryId) ;
			entry.setLagerIId(inventurDto.getLagerIId()) ;
			entry.setNInventurmenge(inventoryEntry.getAmount()) ;
			
			inventurCall.createInventurliste(entry, false, globalInfo.getTheClientDto()) ;		
		}
	}
	
	private void updateInventoryEntryImpl(Integer inventoryId, ArtikelDto itemDto,
			InventoryDataEntry inventoryEntry, Boolean changeAmountTo, InventurDto inventurDto)
			throws NamingException, RemoteException {
		
		InventurlisteDto[] inventurlisteDtos = inventurCall
				.inventurlisteFindByInventurIIdLagerIIdArtikelIId(inventoryId, inventurDto.getLagerIId(), itemDto.getIId()) ;
		if(inventurlisteDtos == null || inventurlisteDtos.length == 0) {
			createInventurlisteImpl(inventoryId, inventurDto, itemDto, inventoryEntry, false) ;
			return ;
		}

		if(changeAmountTo == null) {
			respondBadRequest("changeAmountTo", "missing") ;
			return ;
		}
		
		if(!verifyAmounts(itemDto, inventoryEntry.getAmount(), inventoryEntry.getIdentities())) {
			return ;
		}		
		
		InventurlisteDto workListeDto = null ;
		if(itemDto.istArtikelSnrOderchargentragend()) {
			for (IdentityAmountEntry identityEntry : inventoryEntry.getIdentities()) {
				String snr = identityEntry.getIdentity() ;
				workListeDto = null ;
				for(int i = 0 ; workListeDto == null && i < inventurlisteDtos.length; i++) {
					if(snr.compareTo(inventurlisteDtos[i].getCSeriennrchargennr()) == 0) {
						workListeDto = inventurlisteDtos[i] ;
					}
				}

				if(workListeDto != null) {
					if(!updateInventurliste(itemDto, identityEntry.getAmount(), changeAmountTo,
							workListeDto)) {
						break ;
					}
				} else {
//					createInventurlisteImpl(inventoryId, inventurDto, itemDto, inventoryEntry, false);
				}
			}
		} else {
			workListeDto = inventurlisteDtos[0] ;
			updateInventurliste(itemDto, inventoryEntry.getAmount(), changeAmountTo,
					workListeDto);
		}		
	}

	private boolean updateInventurliste(ArtikelDto itemDto, BigDecimal newAmount,
			Boolean changeAmountTo, InventurlisteDto workListeDto) throws NamingException,
			RemoteException {		
		if(!changeAmountTo) {
			BigDecimal oldAmount = workListeDto.getNInventurmenge() ;
			newAmount = oldAmount.add(newAmount) ;
		}
		
		if(newAmount.signum() < 0) {
			respondBadRequest("amount", "<0") ;
			return false ;
		}

		if(newAmount.signum() == 0) {
			inventurCall.removeInventurListe(workListeDto) ;
		} else {
			if(itemDto.isSeriennrtragend() && newAmount.compareTo(BigDecimal.ONE) > 0) {
				respondBadRequest("serialnr", "amount has to be 1") ;
				return false ;
			}

			workListeDto.setNInventurmenge(newAmount) ;			
			inventurCall.updateInventurliste(workListeDto, false) ;
		}
		
		return true ;
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
