package com.heliumv.api.order;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;
import com.lp.server.partner.service.IAdresse;

@XmlRootElement
public class OrderAddress extends BaseEntryId implements IAdresse{
	private String[] lines ;
	private String salutation ;
	private String titel ;
	private String titelSuffix ;
	private String name1Lastname ;
	private String name2Firstname ;
	private String name3Department ;
	private String postofficeBox ;
	private String street ;
	private String countryCode ;
	private String zipcode ;
	private String city ;
	private String email ;
	private String phone ;
	
	@Override
	public Integer getPartnerId() {
		return getId() ;
	}
	@Override
	public void setPartnerId(Integer partnerId) {
		setId(partnerId);
	}
	
	/**
	 * Formatierte Ausgabe der Adresse
	 * @return
	 */
	public String[] getLines() {
		return lines;
	}
	public void setLines(String[] lines) {
		this.lines = lines;
	}
	
	/**
	 * Die Anrede (Frau, Herr, Firma, ...)
	 * @return
	 */
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	
	/**
	 * Der vorangestellte Titel 
	 * @return
	 */
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}

	/**
	 * Der nachgestellte Titel
	 * @return
	 */
	public String getTitelSuffix() {
		return titelSuffix;
	}
	public void setTitelSuffix(String titelSuffix) {
		this.titelSuffix = titelSuffix;
	}
	public String getName1Lastname() {
		return name1Lastname;
	}
	public void setName1Lastname(String name1Lastname) {
		this.name1Lastname = name1Lastname;
	}
	public String getName2Firstname() {
		return name2Firstname;
	}
	public void setName2Firstname(String name2Firstname) {
		this.name2Firstname = name2Firstname;
	}
	public String getName3Department() {
		return name3Department;
	}
	public void setName3Department(String name3Department) {
		this.name3Department = name3Department;
	}
	public String getPostofficeBox() {
		return postofficeBox;
	}
	public void setPostofficeBox(String postofficeBox) {
		this.postofficeBox = postofficeBox;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
