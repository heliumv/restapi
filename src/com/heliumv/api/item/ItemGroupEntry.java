package com.heliumv.api.item;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class ItemGroupEntry extends BaseEntryId {
	private String cnr ;
	private String description ;
	private Boolean bookReturn ;
	private Boolean certificationRequired ;
	private Integer parentId ;
	
	/**
	 * Die Artikelgruppen-"Nummer"
	 * @return
	 */
	public String getCnr() {
		return cnr;
	}
	public void setCnr(String cnr) {
		this.cnr = cnr;
	}
	
	/**
	 * Die Bezeichnung
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Soll die Rückgabe eines Leihartikels automatisch gebucht werden?
	 * @return
	 */
	public Boolean getBookReturn() {
		return bookReturn;
	}
	public void setBookReturn(Boolean bookReturn) {
		this.bookReturn = bookReturn;
	}
	
	/**
	 * Ist ein Lieferantenzertifikat notwendig
	 * @return
	 */
	public Boolean getCertificationRequired() {
		return certificationRequired;
	}
	public void setCertificationRequired(Boolean certificationRequired) {
		this.certificationRequired = certificationRequired;
	}
	
	/**
	 * Die Vater-Artikelgruppe sofern vorhanden
	 * @return
	 */
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}	
}
