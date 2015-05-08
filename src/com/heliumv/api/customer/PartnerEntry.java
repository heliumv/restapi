package com.heliumv.api.customer;


public class PartnerEntry implements IPartnerEntry {
	private String name1 ;
	private String name2 ;
	private String name3 ;
	private String sign ;
	private String country ;
	private String zipcode ;
	private String phone ;
	private String city ;
	private String addressType ;
	private String countryName ;
	private String street ;
	private String titlePrefix ;
	private String titlePostfix ;
	private String uid ;
	private String eori ;
	private String remark ;
	private String email ;
	private String website ;
	private String fax ;
	private String formattedCity ;
	private String formattedSalutation ;
	
	@Override
	public String getAddressType() {
		return addressType ;
	}

	@Override
	public void setAddressType(String addressType) {
		this.addressType = addressType ;
	}

	@Override
	public String getName1() {
		return name1 ;
	}

	@Override
	public void setName1(String name1) {
		this.name1 = name1 ;
	}

	@Override
	public String getName2() {
		return name2 ;
	}

	@Override
	public void setName2(String name2) {
		this.name2 = name2 ;
	}

	@Override
	public String getName3() {
		return name3 ;
	}

	@Override
	public void setName3(String name3) {
		this.name3 = name3 ;
	}

	@Override
	public String getStreet() {
		return street ;
	}

	@Override
	public void setStreet(String street) {
		this.street = street ;
	}

	@Override
	public String getSign() {
		return sign ;
	}

	@Override
	public void setSign(String sign) {
		this.sign = sign ;
	}

	@Override
	public String getTitlePrefix() {
		return titlePrefix;
	}

	@Override
	public void setTitlePrefix(String titlePrefix) {
		this.titlePrefix = titlePrefix ;
	}

	@Override
	public String getTitlePostfix() {
		return titlePostfix ;
	}

	@Override
	public void setTitlePostfix(String titlePostfix) {
		this.titlePostfix = titlePostfix ;
	}

	@Override
	public String getCountryCode() {
		return country ;
	}

	@Override
	public void setCountryCode(String country) {
		this.country  = country ;
	}

	@Override
	public String getCountryName() {
		return countryName ;
	}

	@Override
	public void setCountryName(String countryName) {
		this.countryName = countryName ;
	}

	@Override
	public String getZipcode() {
		return zipcode ;
	}

	@Override
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode ;
	}

	@Override
	public String getPhone() {
		return phone ;
	}

	@Override
	public void setPhone(String phone) {
		this.phone = phone ;
	}

	@Override
	public String getCity() {
		return city ;
	}

	@Override
	public void setCity(String city) {
		this.city = city ;
	}

	@Override
	public String getUid() {
		return uid ;
	}

	@Override
	public void setUid(String uid) {
		this.uid = uid ;
	}

	@Override
	public String getEori() {
		return eori ;
	}

	@Override
	public void setEori(String eori) {
		this.eori = eori ;
	}

	@Override
	public String getRemark() {
		return remark ;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark ;
	}

	@Override
	public String getEmail() {
		return email ;
	}

	@Override
	public void setEmail(String email) {
		this.email = email ;
	}

	@Override
	public String getWebsite() {
		return website ;
	}

	@Override
	public void setWebsite(String website) {
		this.website = website ;
	}

	@Override
	public String getFax() {
		return fax ;
	}

	@Override
	public void setFax(String fax) {
		this.fax = fax ;
	}

	@Override
	public String getFormattedCity() {
		return formattedCity ;
	}

	@Override
	public void setFormattedCity(String formattedCity) {
		this.formattedCity = formattedCity ;
	}

	public String getFormattedSalutation() {
		return formattedSalutation;
	}

	public void setFormattedSalutation(String formattedSalutation) {
		this.formattedSalutation = formattedSalutation;
	}
}
