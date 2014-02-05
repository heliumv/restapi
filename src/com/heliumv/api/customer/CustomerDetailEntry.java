/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
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
