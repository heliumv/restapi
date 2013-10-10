package com.heliumv.api.customer;

import com.lp.server.artikel.service.VkpfartikelpreislisteDto;
import com.lp.server.partner.service.KundeDto;
import com.lp.server.partner.service.PartnerDto;
import com.lp.server.personal.service.PersonalDto;
import com.lp.server.system.service.LandplzortDto;

public class CustomerEntryMapper {
	public CustomerDetailEntry mapDetailEntry(KundeDto kundeDto) {
		CustomerDetailEntry customer = new CustomerDetailEntry() ;
		if(kundeDto != null) {
			customer.setId(kundeDto.getIId()) ;
			// customer.setAddressType("");
			if(kundeDto.getPartnerDto() != null) {
				PartnerDto partnerDto = kundeDto.getPartnerDto() ;
				customer.setName1(partnerDto.getCName1nachnamefirmazeile1());
				customer.setName2(partnerDto.getCName2vornamefirmazeile2()) ;
				customer.setPhone(partnerDto.getCTelefon()); 
				customer.setStreet(partnerDto.getCStrasse());
				customer.setSign(partnerDto.getCKbez());
				customer.setAddressType(partnerDto.getCAdressart());
				customer.setDeliveryAllowed(kundeDto.getTLiefersperream() == null) ;
				customer.setClassification(kundeDto.getCAbc()) ;
//				customer.setRepresentativeSign(kundeDto.getPersonaliIdProvisionsempfaenger());
				customer.setName3(partnerDto.getCName3vorname2abteilung()) ;
				customer.setTitlePrefix(partnerDto.getCTitel());
				customer.setTitlePostfix(partnerDto.getCNtitel()) ;
				customer.setUid(partnerDto.getCUid()) ;
				customer.setEori(partnerDto.getCEori()) ;
//				customer.setPostofficebox(partnerDto.getCPostfach()) ;
				customer.setRemark(partnerDto.getXBemerkung()) ;
				customer.setEmail(partnerDto.getCEmail()) ;
				customer.setWebsite(partnerDto.getCHomepage()) ;
				customer.setFax(partnerDto.getCFax()) ;
				if(partnerDto.getLandplzortDto() != null) {
					LandplzortDto lpoDto = partnerDto.getLandplzortDto() ;
					if(lpoDto.getLandDto() != null) {
						customer.setCountryCode(lpoDto.getLandDto().getCLkz()) ;
						customer.setCountryName(lpoDto.getLandDto().getCName()) ;
						
						customer.setZipcode(lpoDto.getCPlz());
					}

					if(lpoDto.getOrtDto() != null) {
						customer.setCity(lpoDto.getOrtDto().getCName()) ;
					}
					
					customer.setFormattedCity(partnerDto.formatLKZPLZOrt()) ;
				}
				
				kundeDto.getVkpfArtikelpreislisteIIdStdpreisliste() ;
			}
		}
		return customer ;
	}
	
	public CustomerDetailEntry mapDetailEntry(KundeDto kundeDto, PersonalDto personalDto, VkpfartikelpreislisteDto preislisteDto) {
		CustomerDetailEntry entry = mapDetailEntry(kundeDto) ;
		mapDetailEntry(entry, preislisteDto) ;
		mapDetailEntry(entry, personalDto) ;
		return entry ;
	}
	
	public CustomerDetailEntry mapDetailEntry(CustomerDetailEntry customer, VkpfartikelpreislisteDto preislisteDto) {
		if(customer != null && preislisteDto != null) {
			customer.setPricelistCnr(preislisteDto.getCNr()) ;			
		}
		return customer ;
	}

	public CustomerDetailEntry mapDetailEntry(CustomerDetailEntry customer, PersonalDto personalDto) {
		if(customer != null && personalDto != null) {
			customer.setRepresentativeSign(personalDto.getCKurzzeichen()) ;			
		}
		return customer ;
	}
	
}
