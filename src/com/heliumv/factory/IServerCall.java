package com.heliumv.factory;

import javax.naming.NamingException;


public interface IServerCall {
	public ILogonCall getLogonCall() throws NamingException ;
	public IClientCall getClientCall()  throws NamingException ;
	public IJudgeCall getJudgeCall() throws NamingException ;
	public IParameterCall getParameterCall() throws NamingException ;
	public IPersonalCall getPersonalCall() throws NamingException ;
	public IZeiterfassungCall getZeiterfassungCall() throws NamingException ;
}
