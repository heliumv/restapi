/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/**
 * Die Eigenschaften eines Zeitbuchung beinhalten die Datums- und Zeiteingabe
 * 
 * @author Gerold
 */
public class TimeRecordingEntry {
	private String userId ;
	private int year ;
	private int month ;
	private int day ;
	private int hour ;
	private int minute ;
	private int second ;
	private Integer forStaffId ;
	private String where ;
	private String forStaffCnr ;

	
	/**
	 * Die (optionale) PersonalId f&uuml;r die die Buchung gilt
	 * @return die PersonalId f&uuml;r die die Buchung gelten soll
	 */
	public Integer getForStaffId() {
		return forStaffId;
	}

	public void setForStaffId(Integer forStaffId) {
		this.forStaffId = forStaffId;
	}
	

	/**
	 * Die (optionale) Personalnummer f&uuml;r die die Buchung gilt</br>
	 * <p>Wenn f&uuml;r eine andere Person gebucht werden soll, dann mu&szlig;
	 * entweder forStaffId oder forStaffCnr gesetzt werden</p>
	 * @return die Personalnummer f&uuml;r die die Buchung gelten soll
	 */
	public String getForStaffCnr() {
		return forStaffCnr;
	}

	public void setForStaffCnr(String forStaffCnr) {
		this.forStaffCnr = forStaffCnr;
	}

	/**
	 * Die erforderliche BenutzerId desjenigen der am HELIUM V Server angemeldet ist.
	 * @return
	 */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * Das Jahr f&uuml;r das die Buchung gelten soll
	 * @return
	 */
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * Der Monat im Bereich 1 - 12 f&uuml;r den die Buchung gelten soll
	 * @return
	 */
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
	/**
	 * Der Tag im Bereich 1 - 31 f&uuml;r den die Buchung gelten soll.
	 * @return
	 */
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	/**
	 * Die Stunde im Bereich 0 - 23 f&uuml;r die die Buchung bestimmt ist.
	 * @return
	 */
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	/**
	 * Die Minute im Bereich 0 - 59 f&uuml;r die die Buchung bestimmt ist.
	 * @return
	 */
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	/**
	 * Die (optionale) Sekunde im Bereich 0 - 59 f&uuml;r die die Buchung bestimmt ist.
	 * @return
	 */
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}

	/**
	 * Die (optionale) Quelle der Buchung</br>
	 * <p>Dies kann ein beliebiger Text sein, der aus Anwendersicht die Zuordnung zur 
	 * Buchung erm&ouml;glicht.</p>
	 * @return
	 */
	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}
}
