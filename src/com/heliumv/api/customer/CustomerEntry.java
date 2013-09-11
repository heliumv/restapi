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
	
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCountryCode() {
		return country;
	}
	public void setCountryCode(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Boolean getDeliveryAllowed() {
		return deliveryAllowed;
	}
	public void setDeliveryAllowed(Boolean deliveryAllowed) {
		this.deliveryAllowed = deliveryAllowed;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getRepresentativeSign() {
		return representativeSign;
	}
	public void setRepresentativeSign(String representativeSign) {
		this.representativeSign = representativeSign;
	}	
}
