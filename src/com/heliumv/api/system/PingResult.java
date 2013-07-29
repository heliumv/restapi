package com.heliumv.api.system;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PingResult {
	private long serverTime ;
	private long apiTime ;
	private Integer serverBuildNumber ;
	private String serverVersionNumber ;
	
	
	public long getApiTime() {
		return apiTime;
	}
	public void setApiTime(long apiTime) {
		this.apiTime = apiTime;
	}
	
	public long getServerTime() {
		return serverTime;
	}
	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}
	public Integer getServerBuildNumber() {
		return serverBuildNumber;
	}
	public void setServerBuildNumber(Integer serverBuildNumber) {
		this.serverBuildNumber = serverBuildNumber;
	}
	public String getServerVersionNumber() {
		return serverVersionNumber;
	}
	public void setServerVersionNumber(String serverVersionNumber) {
		this.serverVersionNumber = serverVersionNumber;
	}	
}
