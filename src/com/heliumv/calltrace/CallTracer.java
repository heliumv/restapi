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
package com.heliumv.calltrace;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heliumv.annotation.HvCallrate;
import com.lp.util.EJBExceptionLP;

/**
 * User: gp
 * Date: 24.10.13
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
public class CallTracer {
	private static Logger log = LoggerFactory.getLogger(CallTracer.class) ;
	private Map<Method, TraceData> traceData = new HashMap<Method, TraceData>() ;

    public void trace(HvCallrate theModul, Method theMethod) {
        TraceData theTrace = traceData.get(theMethod) ;
        if(theTrace == null) {
            theTrace = new TraceData() ;
            traceData.put(theMethod, theTrace) ;
        }

        if(theTrace.incrementCount() > theModul.maxCalls()) {
        	if(theTrace.getTimespan() < theModul.durationMs()) {
            	log.info("Overrated! count '" + theTrace.getCount() + "' in '" + theTrace.getTimespan() + "' ms.");
        		handleOverrated(); 
        	} else {
        		theTrace.clear() ;
        	}
        }
    }
    
    protected void handleOverrated() {
		throw new EJBExceptionLP(EJBExceptionLP.FEHLER_ZAHL_ZU_GROSS, "Throtteling") ;        	    	
    }
}
