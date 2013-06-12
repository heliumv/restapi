package com.heliumv.api.production;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.Globals;
import com.heliumv.factory.IClientCall;
import com.heliumv.factory.IFertigungCall;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IStuecklisteCall;
import com.heliumv.factory.impl.ProductionQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.fertigung.service.FertigungFac;
import com.lp.server.fertigung.service.LosDto;
import com.lp.server.fertigung.service.LosablieferungDto;
import com.lp.server.stueckliste.service.StuecklisteDto;
import com.lp.server.stueckliste.service.StuecklisteFac;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;

@Service("hvProduction")
@Path("/api/v1/production/")
public class ProductionApi extends BaseApi implements IProductionApi {
	@Autowired
	private IClientCall clientCall ;
	@Autowired
	private IParameterCall parameterCall ;
	@Autowired
	private IFertigungCall fertigungCall ;
	@Autowired
	private IStuecklisteCall stuecklisteCall ;
	@Autowired
	private IJudgeCall judgeCall ;
	
	@GET
	@Path("/{userid}")
	@Produces({"application/json", "application/xml"})
	public List<ProductionEntry> getProductions(
			@PathParam("userid") String userId,
			@QueryParam("limit") Integer limit,
			@QueryParam("startIndex") Integer startIndex,
			@QueryParam("filter_cnr") String filterCnr,
			@QueryParam("filter_customer") String filterCustomer, 
			@QueryParam("filter_project") String filterProject,
			@QueryParam("filter_itemcnr") String filterItemCnr,
			@QueryParam("filter_withHidden") Boolean filterWithHidden) {
		List<ProductionEntry> productions = new ArrayList<ProductionEntry>() ;

		if(connectClient(userId) == null) {
			respondUnauthorized() ;
			return productions ;
		}

		FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
		collector.add(buildFilterCnr(filterCnr)) ;
//		collector.add(buildFilterCompanyName(filterCompany)) ;
		FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;

		try {
			ProductionQuery query = new ProductionQuery(parameterCall) ;			
			QueryParameters params = query.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
		
			QueryResult result = query.setQuery(params) ;
			productions = query.getResultList(result) ;
		} catch(NamingException e) {
			e.printStackTrace() ;
		}
		return productions ;
	}
	
	
	@POST
	@Path("/{productionId}")
//	@Produces({"application/json", "application/xml"})	
	public void theFunction(
			@PathParam("productionId") Integer losId,
			@QueryParam("userId") String userId,
			@QueryParam("amount") BigDecimal menge) {
		try {
			if(connectClient(userId) == null) return ; 
			
			LosDto losDto = fertigungCall.losFindByPrimaryKeyOhneExc(losId) ;
			if(losDto == null) {
				respondNotFound("productionId", losId.toString()) ;
				return ;
			}
			
			processMaterialBuchung(losDto, menge) ;
			processAblieferung(losDto, menge) ;
		} catch(NamingException e) {
			e.printStackTrace() ;
			respondUnavailable(e) ;			
		} catch(RemoteException e) {
			e.printStackTrace() ;
			respondUnavailable(e) ;
		}
	}

	
	private void processMaterialBuchung(LosDto losDto, BigDecimal menge) throws RemoteException, NamingException {
		if(parameterCall.isKeineAutomatischeMaterialbuchung()) return ;
		
		if(parameterCall.isBeiLosErledigenMaterialNachbuchen()) {
			fertigungCall.bucheMaterialAufLos(losDto, menge) ;
		} else {
			if(losDto.getStuecklisteIId() == null) return ; 
			
			StuecklisteDto stklDto = stuecklisteCall.stuecklisteFindByPrimaryKey(losDto.getStuecklisteIId()) ;
			if(!stklDto.isMaterialbuchungbeiablieferung()) return ;

			fertigungCall.bucheMaterialAufLos(losDto, menge) ;
		}
	}
	
	
	private void processAblieferung(LosDto losDto, BigDecimal menge) throws NamingException, RemoteException {
		boolean isLosErledigen = judgeCall.hasFertDarfLosErledigen(Globals.getTheClientDto()) ;
		
		LosablieferungDto losablieferungDto = new LosablieferungDto() ;
		losablieferungDto.setLosIId(losDto.getIId()) ;
		losablieferungDto.setNMenge(menge) ;
		fertigungCall.createLosablieferung(losablieferungDto, isLosErledigen) ;
	}
	
	private FilterKriterium buildFilterCnr(String cnr) {
		if(cnr == null || cnr.trim().length() == 0) return null ;
	
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"flrlos." + FertigungFac.FLR_LOS_C_NR, StringHelper.removeSqlDelimiters(cnr), 
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_LEADING, // Auswertung als '%XX'
				true, // wrapWithSingleQuotes
				false, Facade.MAX_UNBESCHRAENKT);		
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	private FilterKriterium buildFilterItemNumber(String cnr) throws NamingException, RemoteException {
		if(cnr == null || cnr.trim().length() == 0) return null ;

		int itemLengthAllowed = parameterCall.getMaximaleLaengeArtikelnummer() ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"flrlos." + FertigungFac.FLR_LOS_FLRSTUECKLISTE + "."
				+ StuecklisteFac.FLR_STUECKLISTE_FLRARTIKEL + ".c_nr", StringHelper.removeSqlDelimiters(cnr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING, // Auswertung als 'XX%'
				true, // wrapWithSingleQuotes
				true, itemLengthAllowed);		
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
}
