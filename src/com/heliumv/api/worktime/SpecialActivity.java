package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Eine Sondertaetigkeit
 * 
 * @author Gerold
 */
@XmlRootElement
public class SpecialActivity {
	private Integer id ;
	private String activity ;
	
	public SpecialActivity() {
	}
	
	public SpecialActivity(Integer theId, String theActivity) {
		id = theId ;
		activity = theActivity ;
	}
	
	/**
	 * Die id
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Die Bezeichnung der Sondertaetigkeit</br>
	 * <p>Beispielsweise "KOMMT", "GEHT", "ARZT", "KRANK"</p>
	 * @return
	 */
	public String getActivity() {
		return activity;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}
}
