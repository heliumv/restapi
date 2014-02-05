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
package com.heliumv.factory.impl;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.factory.IClientCall;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.ILogonCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IPersonalCall;
import com.heliumv.factory.IServerCall;
import com.heliumv.factory.IZeiterfassungCall;


public class ServerCall implements IServerCall {

	private ILogonCall logonCall ;

	@Autowired
	private IClientCall clientCall ;
	private IPersonalCall personalCall ;
	private IZeiterfassungCall zeiterfassungCall ;
	private IJudgeCall judgeCall ;

	@Autowired
	private IParameterCall parameterCall ;
	
	
	public ILogonCall getLogonCall() throws NamingException {
		if(logonCall == null) {
			logonCall = new LogonCall() ;
		}

		return logonCall ;
	}
	
	public IClientCall getClientCall() throws NamingException {
		if(clientCall == null) {
			clientCall = new ClientCall() ;
		}
		return clientCall ;
	}
	
	public IJudgeCall getJudgeCall() throws NamingException {
		if(judgeCall == null) {
			judgeCall = new JudgeCall() ;
		}
		
		return judgeCall;
	}
	
	public IParameterCall getParameterCall() throws NamingException {
		if(parameterCall == null) {
			parameterCall = new ParameterCall() ;
		}
		return parameterCall ;
	}
	
	public IPersonalCall getPersonalCall() throws NamingException {
		if(personalCall == null) {
			personalCall = new PersonalCall() ;
		}
		return personalCall ;
	}
	
	
	public IZeiterfassungCall getZeiterfassungCall() throws NamingException {
		if(zeiterfassungCall == null) {
			zeiterfassungCall = new ZeiterfassungCall() ;
		}
		return zeiterfassungCall ;
	}

	protected void setLogonCall(ILogonCall theLogonCall) {
		logonCall = theLogonCall ;
	}
	
	protected void setClientCall(IClientCall theClientCall) {
		clientCall = theClientCall ;
	}

	protected void setJudgeCall(IJudgeCall theJudgeCall) {
		judgeCall = theJudgeCall ;
	}
	
	protected void setParameterCall(IParameterCall theParameterCall) {
		parameterCall = theParameterCall ;
	}
	
	protected void setPersonalCall(IPersonalCall thePersonalCall) {
		personalCall = thePersonalCall ;
	}
	
	protected void setZeiterfassungCall(IZeiterfassungCall theZeiterfassungCall) {
		zeiterfassungCall = theZeiterfassungCall ;
	}
}
