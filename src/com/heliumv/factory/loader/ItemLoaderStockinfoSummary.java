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
