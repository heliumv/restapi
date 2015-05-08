package com.heliumv.api.internal;

import com.heliumv.tools.StringHelper;

public class ProjectNrParser {
	public final static int LENGTH_PROJECT = 5 ;
	public final static int LENGTH_SUPPORT = 4 ;
	
	public enum ProjectType {
		UNDEFINED,
		PROJECT,
		SUPPORT
	}

	private ProjectType projectType ;
	private String projectNumber ;
	
	public ProjectNrParser() {		
	}
	
	public String getProjectNumber() {
		return projectNumber ;
	}
	
	public ProjectType getProjectType() {
		return projectType ;
	}

	public boolean parse(String possibleProjectnr) {
		init() ;
		if(StringHelper.isEmpty(possibleProjectnr)) return false ;

		if(possibleProjectnr.startsWith("PJ")) return parseProject(possibleProjectnr.substring(2)) ;
		if(possibleProjectnr.startsWith("SP")) return parseSupport(possibleProjectnr.substring(2)) ;
		possibleProjectnr = possibleProjectnr.trim();
		if(Character.isDigit(possibleProjectnr.charAt(0))) return parseProjectOrSupport(possibleProjectnr) ;
		
		return false ;
	}

	
	/**
	 * Liefert eine Zahl zur&uuml;ck
	 * 
	 * @param value
	 * @return "" oder die Zahl als String
	 */
	protected String extractNumber(String value) {
		String proj = "" ;
		int length = value.length() ;
		
		int i = 0 ;
		while(i < length && Character.isSpaceChar(value.charAt(i))) {
			++i ;
		}
		if(i >= length) return "" ;
		while(i < length && Character.isDigit(value.charAt(i))) {
			proj += value.charAt(i) ;
			++i ;
		}

		return proj ;
	}
	
	protected boolean parseProject(String value) {
		String proj = extractNumber(value) ;
		if(proj.length() > 0 && proj.length() == LENGTH_PROJECT) {
			projectNumber = proj ;
			projectType = ProjectType.PROJECT ;
		}
		
		return projectNumber != null ;
	}
	
	protected boolean parseSupport(String value) {
		if(value.startsWith("J") || value.startsWith("_")) {
			value = value.substring(1) ;
		}
		String proj = extractNumber(value) ;
		if(proj.length() > 0 && proj.length() == LENGTH_SUPPORT) {
			projectNumber = proj ;
			projectType = ProjectType.SUPPORT ;
		}
		
		return projectNumber != null ;
	}

	protected boolean parseProjectOrSupport(String value) {
		String proj = extractNumber(value) ;
		if(proj.length() == LENGTH_SUPPORT) {
			projectNumber = proj ;
			projectType = ProjectType.SUPPORT ;
			return true ;
		}
		
		if(proj.length() == LENGTH_PROJECT) {
			projectNumber = proj ;
			projectType = ProjectType.PROJECT ;
			return true ;
		}
		
		return false ;
	}
	
	protected void init() {
		projectNumber = null ;
		projectType = ProjectType.UNDEFINED ;
	}
}
