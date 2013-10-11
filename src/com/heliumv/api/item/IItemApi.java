package com.heliumv.api.item;

import java.util.List;


public interface IItemApi {
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
	ItemEntry findItemByAttributes(String userId, String cnr, String serialnumber, Boolean addComments, Boolean addStockAmountInfos) ;


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
	 * @return eine (leere) Liste von <code>ItemEntry</code>
	 */
	List<ItemEntry> getItems(String userId, Integer limit, Integer startIndex, 
			String filterCnr, String filterTextSearch, String filterDeliveryCnr, String filterItemGroupClass,
			String filterItemReferenceNr,
			Boolean filterWithHidden) ;
	
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
	List<StockAmountEntry> getStockAmount(String userId, String itemCnr, Boolean returnItemInfo) ;	
	
	
	/**
	 * Eine Liste aller Artikelgruppen ermitteln.</br>
	 *
	 * @param userId der angemeldete HELIUM V Benutzer
	 * @return eine (leere) Liste von Artikelgruppen
	 */
	ItemGroupEntryList getItemGroups(String userId) ;

	/**
	 * Eine Liste aller Artikeleigenschaften eines Artikels ermitteln</br>
	 * 
	 * @param userId userId der angemeldete HELIUM V Benutzer
	 * @param itemCnr die gew&uuml;nschte Artikelnummer
	 * @return eine (leere) Liste von Artikeleigenschaften
	 */
	ItemPropertyEntryList getItemProperties(String userId, String itemCnr) ;


	/**
	 * Eine Liste aller Artikeleigenschaften eines Artikels ermitteln.
	 * @param userId userId der angemeldete HELIUM V Benutzer
	 * @param itemId die Id des gew&uuml;nschten Artikels
	 * @return eine (leere) Liste von Artikeleigenschaften
	 */
	ItemPropertyEntryList getItemPropertiesFromId(String userId, Integer itemId) ;	
}
