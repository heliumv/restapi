package com.heliumv.factory;

import java.util.HashMap;
import java.util.Map;

public class BaseCallRegistrant {
//	private static Log log = LogFactory.getLog(BaseCall.class) ;
	private Map<String, BaseCall<?>> registeredCalls = new HashMap<String, BaseCall<?>>() ;
		
	public void register(BaseCall<?> theCall) {
		registeredCalls.put(theCall.getBeanName(), theCall) ;
		
//		log.debug("Registered {" + theCall.getBeanName() + "}. Counting {" + registeredCalls.size() + "} elements") ;
	}
	
	public void unregister(BaseCall<?> theCall) {
		registeredCalls.remove(theCall.getBeanName()) ;
	}
	
	public void resetCalls() {
		for (BaseCall<?> theCall : registeredCalls.values()) {
			theCall.clear() ;
		}
		
		registeredCalls.clear() ;
	}
}
