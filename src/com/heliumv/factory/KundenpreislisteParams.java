package com.heliumv.factory;

import java.sql.Date;
import java.util.GregorianCalendar;

public class KundenpreislisteParams {
	private Integer kundeId ;
	private Integer artikelgruppeId ;
	private Integer artikelklasseId ;
	private String itemCnrVon ;
	private String itemCnrBis ;
	private boolean mitVersteckten ;
	private Date    gueltigkeitsDatum ;
	private boolean nurSonderkonditionen ;
	private boolean mitMandantensprache ;
	private boolean mitInaktiven ;
	private boolean nurWebshop ;
	
	public KundenpreislisteParams() {
		setDefaults() ;
	}
	
	public KundenpreislisteParams(Integer kundeId) {
		setDefaults() ;
		this.kundeId = kundeId ;
	}
	
	private void setDefaults() {
		mitInaktiven = true ;		
	}
	
	public Integer getKundeId() {
		return kundeId;
	}
	public void setKundeId(Integer kundeId) {
		this.kundeId = kundeId;
	}
	public Integer getArtikelgruppeId() {
		return artikelgruppeId;
	}
	public void setArtikelgruppeId(Integer artikelgruppeId) {
		this.artikelgruppeId = artikelgruppeId;
	}
	public Integer getArtikelklasseId() {
		return artikelklasseId;
	}
	public void setArtikelklasseId(Integer artikelklasseId) {
		this.artikelklasseId = artikelklasseId;
	}
	public String getItemCnrVon() {
		return itemCnrVon;
	}
	public void setItemCnrVon(String itemCnrVon) {
		this.itemCnrVon = itemCnrVon;
	}
	public String getItemCnrBis() {
		return itemCnrBis;
	}
	public void setItemCnrBis(String itemCnrBis) {
		this.itemCnrBis = itemCnrBis;
	}
	public boolean isMitVersteckten() {
		return mitVersteckten;
	}
	public void setMitVersteckten(boolean mitVersteckten) {
		this.mitVersteckten = mitVersteckten;
	}
	public Date getGueltigkeitsDatum() {
		if(gueltigkeitsDatum == null) {
			gueltigkeitsDatum = new Date(GregorianCalendar.getInstance().getTimeInMillis()) ;
		}
		return gueltigkeitsDatum;
	}
	public void setGueltigkeitsDatum(Date gueltigkeitsDatum) {
		this.gueltigkeitsDatum = gueltigkeitsDatum;
	}
	public boolean isNurSonderkonditionen() {
		return nurSonderkonditionen;
	}
	public void setNurSonderkonditionen(boolean nurSonderkonditionen) {
		this.nurSonderkonditionen = nurSonderkonditionen;
	}
	public boolean isMitMandantensprache() {
		return mitMandantensprache;
	}
	public void setMitMandantensprache(boolean mitMandantensprache) {
		this.mitMandantensprache = mitMandantensprache;
	}
	public boolean isMitInaktiven() {
		return mitInaktiven;
	}
	public void setMitInaktiven(boolean mitInaktiven) {
		this.mitInaktiven = mitInaktiven;
	}

	public boolean isNurWebshop() {
		return nurWebshop;
	}

	public void setNurWebshop(boolean nurWebshop) {
		this.nurWebshop = nurWebshop;
	}	
}
