package com.heliumv.api.item;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.annotation.HvFlrMapper;
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
	private BigDecimal stockAmount ;
	private BigDecimal costs ;
	private String billOfMaterialType ;
	private Boolean available ;
	private List<String> comments ;
	private Boolean hidden ;
	private String unitCnr ;
	private String typeCnr ;
	private String itemgroupCnr ;
	private String itemclassCnr ;
	private String revision ;
	private String referenceNumber ;
	private String index ;
	
	private StockAmountInfoEntry stockAmountInfo ;

	/**
	 * Die Kennung des Artikels (Artikelnummer)
	 * @return
	 */
	public String getCnr() {
		return cnr;
	}
	@HvFlrMapper(flrName="artikel.artikelnummerlang")
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
	@HvFlrMapper(flrName="bes.artikelbezeichnung") 
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
	@HvFlrMapper(flrName = "artikel.zusatzbez")
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
	@HvFlrMapper(flrName = "lp.kurzbezeichnung")
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	/**
	 * Der "verf&uuml;gbare" Lagerstand
	 * @return
	 */
	public BigDecimal getStockAmount() {
		return stockAmount;
	}
	@HvFlrMapper(flrName = "lp.lagerstand")
	public void setStockAmount(BigDecimal stockAmount) {
		this.stockAmount = stockAmount;
	}
	
	/**
	 * Der Gestehungspreis
	 * @return
	 */
	public BigDecimal getCosts() {
		return costs;
	}
	@HvFlrMapper(flrName = "lp.preis")
	public void setCosts(BigDecimal costs) {
		this.costs = costs;
	}
	
	/**
	 * Die Stücklistenart
	 * @return
	 */
	public String getBillOfMaterialType() {
		return billOfMaterialType;
	}
	@HvFlrMapper(flrName = "lp.stuecklistenart")
	public void setBillOfMaterialType(String billOfMaterialType) {
		this.billOfMaterialType = billOfMaterialType;
	}

	/**
	 * Ist der Artikel verfügbar bzw. gesperrt?
	 * @return
	 */
	public Boolean isAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
	@HvFlrMapper(flrName="Icon")
	public void setAvailable(Object[] iconInfo) {
		available = iconInfo == null ;
	}
	
	/**
	 * Die Liste aller Artikelkommentare im Format "text/html"
	 * @return null, oder eine Liste der für den Artikel vorhandenen Artikelkommentar im Format "text/html"
	 */
	public List<String> getComments() {
		return comments;
	}
	
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	
	/**
	 * Ist es ein versteckter Artikel?
	 * 
	 * @return true wenn es ein versteckter Artikel ist
	 */
	public Boolean isHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	
	/**
	 * Die Einheit des Artikels
	 * @return
	 */
	public String getUnitCnr() {
		return unitCnr;
	}
	
	public void setUnitCnr(String unitCnr) {
		this.unitCnr = unitCnr;
	}
	
	/**
	 * Die Artikelart
	 * @return
	 */
	public String getTypeCnr() {
		return typeCnr;
	}
	public void setTypeCnr(String typeCnr) {
		this.typeCnr = typeCnr;
	}
	
	/**
	 * Die Artikelgruppe
	 * @return
	 */
	public String getItemgroupCnr() {
		return itemgroupCnr;
	}
	@HvFlrMapper(flrNames= {"lp.artikelgruppeInAbmessung", "lp.artikelgruppe" })
	public void setItemgroupCnr(String itemgroupCnr) {
		this.itemgroupCnr = itemgroupCnr;
	}
	
	/**
	 * Die Artikelklasse
	 */
	public String getItemclassCnr() {
		return itemclassCnr;
	}
	@HvFlrMapper(flrName="lp.artikelklasse")
	public void setItemclassCnr(String itemclassCnr) {
		this.itemclassCnr = itemclassCnr;
	}
	
	/**
	 * Die Revisions"nummer"
	 * @return
	 */
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}

	/**
	 * Lagerstandsinformationen
	 * 
	 * @return
	 */
	public StockAmountInfoEntry getStockAmountInfo() {
		return stockAmountInfo;
	}
	public void setStockAmountInfo(StockAmountInfoEntry stockAmountInfo) {
		this.stockAmountInfo = stockAmountInfo;
	}
	
	/**
	 * Die Referenznummer
	 * @return
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	
	/**
	 * Die Indexnummer
	 * @return
	 */
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}	

}
