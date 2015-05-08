package com.heliumv.api.production;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heliumv.api.BaseFLRTransformer;
import com.lp.server.system.fastlanereader.service.TableColumnInformation;

public class OpenWorkEntryTransformer extends BaseFLRTransformer<OpenWorkEntry> {
	private static Logger log = LoggerFactory.getLogger(OpenWorkEntryTransformer.class) ;

	@Override
	public OpenWorkEntry transformOne(Object[] flrObject,
			TableColumnInformation columnInformation) {
		OpenWorkEntry entry = new OpenWorkEntry() ;
		entry.setId((Integer) flrObject[0]) ;
		entry.setCustomerShortDescription((String) flrObject[1]) ;
		entry.setAbc((String) flrObject[2]) ; 
		entry.setProductionCnr((String) flrObject[3]) ;
		entry.setPartlistCnr((String) flrObject[5]);
		entry.setPartlistDescription((String) flrObject[6]) ;
		entry.setWorkNumber((Integer) flrObject[7]) ;
		entry.setWorkItemCnr((String) flrObject[8]) ;
		entry.setWorkItemDescription((String) flrObject[9]);
		entry.setWorkItemStartDate(((Date) flrObject[10]).getTime()) ;
		entry.setMachineOffsetMs((Integer) flrObject[11]);
		String kw = (String) flrObject[12] ;
		if(kw != null) {
			try {
				String s[] = kw.split("/") ;
				entry.setWorkItemStartCalendarYear(Integer.parseInt(s[1]));
				entry.setWorkItemStartCalendarWeek(Integer.parseInt(s[0])); 
			} catch(NumberFormatException e) {
				log.error("Converting '" + kw + "' failed with:", e) ;
			}
		}
		entry.setDuration((BigDecimal) flrObject[13]) ;
		entry.setMachineCnr((String) flrObject[14]) ;
		entry.setMachineDescription((String) flrObject[15]) ;
		entry.setMaterialCnr((String) flrObject[16]) ;
		entry.setMaterialDescription((String) flrObject[17]) ;
		entry.setHasWorktime(new Boolean(flrObject[19] != null));
		entry.setMachineId((Integer) flrObject[20]) ;
		return entry ;
	}
}
