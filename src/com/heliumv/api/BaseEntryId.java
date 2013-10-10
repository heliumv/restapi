package com.heliumv.api;

import com.heliumv.annotation.HvFlrMapper;

public class BaseEntryId {
	private Integer id ;

	/**
	 * Die eindeutige Id des Eintrags
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	@HvFlrMapper(flrName="i_id")
	public void setId(Integer id) {
		this.id = id;
	}
	
}
