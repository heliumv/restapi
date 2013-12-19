package com.heliumv.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Ein bestimmtes Recht muss fuer den angemeldeten Benutzer in HELIUM V freigeschaltet sein
 */
public @interface HvJudge {
	/**
	 * Der Rechtename aus RechteFac.RECHT_PERS_ZEITERFASSUNG_R
	 */
	String recht() default "" ;	
	
	/**
	 * Eines dieser Rechte aus RechteFac.RECHT_* muss erfuellt sein
	 * @return
	 */
	String[] rechtOder() default {""};	
}
