package com.heliumv.factory.query;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.api.worktime.ItemEntryTransformer;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.ISystemCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;

public class ArtikelArbeitszeitQuery extends BaseQuery<ItemEntry> {
	@Autowired 
	private IMandantCall mandantCall ;
	@Autowired
	private ISystemCall systemCall ;
	@Autowired
	private IParameterCall parameterCall ;

	public ArtikelArbeitszeitQuery() {
		super(QueryParameters.UC_ID_ARTIKELLISTE) ;
		setTransformer(new ItemEntryTransformer()) ;
	}
	
	
	@Override
	protected List<FilterKriterium> getRequiredFilters() {
		List<FilterKriterium> filters = new ArrayList<FilterKriterium>() ;
		
		filters.add(getArbeitszeitArtikelFilter()) ;
		filters.add(getArtikelMandantFilter()) ;	
		return filters ;
	}

	private FilterKriterium getArbeitszeitArtikelFilter() {
		return new FilterKriterium("artikelliste."
				+ ArtikelFac.FLR_ARTIKEL_ARTIKELART_C_NR, true,
				StringHelper.asSqlString(ArtikelFac.ARTIKELART_ARBEITSZEIT),
				FilterKriterium.OPERATOR_EQUAL, false) ;
	}
	
	private FilterKriterium getArtikelMandantFilter() {
		String mandant = Globals.getTheClientDto().getMandant() ;
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
	
	public FilterKriterium getFilterArtikelNummer(String filterCnr) throws NamingException, RemoteException {
		if(filterCnr == null || filterCnr.trim().length() == 0) return null ;
		
		int maxLength = parameterCall.getMaximaleLaengeArtikelnummer() ;

		FilterKriteriumDirekt fk = new FilterKriteriumDirekt("artikelliste.c_nr", 
				StringHelper.removeSqlDelimiters(filterCnr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING, true, true, maxLength);
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;		
	}
}
