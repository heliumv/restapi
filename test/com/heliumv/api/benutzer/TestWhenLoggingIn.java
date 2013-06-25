package com.heliumv.api.benutzer;

import junit.framework.TestCase;

public class TestWhenLoggingIn extends TestCase {
	private UserApi sut ;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		sut = new UserApi() ;
	}
	
	public void testWithNullParameters() {
		assertNull(sut.logon(null)) ;
	}
}
