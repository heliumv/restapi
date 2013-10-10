package com.heliumv.api.customer;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class CustomerEntry extends BaseEntryId {
	private String name1 ;
	private String name2 ;
	private String sign ;
	private String country ;
	private String zipcode ;
	private String phone ;
	private String city ;
	private Boolean deliveryAllowed ;
	private String classification ;
	private String addressType ;
	private String representativeSign ;
	
	/**
	 * Die erste Adresszeile
	 * 
	 * @return
	 */
	public String getName1() {
		return name1;
	}
	
	public void setName1(String name1) {
		this.name1 = name1;
	}
	
	/**
	 * Die zweite Adresszeile
	 *
	 * @return
	 */
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	
	/**
	 * Die Kurzbezeichnung
	 * 
	 * @return
	 */
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	/**
	 * Der zweistellige L&auml;ndercode (zum Beispiel: <code>AT</code>)
	 * @return
	 */
	public String getCountryCode() {
		return country;
	}
	public void setCountryCode(String country) {
		this.country = country;
	}
	
	/**
	 * Die Postleitzahl
	 * 
	 * @return
	 */
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	/**
	 * Die Telefonnummer
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * Die Stadt
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Ist eine Lieferung an den Kunden erlaubt?
	 * 
	 * @return <code>true</code> wenn die Lieferung an den Kunden m&ouml;glich ist, 
	 * mit <code>false</code> handelt es sich um einen Kunden mit Liefersperre
	 */
	public Boolean getDeliveryAllowed() {
		return deliveryAllowed;
	}
	public void setDeliveryAllowed(Boolean deliveryAllowed) {
		this.deliveryAllowed = deliveryAllowed;
	}
	
	/**
	 * Die ABC Qualifizierung des Kunden
	 * 
	 * @return
	 */
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	/**
	 * Der Adresstyp</br>
	 * <p>Beispielsweise <code>Lieferadresse</code> oder <code>Filialadresse</code></p>
	 * @return
	 */
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	
	/**
	 * Das Kurzzeichen des Vertreters (Provisionsempf&auml;ngers) im Haus f&uuml;r den Kunden
	 * 
	 * @return
	 */
	public String getRepresentativeSign() {
		return representativeSign;
	}
	public void setRepresentativeSign(String representativeSign) {
		this.representativeSign = representativeSign;
	}
}
