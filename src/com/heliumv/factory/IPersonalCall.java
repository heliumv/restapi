package com.heliumv.factory;

import com.lp.server.personal.service.PersonalDto;

public interface IPersonalCall {
	PersonalDto byPrimaryKeySmall(Integer personalIId) ;
}
