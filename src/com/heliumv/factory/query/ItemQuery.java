package com.heliumv.factory.query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.api.item.ItemEntryTransformer;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.ISystemCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class ItemQuery extends BaseQuery<ItemEntry> {
	@Autowired
	private IParameterCall parameterCall ;
	
	@Autowired
	private IMandantCall mandantCall ;
	
	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Autowired
	private ISystemCall systemCall ;
	
	public ItemQuery() {
		super(QueryParameters.UC_ID_ARTIKELLISTE) ;
		setTransformer(new ItemEntryTransformer()) ;
	}
	
	public ItemQuery(IParameterCall parameterCall) throws NamingException {
		super(UUID.randomUUID().toString(), QueryParameters.UC_ID_ARTIKELLISTE) ;
		setTransformer(new ItemEntryTransformer()) ;
		this.parameterCall = parameterCall ;
	}

//	public List<ItemEntry> getResultList(QueryResult result) {
//		try {
//			getTableInfo() ; 
//		} catch(NamingException e) {
//
//		} catch(RemoteException e) {
//		}
//
//		return super.getResultList(result) ;
//	}
	
	@Override
	protected List<FilterKriterium> getRequiredFilters() {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		filters.add(getFilterKeineHandeingabe()) ;
		filters.add(getFilterMandant()) ;		
		return filters ;
	}
	
	private FilterKriterium getFilterKeineHandeingabe() {
		return new FilterKriterium(
				"artikelliste."
				+ ArtikelFac.FLR_ARTIKEL_ARTIKELART_C_NR, true, "('"
				+ ArtikelFac.ARTIKELART_HANDARTIKEL + "')",
				FilterKriterium.OPERATOR_NOT_IN, false) ;
	}
	
	
	private FilterKriterium getFilterMandant() {
		String mandant = globalInfo.getMandant() ;
		try {
			if (mandantCall.hasFunctionZentralerArtikelstamm()) {
				mandant = systemCall.getHauptmandant() ;
			}

			return new FilterKriterium("artikelliste.mandant_c_nr",
						true, StringHelper.asSqlString(mandant),
						FilterKriterium.OPERATOR_EQUAL, false) ;
		} catch(NamingException e) {
			// TODO: getDefaultFilters auf throws NamingException umstellen
			return null ;
		}
	}
}
