package com.heliumv.factory.impl;

import javax.naming.NamingException;

import com.heliumv.factory.IClientCall;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.ILogonCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IPersonalCall;
import com.heliumv.factory.IServerCall;
import com.heliumv.factory.IZeiterfassungCall;


public class ServerCall implements IServerCall {

	private ILogonCall logonCall ;
	private IClientCall clientCall ;
	private IPersonalCall personalCall ;
	private IZeiterfassungCall zeiterfassungCall ;
	private IJudgeCall judgeCall ;
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
