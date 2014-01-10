package com.heliumv.api.benutzer;

import org.springframework.mock.web.MockHttpServletResponse;

import com.heliumv.api.user.LogonEntry;
import com.heliumv.api.user.UserApi;

import junit.framework.TestCase;

public class TestWhenLoggingIn extends TestCase {
	private UserApi sut ;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		sut = new UserApi() ;
		sut.setHttpServletResponse(new MockHttpServletResponse()) ;
	}
	
	public void testWithNullParameters() {
		assertNull(sut.logon(null)) ;
	}
	
	public void testWithEmptyLogonEntry() {
		LogonEntry l = new LogonEntry() ;
		assertNull(sut.logon(l)) ;
	}

	public void testWithEmptyPassword() {
		LogonEntry l = new LogonEntry() ;
		l.setUsername("LPAdmin") ;
		assertNull(sut.logon(l)) ;
	}
	
	public void testWithUserAndPassword() {
		LogonEntry l = new LogonEntry() ;
		l.setUsername("LPAdmin") ;
		l.setPassword("klpadmin1") ;
		assertNull(sut.logon(l)) ;
	}
}
