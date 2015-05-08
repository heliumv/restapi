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

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import com.lp.util.EJBExceptionLP;


public interface IItemApiV11 extends IItemApi {
	/**
	 * Liefert eine Liste aller Lagerst&auml;nde dieses Artikels</br>
	 * <p>Es werden nur Lager geliefert, die einen Lagerstand > 0 haben. Es werden nur jene
	 * Lagerst&auml;nde geliefert, f&uuml;r die der Benutzer das Recht hat das jeweilige Lager zu benutzen.</p>
	 * 
	 * @param userId der angemeldete API Benutzer
	 * @param itemCnr die gesuchte Artikelnummer
	 * @param returnItemInfo mit <code>true</code> werden neben den Lagerst&auml;nden auch die Daten des
	 * betreffenden Artikels zur&uuml;ckgeliefert.
	 * @return eine (leere) Liste von Lagerst&auml;nden
	 */
	StockAmountEntryList getStockAmountList(String userId, String itemCnr, Boolean returnItemInfo) ;
	
	/**
	 * Einen Artikel anhand seiner Artikelnummer ermitteln</br>
	 * 
	 * @param userId des bei HELIUM V angemeldeten API Benutzer
	 * @param cnr (optional) die gesuchte Artikelnummer
	 * @param serialnumber (optional) die Seriennummer des Artikels</br>
	 * <p>Eineindeutige Artikel k&ouml;nnen &uuml;ber ihre Seriennummer ermittelt werden. Dabei wird
	 * zuerst im aktuellen Lagerstand gesucht, danach in den Abgangsbuchungen. Ist die <code>cnr</code>
	 * ebenfalls angegeben, muss der Artikel der &uuml;ber die Seriennummer ermittelt wurde mit der 
	 * angegebenen Artikelnummer &uuml;bereinstimmen.</p>
	 * @param addComments (optional) mit <code>true</code> die Artikelkommentar der Art text/html ebenfalls liefern
	 * @param addStockAmountInfos (optional) mit <code>true</code> die allgemeinen Lagerstandsinformationen liefern
	 * @return den Artikel sofern vorhanden. Gibt es den Artikel/Seriennummer nicht wird mit 
	 * StatusCode <code>NOT_FOUND (404)</code> geantwortet
	 */
	ItemV1Entry findItemV1ByAttributes(
			String userId,
			String cnr, 
			String serialnumber,
			Boolean addComments, 
			Boolean addStockAmountInfos) ;
	
	BigDecimal getPrice(
			String userId,
			Integer itemId,
			Integer customerId,
			BigDecimal amount,
			String unitCnr) throws NamingException, RemoteException, EJBExceptionLP ;
	
	/**
	 * Eine Liste aller Artikel ermitteln.</br>
	 * <p>Das Ergebnis kann dabei durch Filter eingeschr&auml;nkt werden</p>
	 * 
	 * @param userId des angemeldeten HELIUM V Benutzer
	 * @param limit die maximale Anzahl von zur&uuml;ckgelieferten Datens&auml;tzen
	 * @param startIndex die Index-Nummer desjenigen Satzes mit dem begonnen werden soll
	 * @param filterCnr die (optionale) Artikelnummer nach der die Suche eingeschr&auml;nkt werden soll
	 * @param filterTextSearch der (optionale) Text der die Suche einschr&auml;nkt 
	 * @param filterDeliveryCnr auf die (optionale) Lieferantennr. bzw Bezeichnung einschr&auml;nken
	 * @param filterItemGroupClass auf die (optionale) Artikelgruppe bzw. Artikelklasse einschr&auml;nken
	 * @param filterItemReferenceNr auf die (optionale) Artikelreferenznummer einschr&auml;nken
	 * @param filterWithHidden mit <code>true</code> werden auch versteckte Artikel in die Suche einbezogen
	 * @param filterItemgroupId die (optionale) IId der Artikelgruppe in der die Artikel gesucht werden. Die
	 *  cnr wird aus der angegebenen iid ermittelt und dann auch f&uuml;r die Artikelklasse verwendet
	 * @param filterCustomerItemCnr die (optionale) Kundenartikelnummer nach der gesucht werden kann 
	 * @param filterShopGroupIds die (optionale) auch leere Liste von Shopgruppen IIds in denen die Artikel
	 *   gesucht werden sollen
	 * @param orderBy definiert die Sortierung der Daten. Es k&ouml;nnen durch Komma getrennt mehrere
	 *  Datenfelder angegeben werden. Getrennt durch Leerzeichen kann "asc" (aufsteigend) oder auch "desc" (absteigend)
	 *  sortiert werden. "cnr desc" oder auch "cnr asc, customerItemCnr desc", oder auch "cnr, customerItemCnr desc"
	 * @return eine (leere) Liste von <code>ItemEntry</code>
	 */
	ItemEntryList getItemsList(String userId, Integer limit, Integer startIndex, 
			String filterCnr, String filterTextSearch, String filterDeliveryCnr, String filterItemGroupClass,
			String filterItemReferenceNr,
			Boolean filterWithHidden, Integer filterItemgroupId, String filterCustomerItemCnr,
			String filterShopGroupIds, String orderBy) throws RemoteException, NamingException, EJBExceptionLP, Exception ;
	
	/**
	 * Eine Liste aller Artikelgruppen ermitteln</br>
	 * <p>Diese Methode ist f&uuml;r die Verwendung in Listboxen/Comboboxen gedacht</p>
	 * 
	 * @param userId des angemeldeten HELIUM V Benutzer
	 * @return eine (leere) Liste aller Artikelgruppen
	 */
	ItemGroupEntryList getItemGroupsList(
			String userId) throws RemoteException, NamingException, EJBExceptionLP, Exception ;
	
	/**
	 * Eine Liste aller Shopgruppen ermitteln</br>
	 * 
	 * @param userId des angemeldeten HELIUM V Benutzer
	 * @param limit (optional) die maximale Anzahl von zu liefernden Shopgruppen. Bei 0 werden alle geliefert
	 * @param startIndex (optional) die Index-Nummer desjenigen Satzes mit dem begonnen werden soll
	 * @return eine (leere) Liste von <code>ShopGroupEntry</code>
	 * 
	 * @throws RemoteException
	 * @throws NamingException
	 * @throws EJBExceptionLP
	 * @throws Exception
	 */
	ShopGroupEntryList getShopGroupsList(
			String userId,
			Integer limit,
			Integer startIndex) throws RemoteException, NamingException, EJBExceptionLP, Exception ;
}
