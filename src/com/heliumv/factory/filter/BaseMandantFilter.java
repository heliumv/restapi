package com.heliumv.factory.filter;

import com.lp.server.util.fastlanereader.service.query.FilterKriterium;

public class BaseMandantFilter extends FilterKriterium {
	private static final long serialVersionUID = -4529361044612477240L;

	public BaseMandantFilter(String flrTableName, String mandantCnr) {
		super(flrTableName + ".mandant_c_nr", true, "'" + mandantCnr + "'", FilterKriterium.OPERATOR_EQUAL, false) ;
	}
}
