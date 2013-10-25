package com.heliumv.calltrace;

public class CallTracerDelaying extends CallTracer {
	@Override
	protected void handleOverrated() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		super.handleOverrated();
	}
}
