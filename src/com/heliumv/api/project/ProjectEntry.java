package com.heliumv.api.project;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProjectEntry {
	private Integer id ;
	private String cnr ;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCnr() {
		return cnr;
	}
	public void setCnr(String cnr) {
		this.cnr = cnr;
	}	
}
