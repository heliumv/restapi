package com.heliumv.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Ein bestimmtes Modul muss in HELIUM V freigeschaltet sein
 */
public @interface HvModul {
	/**
	 * Der Modulname aus LocaleFac.BELEGART_*
	 */
	String modul() ;
}
