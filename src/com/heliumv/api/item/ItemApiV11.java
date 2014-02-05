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
