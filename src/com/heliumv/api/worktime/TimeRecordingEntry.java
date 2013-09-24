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
	
	/**
	 * Die (optionale) PersonalId für die die Buchung gilt
	 * @return
	 */
	public Integer getForStaffId() {
		return forStaffId;
	}

	public void setForStaffId(Integer forStaffId) {
		this.forStaffId = forStaffId;
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
	 * Das Jahr für das die Buchung gelten soll
	 * @return
	 */
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * Der Monat im Bereich 1 - 12 für den die Buchung gelten soll
	 * @return
	 */
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
	/**
	 * Der Tag im Bereich 1 - 31 für den die Buchung gelten soll.
	 * @return
	 */
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	/**
	 * Die Stunde im Bereich 0 - 23 für die die Buchung bestimmt ist.
	 * @return
	 */
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	/**
	 * Die Minute im Bereich 0 - 59 für die die Buchung bestimmt ist.
	 * @return
	 */
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	/**
	 * Die (optionale) Sekunde im Bereich 0 - 59 für die die Buchung bestimmt ist.
	 * @return
	 */
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}

	/**
	 * Die (optionale) Quelle der Buchung
	 * @return
	 */
	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}
}
