package com.heliumv.tools;

import com.lp.server.util.fastlanereader.service.query.FilterKriterium;

public class FilterHelper {
	public static FilterKriterium createWithHidden(Boolean withHidden, String fieldname) {
		if(withHidden == null || withHidden == false) {
			return new FilterKriterium(fieldname, true, "(1)", 
				FilterKriterium.OPERATOR_NOT_IN, false);
		} 

		return null ;
	}
}
