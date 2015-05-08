package com.heliumv.api.customer;

import com.lp.server.partner.service.PartnerDto;
import com.lp.server.system.service.LandplzortDto;

public class PartnerEntryMapper {
	public IPartnerEntry mapPartnerEntry(IPartnerEntry result, PartnerDto partnerDto) {
		if(partnerDto != null) {
			result.setName1(partnerDto.getCName1nachnamefirmazeile1());
			result.setName2(partnerDto.getCName2vornamefirmazeile2()) ;
			result.setPhone(partnerDto.getCTelefon()); 
			result.setStreet(partnerDto.getCStrasse());
			result.setSign(partnerDto.getCKbez());
			result.setAddressType(partnerDto.getCAdressart());
			result.setName3(partnerDto.getCName3vorname2abteilung()) ;
			result.setTitlePrefix(partnerDto.getCTitel());
			result.setTitlePostfix(partnerDto.getCNtitel()) ;
			result.setUid(partnerDto.getCUid()) ;
			result.setEori(partnerDto.getCEori()) ;
			result.setRemark(partnerDto.getXBemerkung()) ;
			result.setEmail(partnerDto.getCEmail()) ;
			result.setWebsite(partnerDto.getCHomepage()) ;
			result.setFax(partnerDto.getCFax()) ;
			if(partnerDto.getLandplzortDto() != null) {
				LandplzortDto lpoDto = partnerDto.getLandplzortDto() ;
				if(lpoDto.getLandDto() != null) {
					result.setCountryCode(lpoDto.getLandDto().getCLkz()) ;
					result.setCountryName(lpoDto.getLandDto().getCName()) ;
					
					result.setZipcode(lpoDto.getCPlz());
				}

				if(lpoDto.getOrtDto() != null) {
					result.setCity(lpoDto.getOrtDto().getCName()) ;
				}
				
				result.setFormattedCity(partnerDto.formatLKZPLZOrt()) ;				
			}
			result.setFormattedSalutation(partnerDto.formatTitelAnrede()) ;
		}
		return result ;
	}
}
