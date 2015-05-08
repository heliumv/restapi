package com.heliumv.feature;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.HvClientText;
import com.heliumv.api.customer.AnsprechpartnerMapper;
import com.heliumv.api.customer.CustomerDetailEntry;
import com.heliumv.api.customer.CustomerDetailLoggedOnEntry;
import com.heliumv.api.customer.CustomerService;
import com.heliumv.api.customer.PartnerEntry;
import com.heliumv.api.customer.PartnerEntryMapper;
import com.heliumv.api.item.ItemListBuilder;
import com.heliumv.api.item.ShopGroupIdList;
import com.heliumv.api.partlist.PartlistEmailEntry;
import com.heliumv.factory.IAnsprechpartnerCall;
import com.heliumv.factory.IKundeCall;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IPartnerCall;
import com.heliumv.factory.IPersonalCall;
import com.heliumv.factory.IStuecklisteCall;
import com.heliumv.factory.IVersandCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.partner.service.AnsprechpartnerDto;
import com.lp.server.partner.service.KundeDto;
import com.lp.server.partner.service.PartnerDto;
import com.lp.server.personal.service.PersonalDto;
import com.lp.server.stueckliste.service.StuecklisteDto;
import com.lp.server.system.service.VersandauftragDto;
import com.lp.util.EJBExceptionLP;
import com.lp.util.Helper;

public class HvCustomerPartlistFeature implements HvFeature {
	@Autowired
	private IKundeCall kundeCall ;
	@Autowired
	private IAnsprechpartnerCall ansprechpartnerCall ;
	@Autowired
	private AnsprechpartnerMapper ansprechpartnerMapper ;
	@Autowired
	private PartnerEntryMapper partnerMapper ;
	
	@Autowired
	private FeatureFactory featureFactory ;
	@Autowired
	private CustomerService customerService ;
	@Autowired
	private IParameterCall parameterCall ;
	@Autowired
	private IVersandCall versandCall ;
	@Autowired
	private IMandantCall mandantCall ;
	@Autowired
	private IPersonalCall personalCall ;
	@Autowired
	private IPartnerCall partnerCall ;
	@Autowired
	private IStuecklisteCall stuecklisteCall ;
	@Autowired
	private HvClientText clientText ;
	
	private KundeDto getCustomer() throws NamingException, RemoteException {
		return kundeCall
				.kundeFindByAnsprechpartnerIdcNrMandantOhneExc(featureFactory.getAnsprechpartnerId()) ;
	}
	
	private AnsprechpartnerDto getAnsprechpartnerDto() throws NamingException, RemoteException {
		return ansprechpartnerCall.ansprechpartnerFindByPrimaryKey(featureFactory.getAnsprechpartnerId()) ;
	}
	
	public void changePassword(String password) throws NamingException, RemoteException {
		KundeDto kundeDto = getCustomer() ;
		if(kundeDto == null) return ;
//		{
//			respondBadRequest("customer", featureFactory.getAnsprechpartnerId().toString());
//			return ;
//		}
		
		AnsprechpartnerDto ansprechpartnerDto = ansprechpartnerCall
				.ansprechpartnerFindByPrimaryKey(featureFactory.getAnsprechpartnerId()) ;
		
		ansprechpartnerDto.setCKennwort(Helper.getMD5Hash(
				new String(password)).toString());
		ansprechpartnerCall.updateAnsprechpartner(ansprechpartnerDto) ;
	}
	
	public CustomerDetailLoggedOnEntry getLoggedOnCustomerDetail() throws NamingException, RemoteException {
		KundeDto kundeDto = getCustomer() ;
		if(kundeDto == null) {
//			respondBadRequest("customer", featureFactory.getAnsprechpartnerId().toString());
			return null ;
		}

		CustomerDetailEntry customerDetailEntry = customerService.getCustomerDetailEntry(kundeDto) ;
		CustomerDetailLoggedOnEntry entry = new CustomerDetailLoggedOnEntry(customerDetailEntry) ;
		
		AnsprechpartnerDto ansprechpartnerDto = ansprechpartnerCall
				.ansprechpartnerFindByPrimaryKey(featureFactory.getAnsprechpartnerId()) ;
		PartnerEntry accountEntry = new PartnerEntry();
		ansprechpartnerMapper.mapAnsprechpartner(accountEntry, ansprechpartnerDto) ;
		entry.setAccountEntry(accountEntry);
		
		PersonalDto personalDto = personalCall.byPrimaryKeySmall(kundeDto.getPersonaliIdProvisionsempfaenger()) ;
		PartnerDto partnerDto = partnerCall.partnerFindByPrimaryKey(personalDto.getPartnerIId()) ;
		PartnerEntry representativeEntry = new PartnerEntry() ;
		partnerMapper.mapPartnerEntry(representativeEntry, partnerDto) ;
		entry.setRepresentativeEntry(representativeEntry);
		return entry ;
	}
	
