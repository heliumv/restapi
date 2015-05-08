package com.heliumv.api;

public interface IBaseApiHeaderConstants {
	
	/**
	 * Header-Name f&uuml;r den HELIUM V ERROR CODE
	 */
	public final static String X_HV_ERROR_CODE = "x-hv-error-code" ;
	
	/**
	 * Header-Name f&uml;r den HELIUM V EXTENDED ERROR CODE
	 * Hier wird &uuml;blicherweise jener Fehlercode referenziert, der von einem fehlgeschlagenen
	 * EJB Server Call geliefert wird
	 */
	public final static String X_HV_ERROR_CODE_EXTENDED = "x-hv-error-code-extended" ;
	
	/**
	 * Header-Name f&uuml;r die HELIUM V EXTEND ERROR DESCRIPTION
	 * Hier wird &uuml;blicherweise die Message referenziert, die vom fehlgeschlagenen EJB 
	 * Server Call geliefert wird
	 */
	public final static String X_HV_ERROR_CODE_DESCRIPTION = "x-hv-error-description" ;
	
	public final static String X_HV_ERROR_CODE_TRANSLATED  = "x-hv-error-translated" ;
	
	/**
	 * Header-Name f&uuml;r den HELIUM V ERROR KEY
	 * Hiermit wird &uuml;blicherweise die Property/Parametername der den Fehler enth&auml;t referenziert
	 */
	public final static String X_HV_ERROR_KEY  = "x-hv-error-key" ;
	
	/**
	 * Header-Name f&uuml;r den HELIUMV V ERROR VALUE
	 * Hier wird &uuml;blicherweise der fehlerhafte Wert referenziert
	 */
	public final static String X_HV_ERROR_VALUE = "x-hv-error-value" ;
	
	public final static String X_HV_ADDITIONAL_ERROR_KEY = "x-hv-error-additional-data-key" ;
			
	public final static String X_HV_ADDITIONAL_ERROR_VALUE = "x-hv-error-additional-data-value" ;
}
