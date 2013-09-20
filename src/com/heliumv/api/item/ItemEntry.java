package com.heliumv.api.item;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
/**
 * Beschreibt die Properties eines Artikels
 * 
 * @author Gerold
 */
public class ItemEntry extends BaseEntryId {
	private String cnr ;
	private String description ;
	private String description2 ;
	private String name ;
	private String shortName ;
	
	/**
	 * Die Kennung des Artikels
	 * @return
	 */
	public String getCnr() {
		return cnr;
	}
	public void setCnr(String cnr) {
		this.cnr = cnr;
	}
	
	/**
	 * Die Zusatzbezeichnung des Artikels
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Die Zusatzbezeichnung2 des Artikels
	 * @return
	 */
	public String getDescription2() {
		return description2;
	}
	public void setDescription2(String description2) {
		this.description2 = description2;
	}
	
	/**
	 * Der Name des Artikels
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Die Kurzbezeichnung des Artikels
	 * @return
	 */
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}	
}
