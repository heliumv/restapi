package com.heliumv.factory;

import com.lp.server.system.service.TheClientDto;

public interface IGlobalInfo {
	
	TheClientDto getTheClientDto() ;
	void setTheClientDto(TheClientDto theClientDto) ;
	
	String getMandant() ;
}