	@Override
	public void applyItemListFilter(ItemListBuilder builder, String filterCnr,
			String filterTextSearch, String filterDeliveryCnr,
			String filterItemGroupClass, String filterItemReferenceNr,
			Boolean filterWithHidden, Integer filterItemgroupId, String filterCustomerItemCnr, ShopGroupIdList filterItemgroupIds) throws NamingException, RemoteException {
		Integer partnerId = featureFactory.getPartnerIdFromAnsprechpartnerId() ;
		builder.clear()
			.addFilterStuecklistenPartner(partnerId)
			.addFilterWithHidden(filterWithHidden)
			.addFilterCustomerItemCnr(filterCustomerItemCnr) ;

		if(filterItemgroupId != null && filterItemgroupIds != null) {
			// Wenn beide angegeben, dann hat die Liste hoehere Prioritaet (und nimmt die einzelne Id mit auf)
			filterItemgroupIds.getEntries().add(filterItemgroupId) ;
			filterItemgroupId = null ;
		}
		if(filterItemgroupId == null && (filterItemgroupIds == null || filterItemgroupIds.getEntries().size() == 0)) {
			// Wenn weder eine Shopgruppe-Id angegeben, noch eine Shopgruppenliste vorhanden, dann nur "Artikel mit Shopgruppe+Kundenartikel"
			filterItemgroupId = 0 ;
		}
		
		builder.addFilterTextSearch(filterTextSearch);
		builder.addFilterItemGroupId(filterItemgroupId) ;
		builder.addFilterItemGroupIds(filterItemgroupIds) ;
		builder.addFilterCnr(filterCnr) ;
		
//		if(filterItemgroupId != null || filterItemgroupIds != null || StringHelper.hasContent(filterTextSearch)) {
//			builder
//				.addFilterTextSearch(filterTextSearch)
//				.addFilterItemGroupId(filterItemgroupId)
//				.addFilterItemGroupIds(filterItemgroupIds)
//				.addFilterCnr(filterCnr);
//		} else {
//			builder.addFilterTextSearch(filterCnr) ;		
//		}
	}
	
	@Override
	public void sendEmailToProvisionAccount(Integer partlistId, PartlistEmailEntry emailEntry)
			throws NamingException, RemoteException, EJBExceptionLP {
		
		StuecklisteDto stuecklisteDto = stuecklisteCall.stuecklisteFindByPrimaryKey(partlistId) ;
		String subject = clientText.getFormatted("partlist.email.header", stuecklisteDto.getArtikelDto().getCNr()) ;

		VersandauftragDto dto = new VersandauftragDto();
		dto.setCBetreff(subject);
		dto.setCText(emailEntry.getEmailText());
		
		AnsprechpartnerDto ansprechpartnerDto = getAnsprechpartnerDto() ;
		String absender = "admin@domain.local" ;
		if(!StringHelper.isEmpty(ansprechpartnerDto.getCEmail())) {
			absender = ansprechpartnerDto.getCEmail() ;
		} else {
			String mandantEmail = mandantCall.getMandantEmailAddress() ;
			absender = mandantEmail != null ? mandantEmail : "admin@domain.local" ;
		}

		dto.setCAbsenderadresse(absender) ;
			
		KundeDto kundeDto = getCustomer() ;
		if(kundeDto == null) return ;

		PersonalDto personalDto = personalCall.byPrimaryKeySmall(kundeDto.getPersonaliIdProvisionsempfaenger()) ;
		String empfaenger = "admin@heliumv.com" ;
		
		if(!StringHelper.isEmpty(personalDto.getCEmail())) {
			empfaenger = personalDto.getCEmail() ;
			dto.setCCcempfaenger(absender);
		} else {
			String mandantEmail = mandantCall.getMandantEmailAddress() ;
			empfaenger = mandantEmail != null ? mandantEmail : "admin@domain.local" ;
			
			PartnerDto partnerDto = partnerCall.partnerFindByPrimaryKey(personalDto.getPartnerIId()) ;
			dto.setCText(
					emailEntry.getEmailText() + 
					clientText.getFormatted("partlist.email.footer.missing",
							partnerDto.getCName1nachnamefirmazeile1(),
							partnerDto.getCName2vornamefirmazeile2()));
		}
		
		dto.setCEmpfaenger(empfaenger) ;		
		versandCall.createVersandAuftrag(dto) ;
	}
}
