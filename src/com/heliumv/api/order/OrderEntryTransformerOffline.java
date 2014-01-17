package com.heliumv.api.order;

import java.util.ArrayList;
import java.util.List;

import com.lp.server.auftrag.service.IAuftragFLRData;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class OrderEntryTransformerOffline extends OrderEntryTransformer {
	private IAuftragFLRData[] flrData ;
	
	public void setFlrData(IAuftragFLRData[] flrData) {
		this.flrData = flrData ;
	}
	
	/**
	 * Wandelt alle FLR Objekte in typisierte Objekte um.</br>
	 * Dabei wird transformOne(Object[] o) verwendet um ein FLR Objekt
	 * zu transformieren.
	 * 
	 * @param flrObjects
	 * @return eine (leere) Liste von transformierten FLR Objekten
	 */
	public List<OrderEntry> transform(Object[][] flrObjects, TableColumnInformation columnInformation) {
		ArrayList<OrderEntry> entities = new ArrayList<OrderEntry>() ;
		if(flrObjects == null || flrObjects.length == 0) return entities ;

		if(flrObjects.length != flrData.length) {
			throw new IllegalArgumentException(
					"flrData.length (" + flrData.length + ") != flrObjects.length (" + flrObjects.length + ")") ;
		}
		
		int index = 0 ;
		for(Object[] objects : flrObjects) {
			OrderEntry entry = transformOne(objects, columnInformation) ;
			transformFlr(entry, flrData[index]) ;

			entities.add (entry) ;
			++index ;
		}

		return entities ;
	}
	
	public void transformFlr(OrderEntry entry, IAuftragFLRData flrData) {
		entry.setDeliveryPartnerId(flrData.getAddressContact().getPartnerAddress().getPartnerId());
		if(flrData.getAddressContact().getContactAddress() != null) {
			entry.setDeliveryContactId(flrData.getAddressContact().getContactAddress().getPartnerId());
		}
		entry.setInternalComment(flrData.hasInternerKommentar()) ;
		entry.setExternalComment(flrData.hasExternerKommentar()) ;
	}
}
