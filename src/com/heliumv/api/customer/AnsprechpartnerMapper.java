package com.heliumv.api.customer;

import org.springframework.beans.factory.annotation.Autowired;

import com.lp.server.partner.service.AnsprechpartnerDto;
import com.lp.server.partner.service.PartnerDto;

public class AnsprechpartnerMapper {
	@Autowired
	private PartnerEntryMapper partnerEntryMapper ;
	
	protected PartnerEntryMapper getPartnerMapper() {
		return partnerEntryMapper ;
	}
	
	public IPartnerEntry mapAnsprechpartner(IPartnerEntry result, AnsprechpartnerDto ansprechpartnerDto) {
		if(ansprechpartnerDto != null) {
			PartnerDto partnerDto = ansprechpartnerDto.getPartnerDto() ;
			if(partnerDto != null) {
				getPartnerMapper().mapPartnerEntry(result, partnerDto) ;
			}
			
			result.setEmail(ansprechpartnerDto.getCEmail());
		}
		
		return result ;
	}
}
