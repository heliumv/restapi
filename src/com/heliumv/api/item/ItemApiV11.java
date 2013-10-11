package com.heliumv.api.item;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Service;

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
}
