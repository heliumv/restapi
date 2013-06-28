package com.heliumv.factory.query;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.heliumv.api.worktime.ZeitdatenEntry;
import com.heliumv.api.worktime.ZeitdatenEntryTransformer;
import com.heliumv.tools.StringHelper;
import com.lp.server.personal.service.ZeiterfassungFac;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.util.Helper;

public class ZeitdatenQuery extends BaseQuery<ZeitdatenEntry> {
	public ZeitdatenQuery() {
		super(QueryParameters.UC_ID_ZEITDATEN) ;
		setTransformer(new ZeitdatenEntryTransformer()) ;
	}

	@Override
	protected List<FilterKriterium> getRequiredFilters() {
		return null;
	}
	
	public FilterKriterium getFilterForPersonalId(Integer personalId)  {
		FilterKriterium fk = new FilterKriterium(
				"zeitdaten."+ZeiterfassungFac.FLR_ZEITDATEN_FLRPERSONAL + ".i_id", true,
				personalId + "", FilterKriterium.OPERATOR_EQUAL, false) ;
		return fk ;
	}


	public List<FilterKriterium> getFilterForDate(Integer year, Integer month, Integer day) {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;

		Calendar c = Calendar.getInstance() ;
		c.set(Calendar.YEAR, year) ;
		c.set(Calendar.MONTH, month - 1) ;
		c.set(Calendar.DAY_OF_MONTH, day) ;
		c.set(Calendar.HOUR, 0) ;
		c.set(Calendar.MINUTE, 0) ;
		c.set(Calendar.SECOND, 0) ;
		c.set(Calendar.MILLISECOND, 0) ;
		
		Date sqlDate = new Date(c.getTimeInMillis()) ;
		
		FilterKriterium fromDate = new FilterKriterium(
				"zeitdaten."+ZeiterfassungFac.FLR_ZEITDATEN_T_ZEIT, true, "'"
						+ Helper.formatDateWithSlashes(sqlDate) + "'",
				FilterKriterium.OPERATOR_GTE, false);
		filters.add(fromDate) ;
		
		c.add(Calendar.DAY_OF_MONTH, 1) ;
		sqlDate = new Date(c.getTimeInMillis()) ;
		FilterKriterium toDate = new FilterKriterium(
				"zeitdaten."+ZeiterfassungFac.FLR_ZEITDATEN_T_ZEIT, true, "'"
						+ Helper.formatDateWithSlashes(sqlDate) + "'",
				FilterKriterium.OPERATOR_LT, false);
		filters.add(toDate) ;
		return filters ;
	}
	

	public FilterKriterium getFilterArtikelNummer(String filterCnr)  {
		if(filterCnr == null || filterCnr.trim().length() == 0) return null ;

		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"zeitdaten."+ZeiterfassungFac.FLR_ZEITDATEN_FLRARTIKEL + ".c_nr", StringHelper.removeSqlDelimiters(filterCnr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING, true, true, Facade.MAX_UNBESCHRAENKT) ;
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;		
	}
	
}
