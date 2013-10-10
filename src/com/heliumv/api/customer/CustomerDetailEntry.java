package com.heliumv.api.customer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerDetailEntry extends CustomerEntry {
	private String name3 ;
	private String countryName ;
	private String street ;
	private String titlePrefix ;
	private String titlePostfix ;
	private String uid ;
	private String eori ;
//	private String postofficebox ;
	private String remark ;
	private String email ;
	private String website ;
	private String fax ;
	private String formattedCity ;
	private String pricelistCnr ;
	
	/**
	 * Der Name des Landes
	 * @return
	 */
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Die dritte Adresszeile (Abteilung)
	 * 
	 * @return
	 */
	public String getName3() {
		return name3;
	}

	public void setName3(String name3) {
		this.name3 = name3;
	}

	/**
	 * Die Strasse
	 * 
	 * @return
	 */
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Der Titel</br>
	 * <p>Jener Teil der vorangestellt wird</p>
	 * @return
	 */
	public String getTitlePrefix() {
		return titlePrefix;
	}

	public void setTitlePrefix(String titelPrefix) {
		this.titlePrefix = titelPrefix;
	}

	/**
	 * Der nachgestellte Teil des Titel
	 * 
	 * @return
	 */
	public String getTitlePostfix() {
		return titlePostfix;
	}

	public void setTitlePostfix(String titlePostfix) {
		this.titlePostfix = titlePostfix;
	}

	/**
	 * Die UID des Kunden
	 * 
	 * @return
	 */
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * Die EORI des Kunden
	 * 
	 * @return
	 */
	public String getEori() {
		return eori;
	}

	public void setEori(String eori) {
		this.eori = eori;
	}

//	public String getPostofficebox() {
//		return postofficebox;
//	}
//
//	public void setPostofficebox(String postofficebox) {
//		this.postofficebox = postofficebox;
//	}

	/**
	 * Die Bemerkung zum Kunden
	 * 
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}	

	/**
	 * Die E-Mail Adresse
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Die Homepage
	 * 
	 * @return
	 */
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Die Faxnummer
	 * 
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}


	/**
	 * Die von HELIUM V formatierte Ortsbezeichnung</br>
	 * <p>Beispiel: "AT 5301 Eugendorf bei Salzburg"</p>
	 * @return
	 */
	public String getFormattedCity() {
		return formattedCity;
	}

	public void setFormattedCity(String formattedCity) {
		this.formattedCity = formattedCity;
	}

	/**
	 * Der Schl&uuml;ssel der Preisliste</br>
	 * <p>Beispiel: "Endkunde", "H&auml;ndler"</p>
	 * @return
	 */
	public String getPricelistCnr() {
		return pricelistCnr;
	}

	public void setPricelistCnr(String pricelistCnr) {
		this.pricelistCnr = pricelistCnr;
	}	
}
