package com.heliumv.calltrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallTracerNop extends CallTracer {
	private static Logger log = LoggerFactory.getLogger(CallTracerNop.class) ;

	@Override
	protected void handleOverrated() {
    	log.info("Overrated ignored by purpose!");
	}
}
