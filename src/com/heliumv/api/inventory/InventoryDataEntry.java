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
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.production.IdentityAmountEntry;

@XmlRootElement
public class InventoryDataEntry {
	private String itemCnr ;
	private BigDecimal amount ;
	private List<IdentityAmountEntry> identities ;

	/**
	 * Die Artikelnummer
	 * @return die Artikelnummer
	 */
	public String getItemCnr() {
		return itemCnr;
	}
	public void setItemCnr(String itemCnr) {
		this.itemCnr = itemCnr;
	}
	
	/**
	 * Die Menge des Artikels</br>
	 * <p>Die Menge ist optional, wenn es sich um einen Seriennr/Chargennr-
	 * behafteten Artikel handelt. In diesem Falle sind die Daten &uuml;ber
	 * die <code>Identities</code> zu &uuml;bermitteln.</p>
	 * @return die Menge des Artikels
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * Die Liste der Seriennr/Chargennr Informationen
	 * @return die (leere) Liste
	 */
	public List<IdentityAmountEntry> getIdentities() {
		return identities;
	}
	public void setIdentities(List<IdentityAmountEntry> identities) {
		this.identities = identities;
	}
}
