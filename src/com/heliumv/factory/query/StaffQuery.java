package com.heliumv.factory.query;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.staff.StaffEntry;
import com.heliumv.api.staff.StaffEntryTransformer;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.IPersonalCall;
import com.heliumv.factory.impl.FastLaneReaderCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.personal.service.PersonalDto;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

public class StaffQuery extends FastLaneReaderCall {

	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Autowired
	private IJudgeCall judgeCall ;
	@Autowired
	private IPersonalCall personalCall ;
	
	private StaffEntryTransformer entryTransformer = new StaffEntryTransformer() ;
	
	public StaffQuery() {
		super(QueryParameters.UC_ID_PERSONAL) ;
	}
	
	@Override
	protected List<FilterKriterium> getRequiredFilters() {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		filters.add(getMandantFilter()) ;
		try {
			filters.addAll(getPersonalFilter()) ;
		} catch(NamingException e) {			
		}
		return filters ;
	}

	public List<StaffEntry> getResultList(QueryResult result) {
		return entryTransformer.transform(result.getRowData()) ;
	}
	
	private FilterKriterium getMandantFilter() {
		return new FilterKriterium("mandant_c_nr", true,
				StringHelper.asSqlString(globalInfo.getTheClientDto().getMandant()),
				FilterKriterium.OPERATOR_EQUAL, false) ;
	}
	
	private FilterKriterium getOnlyMeFilter() {
		FilterKriterium fk = new FilterKriterium("i_id", true,
					globalInfo.getTheClientDto().getIDPersonal() + "", FilterKriterium.OPERATOR_EQUAL, false);
		return fk ;		
	}
	
	private FilterKriterium getAbteilungFilter(Integer abteilung) {
		FilterKriterium fk = new FilterKriterium(
				"flrkostenstelleabteilung.i_id", true,
				abteilung + "", FilterKriterium.OPERATOR_EQUAL, false) ;
		return fk ;
	}
	
	private List<FilterKriterium> getPersonalFilter() throws NamingException {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;

 		if(judgeCall.hasPersSichtbarkeitAlle()) return filters ;
 		
 		Integer abteilung = null ; 		
 		if(judgeCall.hasPersSichtbarkeitAbteilung()) { 			
 			PersonalDto personalDto = personalCall.byPrimaryKeySmall(globalInfo.getTheClientDto().getIDPersonal()) ;
 			abteilung = personalDto.getKostenstelleIIdAbteilung() ;
 		}

 		filters.add(abteilung != null ? getAbteilungFilter(abteilung) : getOnlyMeFilter()) ;

 		return filters ;
	}
}
