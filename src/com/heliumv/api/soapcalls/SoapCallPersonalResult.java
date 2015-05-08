package com.heliumv.api.soapcalls;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoapCallPersonalResult {
	private Integer resultCode ;

	public SoapCallPersonalResult() {
		resultCode = 0 ;
	}
	
	public SoapCallPersonalResult(int resultCode) {
		this.resultCode = resultCode ;
	}
	
	/**
	 * 
	 * @return 0 ... ok, -1 ... Los nicht gefunden , -4 ... Personal nicht gefunden
	 */
	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
}
