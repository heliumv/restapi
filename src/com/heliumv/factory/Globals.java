package com.heliumv.factory;

import com.lp.server.system.service.TheClientDto;

public class Globals {
	private static Globals globals ;

	private TheClientDto theClientDto ;
	
	private Globals() {		
	}

	private static Globals instance() {
		if(globals == null) {
			globals = new Globals() ;
		}
		return globals ;
	}
	
	public static TheClientDto getTheClientDto() {		
		return instance().theClientDto ;
	}
	
	public static void setTheClientDto(TheClientDto theClientDto) {
		instance().theClientDto = theClientDto ;
	}
}
