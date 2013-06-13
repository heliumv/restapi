package com.heliumv.factory;

import com.lp.server.system.service.TheClientDto;

public class GlobalInfo implements IGlobalInfo {

	private TheClientDto theClientDto ;
	
	@Override
	public TheClientDto getTheClientDto() {
		return theClientDto ;
	}

	@Override
	public void setTheClientDto(TheClientDto theClientDto) {
		this.theClientDto = theClientDto ;
	}
}
