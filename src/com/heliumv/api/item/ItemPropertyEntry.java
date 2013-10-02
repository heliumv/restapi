package com.heliumv.api.item;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class ItemPropertyEntry extends BaseEntryId {
	private String datatype ;
	private String content ;
	private String name ;
	private Boolean mandatory ;
	private Integer itemgroupId ;
	
	/**
	 * Der Datentyp in der Form "java.lang.Integer", "java.lang.String", ...
	 * @return
	 */
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	
	/**
	 * Der Inhalt. Kann auch "null" sein, wenn die Eigenschaft keinen Wert gesetzt hat.
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	/** 
	 * Der Name/Bezeichnung der Eigenschaft
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Ist diese Eigenschaft zwingend erforderlich
	 * @return
	 */
	public Boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	/**
	 * Die ArtikelgruppenId 
	 * @return
	 */
	public Integer getItemgroupId() {
		return itemgroupId;
	}
	public void setItemgroupId(Integer itemgroupId) {
		this.itemgroupId = itemgroupId;
	}	
}
