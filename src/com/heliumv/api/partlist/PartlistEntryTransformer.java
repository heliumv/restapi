/**
 * 
 */
package com.heliumv.api.partlist;

import java.util.ArrayList;
import java.util.List;

import com.heliumv.api.BaseFLRTransformer;
import com.lp.server.stueckliste.service.IStuecklisteFLRData;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

/**
 * @author Gerold
 *
 */
public class PartlistEntryTransformer extends BaseFLRTransformer<PartlistEntry> {
	private IStuecklisteFLRData[] flrData ;
	
	public void setFlrData(IStuecklisteFLRData[] flrData) {
		this.flrData = flrData ;
	}
	
	
	@Override
	public List<PartlistEntry> transform(Object[][] flrObjects,
			TableColumnInformation columnInformation) {
		ArrayList<PartlistEntry> entities = new ArrayList<PartlistEntry>() ;
		if(flrObjects == null || flrObjects.length == 0) return entities ;

		if(flrObjects.length != flrData.length) {
			throw new IllegalArgumentException(
					"flrData.length (" + flrData.length + ") != flrObjects.length (" + flrObjects.length + ")") ;
		}
		
		int index = 0 ;
		for(Object[] objects : flrObjects) {
			PartlistEntry entry = transformOne(objects, columnInformation) ;
			transformFlr(entry, flrData[index]) ;

			entities.add (entry) ;
			++index ;
		}

		return entities ;
	}
	
	@Override
	public PartlistEntry transformOne(Object[] flrObject,
			TableColumnInformation columnInformation) {
		PartlistEntry entry = new PartlistEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setCnr((String) flrObject[1]);
		entry.setDescription((String)flrObject[3]);
		entry.setAdditionalDescription((String)flrObject[4]);
		
		return entry ;
	}
	
	public void transformFlr(PartlistEntry entry, IStuecklisteFLRData flrData) {
		entry.setLotCount(flrData.getLosCount());
		entry.setStatusCnr(flrData.getStatusCnr());
		entry.setCustomerItemCnr(flrData.getKundenartikelNr()) ;
	}	
}
