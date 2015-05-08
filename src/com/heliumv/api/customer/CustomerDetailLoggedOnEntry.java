package com.heliumv.api.customer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerDetailLoggedOnEntry {
	private CustomerDetailEntry detailEntry ;
	private PartnerEntry accountEntry ;
	private PartnerEntry representativeEntry ;
	
	public CustomerDetailLoggedOnEntry() {
	}
	
	public CustomerDetailLoggedOnEntry(CustomerDetailEntry detailEntry) {
		this.detailEntry = detailEntry ;
	}
	
	public CustomerDetailLoggedOnEntry(CustomerDetailEntry detailEntry, PartnerEntry accountEntry) {
		this.detailEntry = detailEntry ;
		this.accountEntry = accountEntry ;
	}
	
	/**
	 * Die Kunden-Detaildaten
	 * @return die Kundendetails
	 */
	public CustomerDetailEntry getDetailEntry() {
		return detailEntry;
	}

	public void setDetailEntry(CustomerDetailEntry detailEntry) {
		this.detailEntry = detailEntry;
	}

	/**
	 * Der "Ansprechpartner" auf Seiten des Kunden
	 * 
	 * @return der Ansprechpartner
	 */
	public PartnerEntry getAccountEntry() {
		return accountEntry;
	}

	public void setAccountEntry(PartnerEntry accountEntry) {
		this.accountEntry = accountEntry;
	}	
	
	
	public PartnerEntry getRepresentativeEntry() {
		return representativeEntry ;
	}
	
	public void setRepresentativeEntry(PartnerEntry representativeEntry) {
		this.representativeEntry = representativeEntry ;
	}
}
