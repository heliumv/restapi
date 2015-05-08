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
package com.heliumv.api.production;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MaterialWithdrawalEntry {
	private String lotCnr ;
	private String itemCnr ;
	private BigDecimal amount ;
	private String stockCnr ;
	private Integer stockId ;	
	private List<IdentityAmountEntry> identities ;
	private boolean isReturn ;
	
	/**
	 * Losnummer
	 * @return die Losnummer
	 */
	public String getLotCnr() {
		return lotCnr;
	}
	public void setLotCnr(String lotCnr) {
		this.lotCnr = lotCnr;
	}
	
	/**
	 * Artikelnummer
	 * @return die Artikelnummer
	 */
	public String getItemCnr() {
		return itemCnr;
	}
	public void setItemCnr(String itemCnr) {
		this.itemCnr = itemCnr;
	}
	
	/**
	 * Die Menge</br>
	 * <p>Bei Identit&auml;tsbehafteten Artikeln (Seriennr/Chargenr) ist hier
	 * die kummulierte Menge anzugeben</p>
	 * @return die Menge
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
	 * Die Serien/Chargennummer-Infos
	 * @return die Seriennummer bzw. Chargennummern/St&uuml;ckzahlen
	 */
	public List<IdentityAmountEntry> getIdentities() {
		return identities;
	}
	public void setIdentities(List<IdentityAmountEntry> identities) {
		this.identities = identities;
	}
	
	/**
	 * Lagernummer</br>
	 * <p>Entweder die Lagernummer oder die Lager-Id muss angegeben werden</p>
	 * @return die (optionale) Lagernummer
	 */
	public String getStockCnr() {
		return stockCnr;
	}
	public void setStockCnr(String stockCnr) {
		this.stockCnr = stockCnr;
	}
	
	/**
	 * Lager-Id
	 * @return die (optionale) Id des Lagers
	 */
	public Integer getStockId() {
		return stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}
	
	/**
	 * Es handelt sich um eine tats&auml;chliche R&uuml;ckgabe</br>
	 * <p>Bei einer tats&auml;chlichen R&uuml;ckgabe wird die Sollmenge des zuvor
	 * entnommenen Materials um die zur&uuml;ckgegebene Menge reduziert. Ansonsten
	 * wird die zur&uuml;ckgegebene Menge als Fehlmenge betrachtet.</p>
	 * @return true wenn es sich um eine tats&auml;chliche R&uuml;ckgabe handelt
	 * und somit die Sollmenge reduziert werden soll, ansonsten false.
	 */
	public boolean getReturn() {
		return isReturn;
	}
	
	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}	
}
