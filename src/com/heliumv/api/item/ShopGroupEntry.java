package com.heliumv.api.item;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class ShopGroupEntry extends BaseEntryId {
	private String cnr ;
	private String description ;
	private Integer parentId ;
	private String parentCnr ;
	private String parentDescription ;
	
	/**
	 * @return die Shopgruppen-Kennung
	 */
	public String getCnr() {
		return cnr;
	}
	public void setCnr(String cnr) {
		this.cnr = cnr;
	}
	
	/**
	 * @return die Bezeichnung der Shopgruppe
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 * @return null (wenn kein Vorg&auml;nger) bzw. die Id der Vater-Shopgruppe
	 */
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 
	 * @return null (wenn kein Vorg&auml;nger) bzw. die Kennung der Vater-Shopgruppe
	 */
	public String getParentCnr() {
		return parentCnr;
	}
	public void setParentCnr(String parentCnr) {
		this.parentCnr = parentCnr;
	}
	
	/**
	 * 
	 * @return null (wenn kein Vorg&auml;nger) bzw. die Beschreibung der Vater-Shopgruppe
	 */
	public String getParentDescription() {
		return parentDescription;
	}
	public void setParentDescription(String parentDescription) {
		this.parentDescription = parentDescription;
	}	
}
