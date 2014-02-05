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
package com.heliumv.factory.loader;

import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.api.item.StockAmountInfoEntry;
import com.heliumv.factory.IFehlmengeCall;
import com.heliumv.factory.ILagerCall;
import com.heliumv.factory.IReservierungCall;
import com.lp.server.artikel.service.ArtikelDto;

public class ItemLoaderStockinfoSummary implements IItemLoaderAttribute {
	@Autowired
	private ILagerCall lagerCall ;
	@Autowired
	private IReservierungCall reservierungCall ;
	@Autowired
	private IFehlmengeCall fehlmengeCall ;
	
	@Override
	public ItemEntry load(ItemEntry entry, ArtikelDto artikelDto) {
		try {
			StockAmountInfoEntry infoEntry = new StockAmountInfoEntry() ;
			infoEntry.setStockAmount(lagerCall.getLagerstandAllerLagerEinesMandanten(
						artikelDto.getIId(), false));

//			BigDecimal paternoster = lagerCall.getPaternosterLagerstand(artikelDto.getIId());
			
			infoEntry.setReservedAmount(reservierungCall
					.getAnzahlReservierungen(artikelDto.getIId()));

			infoEntry.setMissingAmount(fehlmengeCall
					.getAnzahlFehlmengeEinesArtikels(artikelDto.getIId()));

			infoEntry.setAvailableAmount(
					infoEntry.getStockAmount().subtract(infoEntry.getReservedAmount())
					.subtract(infoEntry.getMissingAmount())) ;
			entry.setStockAmount(infoEntry.getAvailableAmount());
			entry.setStockAmountInfo(infoEntry) ;
		} catch(RemoteException e) {			
		} catch(NamingException e) {			
		}
		
		return entry ;
	}

	/*

				BigDecimal infertigung = DelegateFactory
						.getInstance()
						.getFertigungDelegate()
						.getAnzahlInFertigung(
								internalFrameArtikel.getArtikelDto().getIId());
				wkvfInfertigung.setValue(Helper.formatZahl(infertigung, 2,
						LPMain.getTheClient().getLocUi()));

				BigDecimal bestellt = DelegateFactory
						.getInstance()
						.getArtikelbestelltDelegate()
						.getAnzahlBestellt(
								internalFrameArtikel.getArtikelDto().getIId());
				wkvfBestellt.setValue(Helper.formatZahl(bestellt, 2, LPMain
						.getTheClient().getLocUi()));

				BigDecimal rahmenres = DelegateFactory
						.getInstance()
						.getReservierungDelegate()
						.getAnzahlRahmenreservierungen(
								internalFrameArtikel.getArtikelDto().getIId());
				wkvfRahmenreserviert.setValue(Helper.formatZahl(rahmenres, 2,
						LPMain.getTheClient().getLocUi()));

				BigDecimal rahmenbestellt = null;
				Hashtable<?, ?> htAnzahlRahmenbestellt = DelegateFactory
						.getInstance()
						.getArtikelbestelltDelegate()
						.getAnzahlRahmenbestellt(
								internalFrameArtikel.getArtikelDto().getIId());
				if (htAnzahlRahmenbestellt
						.containsKey(ArtikelbestelltFac.KEY_RAHMENBESTELLT_ANZAHL)) {
					rahmenbestellt = (BigDecimal) htAnzahlRahmenbestellt
							.get(ArtikelbestelltFac.KEY_RAHMENBESTELLT_ANZAHL);
					wkvfRahmenbestellt.setValue(Helper
							.formatZahl(rahmenbestellt, 2, LPMain
									.getTheClient().getLocUi()));
				}

				BigDecimal rahmenbedarf = DelegateFactory
						.getInstance()
						.getRahmenbedarfeDelegate()
						.getSummeAllerRahmenbedarfeEinesArtikels(
								internalFrameArtikel.getArtikelDto().getIId());
				wkvfRahmenbedarf.setValue(Helper.formatZahl(rahmenbedarf, 2,
						LPMain.getTheClient().getLocUi()));
	 * 
	 */
}
