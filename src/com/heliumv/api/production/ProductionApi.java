package com.heliumv.api.production;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IClientCall;
import com.heliumv.factory.IFertigungCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.ILagerCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IStuecklisteCall;
import com.heliumv.factory.query.ProductionQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.LagerDto;
import com.lp.server.artikel.service.SeriennrChargennrMitMengeDto;
import com.lp.server.fertigung.service.FertigungFac;
import com.lp.server.fertigung.service.LosDto;
import com.lp.server.fertigung.service.LosablieferungDto;
import com.lp.server.fertigung.service.LosistmaterialDto;
import com.lp.server.fertigung.service.LoslagerentnahmeDto;
import com.lp.server.fertigung.service.LossollmaterialDto;
import com.lp.server.stueckliste.service.MontageartDto;
import com.lp.server.stueckliste.service.StuecklisteDto;
import com.lp.server.stueckliste.service.StuecklisteFac;
import com.lp.server.util.Facade;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.util.EJBExceptionLP;
import com.lp.util.Helper;

@Service("hvProduction")
@Path("/api/v1/production/")
public class ProductionApi extends BaseApi implements IProductionApi {
	@Autowired
	private IGlobalInfo globalInfo ;
	@Autowired
	private IClientCall clientCall ;	
	@Autowired
	private IArtikelCall artikelCall ;
	@Autowired
	private IParameterCall parameterCall ;
	@Autowired
	private IFertigungCall fertigungCall ;
	@Autowired
	private ILagerCall lagerCall ;
	
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

