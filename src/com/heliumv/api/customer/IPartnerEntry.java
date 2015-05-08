package com.heliumv.api.customer;

public interface IPartnerEntry {
	/**
	 * Der Adresstyp</br>
	 * <p>Beispielsweise <code>Lieferadresse</code> oder <code>Filialadresse</code></p>
	 * @return
	 */
	String getAddressType() ;
	void setAddressType(String addressType) ;
	
	/**
	 * Die erste Adresszeile
	 * 
	 * @return
	 */
	String getName1() ; 
	void setName1(String name1) ; 
	
	/**
	 * Die zweite Adresszeile
	 *
	 * @return
	 */
	String getName2() ; 
	void setName2(String name2) ;
	
	/**
	 * Die dritte Adresszeile (Abteilung)
	 * 
	 * @return
	 */
	String getName3() ;
	void setName3(String name3) ;

	/**
	 * Die Strasse
	 * 
	 * @return
	 */
	String getStreet() ;

	void setStreet(String street) ;

	/**
	 * Die Kurzbezeichnung
	 * 
	 * @return
	 */
	String getSign() ;
	void setSign(String sign) ;
	
	/**
	 * Der Titel</br>
	 * <p>Jener Teil der vorangestellt wird</p>
	 * @return
	 */
	String getTitlePrefix() ;
	void setTitlePrefix(String titelPrefix) ;

	/**
	 * Der nachgestellte Teil des Titel
	 * 
	 * @return
	 */
	String getTitlePostfix() ;
	void setTitlePostfix(String titlePostfix) ;

	/**
	 * Der zweistellige L&auml;ndercode (zum Beispiel: <code>AT</code>)
	 * @return
	 */
	String getCountryCode() ;
	void setCountryCode(String country) ;
	
	/**
	 * Der Name des Landes
	 * @return
	 */
	String getCountryName() ;
	void setCountryName(String countryName) ;
	
	/**
	 * Die Postleitzahl
	 * 
	 * @return
	 */
	String getZipcode() ;
	void setZipcode(String zipcode) ;
	
	/**
	 * Die Telefonnummer
	 * 
	 * @return
	 */
	public String getPhone() ;
	public void setPhone(String phone) ;
	
	/**
	 * Die Stadt
	 * 
	 * @return
	 */
	public String getCity() ;
	public void setCity(String city) ;

	/**
	 * Die UID des Partners
	 * 
	 * @return
	 */
	String getUid() ;
	void setUid(String uid) ;

	/**
	 * Die EORI des Partners
	 * 
	 * @return
	 */
	String getEori() ;
	void setEori(String eori) ;	
	
	/**
	 * Die Bemerkung zum Partner
	 * 
	 * @return
	 */
	String getRemark() ;
	void setRemark(String remark) ;

	/**
	 * Die E-Mail Adresse
	 * 
	 * @return
	 */
	String getEmail() ;
	void setEmail(String email) ;

	/**
	 * Die Homepage
	 * 
	 * @return
	 */
	String getWebsite() ;
	void setWebsite(String website) ;

	/**
	 * Die Faxnummer
	 * 
	 * @return
	 */
	String getFax() ;
	void setFax(String fax) ;


	/**
	 * Die von HELIUM V formatierte Ortsbezeichnung</br>
	 * <p>Beispiel: "AT 5301 Eugendorf bei Salzburg"</p>
	 * @return
	 */
	String getFormattedCity() ;
	void setFormattedCity(String formattedCity) ;
	
	/**
	 * Die von HELIUM V formatierte Anrede inkl. Titel
	 * @return die formatierte Anrede
	 */
	String getFormattedSalutation() ;
	public void setFormattedSalutation(String formattedSalutation) ;
}
