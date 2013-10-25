package com.heliumv.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ein API-Aufruf wird protokolliert und bei Bedarf verlangsamt/abgebrochen
 * User: gp
 * Date: 24.10.13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface HvCallrate {
	   int maxCalls() default 0 ;
	    int durationMs() default 0 ;
}