		if(connectClient(userId) == null)  return productions ;

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
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
	}

	@POST
	@Path("/materialwithdrawal/")
	@Consumes({"application/json", "application/xml"})
	public void bucheMaterialNachtraeglichVomLagerAb(
			MaterialWithdrawalEntry materialEntry,
			@QueryParam("userId") String userId) {

		ArtikelDto itemDto = null ;
		LagerDto lagerDto = null ;
		
		try {
			if(materialEntry == null) {
				respondBadRequest("materialwithdrawal", "null") ;
				return ;
			}
			
			if(StringHelper.isEmpty(materialEntry.getLotCnr())) {
				respondBadRequest("lotCnr", materialEntry.getLotCnr()) ;
				return ;
			}
			if(StringHelper.isEmpty(materialEntry.getItemCnr())) {
				respondBadRequest("itemCnr", materialEntry.getItemCnr()) ;
				return ;
			}
			if(materialEntry.getAmount() == null || materialEntry.getAmount().signum() == 0) {
				respondBadRequest("amount", "null/0") ;
				return ;
			}
			
			if(connectClient(userId) == null) return ;
			if(!judgeCall.hasFertLosCUD()) {
				respondUnauthorized() ;
				return ;
			}
			
			LosDto losDto = findLosByCnr(materialEntry.getLotCnr()) ;
			if(losDto == null) {
				respondNotFound("lotCnr", materialEntry.getLotCnr()) ;
				return ;
			}
			
			if(!isValidLosState(losDto)) return ;
			
			itemDto = findItemByCnr(materialEntry.getItemCnr()) ;
			if(itemDto == null) {
				respondNotFound("itemCnr", materialEntry.getItemCnr()) ;
				return ;
			}
			
			MontageartDto montageartDto = getMontageart() ;
			if(montageartDto == null) {
				respondBadRequest("", "") ;
				return ;
			}

			lagerDto = getLager(losDto, itemDto) ;
			if(lagerDto == null) {
				respondBadRequest("stock", "") ;
				return ;				
			}

			if(materialEntry.getAmount().signum() > 0) {
				gebeMaterialNachtraeglichAus(lagerDto.getIId(), losDto, itemDto, montageartDto, 
					materialEntry.getAmount(), materialEntry.getIdentities()) ;
			} else {
				BigDecimal amountToReturn = materialEntry.getAmount().abs() ;
				BigDecimal amountNotReturned = nimmMaterialZurueck(
						lagerDto.getIId(), losDto, itemDto, amountToReturn, false) ;
				if(amountNotReturned.signum() == 0) {
					amountNotReturned = nimmMaterialZurueck(
							lagerDto.getIId(), losDto, itemDto, amountToReturn, true) ;
				}
				
				if(amountNotReturned.signum() != 0) {
					respondBadRequest(EJBExceptionLP.FEHLER_ZUWENIG_AUF_LAGER) ;
					appendBadRequestData("stock-available", amountToReturn.subtract(amountNotReturned).toPlainString()) ;
				}
			}
		} catch(NamingException e) {
			respondUnavailable(e) ;
			e.printStackTrace() ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
			e.printStackTrace() ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
			if(e.getCode() == EJBExceptionLP.FEHLER_ZUWENIG_AUF_LAGER) {
				try {
					BigDecimal lagerStand = lagerCall.getLagerstandOhneExc(itemDto.getIId(), lagerDto.getIId()) ;
					appendBadRequestData("stock-available", lagerStand.toPlainString()) ;
				} catch(NamingException n) {
					respondUnavailable(n) ;
				} catch(RemoteException r) {
					respondUnavailable(r) ;
				}
			}
		}
	}
	
	private void gebeMaterialNachtraeglichAus(Integer stockId, LosDto losDto,
			ArtikelDto itemDto, MontageartDto montageartDto, BigDecimal amount, List<IdentityAmountEntry> identities) 
		throws NamingException, RemoteException, EJBExceptionLP {
		BigDecimal sollPreis = getSollPreis(itemDto.getIId(), stockId) ;

		LossollmaterialDto lossollmaterialDto = new LossollmaterialDto() ;
		lossollmaterialDto.setBNachtraeglich(Helper.boolean2Short(true)) ;
		lossollmaterialDto.setArtikelIId(itemDto.getIId()) ;
		lossollmaterialDto.setEinheitCNr(itemDto.getEinheitCNr()) ;
		lossollmaterialDto.setLosIId(losDto.getIId()) ;
		lossollmaterialDto.setMontageartIId(montageartDto.getIId());
		lossollmaterialDto.setNMenge(amount);
		lossollmaterialDto.setNSollpreis(sollPreis);
		
		LosistmaterialDto losistmaterialDto = createLosistmaterialDto(stockId, amount) ;

		fertigungCall.gebeMaterialNachtraeglichAus(lossollmaterialDto,
				losistmaterialDto, transform(identities), false) ;
	}
	
	private BigDecimal nimmMaterialZurueck(Integer stockId, LosDto losDto, 
			ArtikelDto itemDto, BigDecimal amount, boolean updateDb) throws NamingException, RemoteException, EJBExceptionLP {
		LossollmaterialDto[] sollDtos = fertigungCall.lossollmaterialFindByLosIIdOrderByISort(losDto.getIId()) ;
		if(sollDtos.length < 1) return amount ;
		
		int startIndex = sollDtos.length ;
		while(startIndex != -1 && amount.signum() != 0) {
			startIndex = findAcceptableLossollmaterialDtoIndex(sollDtos, itemDto.getIId(), amount, startIndex) ;
			if(-1 == startIndex) break ;

			LosistmaterialDto[] istDtos = fertigungCall.losistmaterialFindByLossollmaterialIId(sollDtos[startIndex].getIId()) ;
			for(int i = 0 ; i < istDtos.length && amount.signum() > 0; i++) {
				if(istDtos[i].isAbgang()) {
					BigDecimal amountToRemove = amount.min(istDtos[i].getNMenge().abs()) ;
					if(amountToRemove.signum() > 0) {
						BigDecimal newAmount = istDtos[i].getNMenge().subtract(amountToRemove) ;
						if(updateDb) {
							fertigungCall.updateLosistmaterialMenge(istDtos[i].getIId(), newAmount) ;
						}
						amount = amount.subtract(amountToRemove) ;
					}
				}
			}
		}
		
		return amount ;
	}
	
	private int findAcceptableLossollmaterialDtoIndex(LossollmaterialDto[] sollDtos,
			Integer itemId, BigDecimal amount, int startIndex) {
		if(startIndex > sollDtos.length || startIndex <= 0) return -1 ;

		for(int i = startIndex - 1; i >= 0; i--) {
			if(itemId.equals(sollDtos[i].getArtikelIId())) {
				return i ;
			}
		}

		return -1 ;
	}
	
	private List<SeriennrChargennrMitMengeDto> transform(List<IdentityAmountEntry> identities) {
		List<SeriennrChargennrMitMengeDto> hvIdentities = new ArrayList<SeriennrChargennrMitMengeDto>() ;
		if(identities == null) return null ;

		for (IdentityAmountEntry identity : identities) {
			SeriennrChargennrMitMengeDto snrDto = new SeriennrChargennrMitMengeDto() ;
			snrDto.setCSeriennrChargennr(identity.getIdentiy()) ;
			snrDto.setNMenge(identity.getAmount()) ;
			hvIdentities.add(snrDto) ;
		}
		
		return hvIdentities.size() == 0 ? null : hvIdentities ;
	}
	
 	private MontageartDto getMontageart() throws NamingException, RemoteException, EJBExceptionLP {
		MontageartDto[] montagearts = stuecklisteCall.montageartFindByMandantCNr() ;
		return montagearts.length > 0 ? montagearts[0] : null ;
	}
	
	private LagerDto getLager(LosDto losDto, ArtikelDto itemDto) throws NamingException, RemoteException {
		LagerDto lagerDto = null ;
		LoslagerentnahmeDto[] laeger = fertigungCall.loslagerentnahmeFindByLosIId(losDto.getIId()) ;
		if (laeger.length > 0) {
			lagerDto = findLagerDtoById(laeger[0].getLagerIId());
		}
		
		return lagerDto ;
	}
	
	private BigDecimal getSollPreis(Integer itemId, Integer stockId) throws NamingException, RemoteException {
		return lagerCall.getGemittelterGestehungspreisEinesLagers(itemId, stockId);		
	}
	
	
	private LosistmaterialDto createLosistmaterialDto(Integer stockId, BigDecimal amount) {
		LosistmaterialDto losistmaterialDto = new LosistmaterialDto();
		losistmaterialDto.setLagerIId(stockId);
		losistmaterialDto.setBAbgang(new Short((short) (amount.signum() > 0 ? 1 : 0))) ;
		losistmaterialDto.setNMenge(amount);
		return losistmaterialDto ;
	}
	
	private boolean isValidLosState(LosDto losDto) {
		if(FertigungFac.STATUS_STORNIERT.equals(losDto.getStatusCNr())) {
			respondBadRequest(EJBExceptionLP.FEHLER_FERTIGUNG_DAS_LOS_IST_STORNIERT) ;
			return false ;
		}

		if(FertigungFac.STATUS_ANGELEGT.equals(losDto.getStatusCNr())) {
			respondBadRequest(EJBExceptionLP.FEHLER_FERTIGUNG_DAS_LOS_IST_NOCH_NICHT_AUSGEGEBEN) ;
			return false ;
		}
		
		if(FertigungFac.STATUS_ERLEDIGT.equals(losDto.getStatusCNr())) {
			respondBadRequest(EJBExceptionLP.FEHLER_FERTIGUNG_DAS_LOS_IST_BEREITS_ERLEDIGT) ;
			return false ;
		}
		
		return true ;
	}
	
	private LosDto findLosByCnr(String cnr) throws NamingException {
		return fertigungCall.losFindByCNrMandantCNrOhneExc(cnr) ;
	}
	
	private ArtikelDto findItemByCnr(String cnr) throws RemoteException, NamingException {
		return artikelCall.artikelFindByCNrOhneExc(cnr) ;
	}
	
	private LagerDto findLagerDtoById(Integer stockId) throws NamingException {
		return lagerCall.lagerFindByPrimaryKeyOhneExc(stockId) ;
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
		boolean isLosErledigen = judgeCall.hasFertDarfLosErledigen() ;
		
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
