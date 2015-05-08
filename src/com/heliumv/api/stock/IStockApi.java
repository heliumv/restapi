package com.heliumv.api.stock;


public interface IStockApi {
	/**
	 * Eine Liste aller Lager
	 * 
	 * @param userId des bei HELIUM V angemeldeten API Benutzer
	 * @return eine (leere) Liste aller Lager die dem angemeldeten Benutzer zug&auml;nglich sind
	 */
	StockEntryList getStockList(String userId) ;

}
