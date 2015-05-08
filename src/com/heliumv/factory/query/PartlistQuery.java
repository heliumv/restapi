package com.heliumv.factory.query;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.partlist.PartlistEntry;
import com.heliumv.api.partlist.PartlistEntryTransformer;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.feature.FeatureFactory;
import com.lp.server.stueckliste.service.StuecklisteQueryResult;
import com.lp.server.util.Validator;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

public class PartlistQuery extends BaseQuery<PartlistEntry> {
	@Autowired
	private IGlobalInfo globalInfo ;
	@Autowired
	private FeatureFactory featureFactory ;

	
	public PartlistQuery() {
		super(QueryParameters.UC_ID_STUECKLISTE) ;
	}

	@Override
	protected List<FilterKriterium> getRequiredFilters()
			throws NamingException, RemoteException {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		filters.add(getFilterMandant()) ;
		filters.addAll(getFilterPartner()) ;
		return filters ;
	}
	
	@Override
	protected List<PartlistEntry> transform(QueryResult result) {
		if(result instanceof StuecklisteQueryResult) {
			prepareTransformer((StuecklisteQueryResult)result) ;
		}
		return super.transform(result);
	}
	
	private void prepareTransformer(StuecklisteQueryResult result) {
		PartlistEntryTransformer transformer = (PartlistEntryTransformer) getTransformer() ;
		transformer.setFlrData(result.getFlrData());
	}
	
	private FilterKriterium getFilterMandant() {
		return new FilterKriterium("stueckliste.mandant_c_nr", true,
				"'" + globalInfo.getMandant() + "'",
				FilterKriterium.OPERATOR_EQUAL, false);	
	}
	
	private List<FilterKriterium> getFilterPartner() throws NamingException, RemoteException {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;

		if(featureFactory.hasCustomerPartlist()) {
			Integer partnerId = featureFactory.getPartnerIdFromAnsprechpartnerId() ;
			Validator.notNullUserMessage(partnerId, 
					"partnerId == null. ansprechpartnerId=" + 
							featureFactory.getPartnerIdFromAnsprechpartnerId().toString());
			filters.add(new FilterKriterium("stueckliste.partner_i_id", true, 
					partnerId.toString(), 
					FilterKriterium.OPERATOR_EQUAL, false)) ;
		}
		
		return filters ;
	}
}
