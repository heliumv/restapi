package com.heliumv.factory.legacy;

import com.lp.server.system.service.PanelbeschreibungDto;
import com.lp.server.system.service.PaneldatenDto;

public class PaneldatenPair {
	private PaneldatenDto paneldatenDto ;
	private PanelbeschreibungDto panelbeschreibungDto ;
	
	public PaneldatenPair() {		
	}

	public PaneldatenPair(PaneldatenDto paneldatenDto, PanelbeschreibungDto panelbeschreibungDto) {
		this.paneldatenDto = paneldatenDto ;
		this.panelbeschreibungDto = panelbeschreibungDto ;
	}
	
	public PaneldatenDto getPaneldatenDto() {
		return paneldatenDto;
	}

	public void setPaneldatenDto(PaneldatenDto paneldatenDto) {
		this.paneldatenDto = paneldatenDto;
	}

	public PanelbeschreibungDto getPanelbeschreibungDto() {
		return panelbeschreibungDto;
	}

	public void setPanelbeschreibungDto(PanelbeschreibungDto panelbeschreibungDto) {
		this.panelbeschreibungDto = panelbeschreibungDto;
	}	
}
