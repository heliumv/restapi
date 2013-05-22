package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentType {
	private String id ;
	private String documentName ;
	
	public DocumentType() {
	}
	
	public DocumentType(String id, String documentName) {
		setId(id) ;
		setDocumentName(documentName) ;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName ;
	}	
}
