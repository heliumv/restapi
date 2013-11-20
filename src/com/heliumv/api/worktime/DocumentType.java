package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/** 
 * Eine Belegart die innerhalb HELIUM V f&uuml;r die Zeiterfassung verwendet werden kann
 * @author Gerold
 *
 */
public class DocumentType {
	private String id ;
	private String documentName ;
	
	public DocumentType() {
	}
	
	public DocumentType(String id, String documentName) {
		setId(id) ;
		setDocumentName(documentName) ;
	}
	
	/**
	 * Die id
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Der Belegname</br>
	 * <p>Beispielsweise "Angebot", "Auftrag", "Projekt", "Los"</p>
	 * @return
	 */
	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName ;
	}	
}
