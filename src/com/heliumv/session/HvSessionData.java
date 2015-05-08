package com.heliumv.session;

import java.util.ArrayList;
import java.util.List;

import com.lp.server.stueckliste.service.KundenStuecklistepositionDto;
import com.lp.server.stueckliste.service.StuecklistepositionDto;

public class HvSessionData {

	private long timeCreated ;
	private List<StuecklistepositionDto> stklPositionen ;
	
	public HvSessionData() {
		timeCreated = System.currentTimeMillis() ;
		stklPositionen = new ArrayList<StuecklistepositionDto>();
	}

	public long getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}

	public List<StuecklistepositionDto> getStklPositionen() {
		return stklPositionen;
	}

	public void setStklPositionen(List<StuecklistepositionDto> stklPositionen) {
		this.stklPositionen = stklPositionen;
	}
	
	public void setKundenStklPositionen(List<KundenStuecklistepositionDto> kundenStklPositionen) {
		List<StuecklistepositionDto> dtos = new ArrayList<StuecklistepositionDto>() ;
		for (KundenStuecklistepositionDto kundenpositionDto : kundenStklPositionen) {
			dtos.add(kundenpositionDto.getPositionDto()) ;
		}
		this.stklPositionen = dtos ;
	}
	public void removeStklPosition(StuecklistepositionDto positionDto) {
		stklPositionen.remove(positionDto) ;
	}
	
	public void addStklPosition(StuecklistepositionDto positionDto) {
		stklPositionen.add(positionDto) ;
	}
	
	public void updateStklPosition(StuecklistepositionDto positionDto) {
		for(int i = 0 ; i < stklPositionen.size(); i++) {
			if(stklPositionen.get(i).getIId().compareTo(positionDto.getIId()) == 0) {
				stklPositionen.remove(i) ;
				stklPositionen.add(positionDto) ;
				return ;
 			}
		}
	}
}
