package com.heliumv.factory;

import javax.naming.NamingException;

public interface IFertigungCallJudge extends IFertigungCall {
	boolean darfGebeMaterialNachtraeglichAus() throws NamingException ;
}
