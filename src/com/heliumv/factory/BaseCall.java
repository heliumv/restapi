package com.heliumv.factory;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class BaseCall<T> implements IBaseCallBeans {
	private Context context = null ;
	private T callFac = null ;
	
  	protected BaseCall(String beanName) throws NamingException {
  		if(null == beanName || beanName.trim().length() == 0) throw new IllegalArgumentException("beanName == null or empty") ;
		context = getInitialContext() ;
		callFac = (T) context.lookup(getServerBeanName(beanName)) ;
	}

	private String getServerBeanName(String beanName) {
		return "lpserver/" + beanName + "/remote" ;
	}
	
	/*
	 * Die Settings sind in der web.xml abgelegt. Beispiel:
	 * 
	 * <web-app>
	 *   <env-entry>
	 *   	<env-entry-name>java.naming.provider.url</env-entry-name>
	 *   	<env-entry-type>java.lang.String</env-entry-type>
	 *   	<env-entry-value>jnp://localhost:2099</env-entry-value>
	 *   </env-entry>
	 *   <env-entry>
	 *   	<env-entry-name>java.naming.factory.initial</env-entry-name>
	 *   	<env-entry-type>java.lang.String</env-entry-type>
	 *   	<env-entry-value>org.jnp.interfaces.NamingContextFactory</env-entry-value>
	 *   </env-entry>
	 * </web-app>
	 */
	private Context getInitialContext() throws NamingException {
		Context env = (Context) new InitialContext().lookup("java:comp/env") ;
		String namingFactory = (String) env.lookup(Context.INITIAL_CONTEXT_FACTORY) ;
		String urlProvider = (String) env.lookup(Context.PROVIDER_URL) ;
		
		Hashtable<String, String> environment = new Hashtable<String, String>();

		environment.put(Context.INITIAL_CONTEXT_FACTORY, namingFactory);
		environment.put(Context.PROVIDER_URL, urlProvider);
		return new InitialContext(environment);
	}
	
	protected T getFac() {
		return callFac ;
	}
}
