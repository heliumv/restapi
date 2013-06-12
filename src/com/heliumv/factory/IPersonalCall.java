package com.heliumv.factory;

import javax.naming.NamingException;

import com.lp.server.personal.service.PersonalDto;

public interface IPersonalCall {
	PersonalDto byPrimaryKeySmall(Integer personalIId) throws NamingException  ;
}
