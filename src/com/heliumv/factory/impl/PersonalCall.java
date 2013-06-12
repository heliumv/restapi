package com.heliumv.factory.impl;

import javax.naming.NamingException;

import com.heliumv.factory.BaseCall;
import com.heliumv.factory.IPersonalCall;
import com.lp.server.personal.service.PersonalDto;
import com.lp.server.personal.service.PersonalFac;

public class PersonalCall extends BaseCall<PersonalFac> implements IPersonalCall {

	public PersonalCall() {
		super(PersonalFacBean) ;
	}
	
	public PersonalDto byPrimaryKeySmall(Integer personalIId) throws NamingException {
		return getFac().personalFindByPrimaryKeySmallOhneExc(personalIId) ;
	}	
}
