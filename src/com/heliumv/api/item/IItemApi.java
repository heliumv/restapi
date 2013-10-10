package com.heliumv.api.item;

import java.util.List;


public interface IItemApi {
	/**
	 * Einen Artikel anhand seiner Artikelnummer ermitteln</br>
	 * 
	 * @param userId des bei HELIUM V angemeldeten API Benutzer
	 * @param cnr (optional) die gesuchte Artikelnummer
	 * @param serialnumber (optional) die Seriennumber des Artikels</br>
	 * <p>Eineindeutige Artikel k&ouml;nnen &uuml;ber ihre Seriennummer ermittelt werden. Dabei wird
	 * zuerst im aktuellen Lagerstand gesucht, danach in den Abgangsbuchungen. Ist die <code>cnr</code>
	 * ebenfalls angegeben, muss der Artikel der &uuml;ber die Seriennummer ermittelt wurde mit der 
	 * angegebenen Artikelnummer &uuml;bereinstimmen.</p>
	 * @param addComments (optional) mit <code>true</code> die Artikelkommentar ebenfalls liefern
	 * @return
	 */
	ItemEntry findItemByAttributes(String userId, String cnr, String serialnumber, Boolean addComments) ;


	/**
	 * Eine Liste aller Artikel ermitteln.</br>
	 * <p>Das Ergebnis kann dabei durch Filter eingeschränkt werden</p>
	 * 
	 * @param userId der angemeldete HELIUM V Benutzer
	 * @param limit die maximale Anzahl von zurückgelieferten Datensätzen
	 * @param startIndex die Id desjenigen Satzes mit dem begonnen werden soll
	 * @param filterCnr die (optionale) Artikelnummer nach der die Suche eingeschränkt werden soll
	 * @param filterTextSearch der (optionale) Text der die Suche einschränkt 
	 * @param filterDeliveryCnr die (optionale) Lieferantennr. bzw Bezeichnung
	 * @param filterItemGroupClass die (optionale) Artikelgruppe bzw. Artikelklasse
	 * @param filterItemReferenceNr die (optionale) Artikelreferenznummer
	 * @param filterWithHidden mit <code>true</code> werden auch versteckte Artikel in die Suche einbezogen
	 * @return
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
	 * @return
	 */
	List<StockAmountEntry> getStockAmount(String userId, String itemCnr, Boolean returnItemInfo) ;	
	
	
	/**
	 * Eine Liste aller Artikelgruppen ermitteln.</br>
	 *
	 * @param userId der angemeldete HELIUM V Benutzer
	 * @return eine (leere) Liste von Artikelgruppen
	 */
	List<ItemGroupEntry> getItemGroups(String userId) ;

	/**
	 * Eine Liste aller Artikeleigenschaften eines Artikels ermitteln</br>
	 * 
	 * @param userId userId der angemeldete HELIUM V Benutzer
	 * @param itemCnr die gew&uuml;nschte Artikelnummer
	 * @return eine (leere) Liste von Artikeleigenschaften
	 */
	List<ItemPropertyEntry> getItemProperties(String userId, String itemCnr) ;


	/**
	 * Eine Liste aller Artikeleigenschaften eines Artikels ermitteln.
	 * @param userId userId der angemeldete HELIUM V Benutzer
	 * @param itemId die Id des gew&uuml;nschten Artikels
	 * @return eine (leere) Liste von Artikeleigenschaften
	 */
	List<ItemPropertyEntry> getItemPropertiesFromId(String userId, Integer itemId) ;	
}
