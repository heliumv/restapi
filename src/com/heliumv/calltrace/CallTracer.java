package com.heliumv.calltrace;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.heliumv.annotation.HvCallrate;
import com.lp.util.EJBExceptionLP;

/**
 * User: gp
 * Date: 24.10.13
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
public class CallTracer {
    private Map<Method, TraceData> traceData = new HashMap<Method, TraceData>() ;

    public void trace(HvCallrate theModul, Method theMethod) {
        TraceData theTrace = traceData.get(theMethod) ;
        if(theTrace == null) {
            theTrace = new TraceData() ;
            traceData.put(theMethod, theTrace) ;
        }

        if(theTrace.incrementCount() > theModul.maxCalls()) {
        	if(theTrace.getTimespan() < theModul.durationMs()) {
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
