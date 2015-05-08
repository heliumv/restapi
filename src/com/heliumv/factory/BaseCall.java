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
package com.heliumv.factory;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.tools.UncheckedCasts;


public class BaseCall<T> implements IBaseCallBeans {
	private static Logger log = LoggerFactory.getLogger(BaseCall.class) ;
	
	private Context context = null ;
	private T callFac = null ;
	private String beanName = null ;

	@Autowired
	private BaseCallRegistrant baseCallRegistrant ;
	
  	protected BaseCall(String beanName) {
  		if(null == beanName || beanName.trim().length() == 0) throw new IllegalArgumentException("beanName == null or empty") ;
  		this.beanName = beanName ;
	}

	private String getServerBeanName(String beanName) {
		return "lpserver/" + beanName + "/remote" ;
	}
	
	public String getBeanName() {
		return beanName ;
	}
	
	public void clear() {
		callFac = null ;
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
		
		log.debug("namingFactory = {" + namingFactory +"}") ;
		log.debug("urlProvider = {" + urlProvider + "}") ;
		
		Hashtable<String, String> environment = new Hashtable<String, String>();

		environment.put(Context.INITIAL_CONTEXT_FACTORY, namingFactory);
		environment.put(Context.PROVIDER_URL, urlProvider);
		return new InitialContext(environment);
	}
	
	protected T getFac() throws NamingException {
		if(callFac == null) {
			context = getInitialContext() ;
			callFac = UncheckedCasts.cast(context.lookup(getServerBeanName(beanName))) ;
			
			baseCallRegistrant.register(this);
		}
		
		return callFac ;
	}
}
