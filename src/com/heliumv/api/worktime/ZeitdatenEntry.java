package com.heliumv.api.worktime;

import javax.xml.bind.annotation.XmlRootElement;

import com.heliumv.api.BaseEntryId;

@XmlRootElement
public class ZeitdatenEntry extends BaseEntryId {
	private String activityCnr ;
	private String description ;
	private String time ;
	private String duration ;
	private String flags ;
	private String where ;
	
	public String getActivityCnr() {
		return activityCnr;
	}
	public void setActivityCnr(String activityCnr) {
		this.activityCnr = activityCnr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
}
