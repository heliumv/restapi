/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
package com.heliumv.api.production;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IClientCall;
import com.heliumv.factory.IFertigungCallJudge;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.ILagerCall;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IStuecklisteCall;
import com.heliumv.factory.IZeiterfassungCall;
import com.heliumv.factory.query.OffeneAgQuery;
import com.heliumv.factory.query.ProductionQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.LagerDto;
import com.lp.server.artikel.service.SeriennrChargennrMitMengeDto;
import com.lp.server.fertigung.service.FertigungFac;
import com.lp.server.fertigung.service.LosDto;
import com.lp.server.fertigung.service.LosistmaterialDto;
import com.lp.server.fertigung.service.LossollarbeitsplanDto;
import com.lp.server.fertigung.service.LossollmaterialDto;
import com.lp.server.personal.service.MaschineDto;
import com.lp.server.stueckliste.service.MontageartDto;
import com.lp.server.system.service.LocaleFac;
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
	private static Logger log = LoggerFactory.getLogger(ProductionApi.class) ;

	@Autowired
	private IGlobalInfo globalInfo ;
	@Autowired
	private IClientCall clientCall ;	
	@Autowired
	private IArtikelCall artikelCall ;
	@Autowired
	private IParameterCall parameterCall ;
	@Autowired
	private IFertigungCallJudge fertigungCall ;	
	@Autowired
	private ILagerCall lagerCall ;
	@Autowired
	private IStuecklisteCall stuecklisteCall ;
	@Autowired
	private IJudgeCall judgeCall ;
	@Autowired
	private IMandantCall mandantCall ;
	@Autowired
	private ProductionQuery productionQuery ;
	@Autowired
	private OffeneAgQuery offeneAgQuery ;
	@Autowired
	private IZeiterfassungCall zeiterfassungCall ;
	
	@Autowired
	private ModelMapper modelMapper ;
	
//	private ILagerCall lagerCallProxy ;
	
	private ILagerCall getLagerCall() {
		return lagerCall ;
//		if(lagerCallProxy == null) {
//			lagerCallProxy = (ILagerCall) LagerCallProxy.newInstance(lagerCall) ;
//		}
//		return lagerCallProxy ;
	}
	
	@GET
	@Path("/{userid}")
	@Produces({FORMAT_JSON, FORMAT_XML})
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

		try {
			if(!mandantCall.hasModulLos()) {
				respondNotFound() ;
				return productions ;
			}

			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(buildFilterCnr(filterCnr)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;

			QueryParameters params = productionQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;
		
			QueryResult result = productionQuery.setQuery(params) ;
			productions = productionQuery.getResultList(result) ;
		} catch(RemoteException e) {
			respondUnavailable(e);
		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
		return productions ;
	}
	
	
//	@POST
//	@Path("/{productionId}")
////	@Produces({"application/json", "application/xml"})	
//	public void theFunction(
//			@PathParam("productionId") Integer losId,
//			@QueryParam("userId") String userId,
//			@QueryParam("amount") BigDecimal menge) {
//		try {
//			if(connectClient(userId) == null) return ; 
//			
//			LosDto losDto = fertigungCall.losFindByPrimaryKeyOhneExc(losId) ;
//			if(losDto == null) {
//				respondNotFound("productionId", losId.toString()) ;
//				return ;
//			}
//			
//			processMaterialBuchung(losDto, menge) ;
//			processAblieferung(losDto, menge) ;
//		} catch(NamingException e) {
//			e.printStackTrace() ;
//			respondUnavailable(e) ;			
//		} catch(RemoteException e) {
//			e.printStackTrace() ;
//			respondUnavailable(e) ;
//		} catch(EJBExceptionLP e) {
//			respondBadRequest(e) ;
//		}
//	}

	@POST
	@Path("/materialwithdrawal/")
	@Consumes({"application/json", "application/xml"})
	public void bucheMaterialNachtraeglichVomLagerAb(
			@HeaderParam(ParamInHeader.TOKEN) String headerUserId,
			MaterialWithdrawalEntry materialEntry,
			@QueryParam("userId") String userId) {

		ArtikelDto itemDto = null ;
		LagerDto lagerDto = null ;
		
		try {
			if(materialEntry == null) {
				respondBadRequestValueMissing("materialwithdrawal") ;
				return ;
			}
			
			if(StringHelper.isEmpty(materialEntry.getLotCnr())) {
				respondBadRequestValueMissing("lotCnr") ;
				return ;
			}
			if(StringHelper.isEmpty(materialEntry.getItemCnr())) {
				respondBadRequestValueMissing("itemCnr") ;
				return ;
			}
			if(materialEntry.getAmount() == null || materialEntry.getAmount().signum() == 0) {
				respondBadRequest("amount", "null/0") ;
				return ;
			}
			
			if(connectClient(headerUserId, userId) == null) return ;
//			if(!judgeCall.hasFertLosCUD()) {
			if(!mandantCall.hasModulLos()) {
				respondNotFound() ;
				return ;
			}
			
			if(!fertigungCall.darfGebeMaterialNachtraeglichAus()) {
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
				respondBadRequest("montageart", "no value defined") ;
				return ;
			}

			lagerDto = getLager(materialEntry.getStockCnr(), materialEntry.getStockId()) ;
			if(lagerDto == null) {
				return ;				
			}

			if(materialEntry.getAmount().signum() > 0) {
				MaterialRuecknahme ausgabe = new MaterialRuecknahme(losDto.getIId(), lagerDto.getIId(), itemDto) ;
				if(!ausgabe.verifyAmounts(materialEntry.getAmount(), materialEntry.getIdentities())) {
					return ;
				}
				
				gebeMaterialNachtraeglichAus(lagerDto.getIId(), losDto, itemDto, montageartDto, 
					materialEntry.getAmount(), materialEntry.getIdentities()) ;
			} else {
				BigDecimal amountToReturn = materialEntry.getAmount().abs() ;
				
				MaterialRuecknahme ruecknahme = new MaterialRuecknahme(losDto.getIId(), lagerDto.getIId(), itemDto) ;
				ruecknahme.setReturn(materialEntry.getReturn());
				if(!ruecknahme.verifyAmounts(amountToReturn, materialEntry.getIdentities())) {
					return ;
				}
				
				BigDecimal amountNotReturned = ruecknahme.returnItem(
						amountToReturn, materialEntry.getIdentities(), false) ;
				if(amountNotReturned.signum() == 0) {
					amountNotReturned = ruecknahme.returnItem(
						amountToReturn, materialEntry.getIdentities(), true) ;
				}
				
				if(amountNotReturned.signum() != 0) {
					respondBadRequest(EJBExceptionLP.FEHLER_ZUWENIG_AUF_LAGER) ;
					appendBadRequestData("stock-available", amountToReturn.subtract(amountNotReturned).toPlainString()) ;
				}
			}
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
			if(e.getCode() == EJBExceptionLP.FEHLER_ZUWENIG_AUF_LAGER) {
				try {
					BigDecimal lagerStand = getLagerCall().getLagerstandOhneExc(itemDto.getIId(), lagerDto.getIId()) ;
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

		List<SeriennrChargennrMitMengeDto> hvIdentities = null ;
		if(itemDto.istArtikelSnrOderchargentragend()) {
			hvIdentities = transform(identities) ;
//			lossollmaterialDto.setNMenge(BigDecimal.ZERO);
		}
		
		lossollmaterialDto.setNMenge(BigDecimal.ZERO);
		fertigungCall.gebeMaterialNachtraeglichAus(lossollmaterialDto,
				losistmaterialDto, hvIdentities, false) ;
	}
	
	public class MaterialRuecknahme {
		private Integer stockId ;
		private Integer losId ;
		private ArtikelDto itemDto ;
		private boolean isReturn ;
		
		public MaterialRuecknahme(Integer losId, Integer stockId, ArtikelDto itemDto) {
			this.losId = losId ;
			this.stockId = stockId ;
			this.itemDto = itemDto ;
			isReturn = false ;
		}

		/**
		 * Mengen &uuml;berpr&uuml;fen</b>
		 * <p>Die Gesamtsumme der identity.amount muss ident mit der angegebenen Menge sein<p>
		 * <p>Es d&uuml;rfen nur positive Mengen in den identities vorhanden sein.</p>
		 * @param amount
		 * @param identities
		 * @return
		 */
		public boolean verifyAmounts(BigDecimal amount, List<IdentityAmountEntry> identities) {
			if(!itemDto.istArtikelSnrOderchargentragend()) return true ;
			
			BigDecimal amountIdentities = BigDecimal.ZERO ;
			for (IdentityAmountEntry entry : identities) {
				if(entry.getAmount() == null) {
					respondBadRequestValueMissing("amount");
					appendBadRequestData(entry.getIdentity(), "amount missing");
					return false ;
				}
				
				if(entry.getAmount().signum() != 1) {
					respondBadRequest("amount", "positive");
					appendBadRequestData(entry.getIdentity(), entry.getAmount().toPlainString());
					return false ;
				}
				
				amountIdentities = amountIdentities.add(entry.getAmount()) ;
			}
			
			if(amountIdentities.compareTo(amount.abs()) != 0) {
				respondBadRequest("totalamount != identityamount", amount.toPlainString()) ;
				appendBadRequestData("identityamount", amountIdentities.toPlainString()) ;
				return false ;
			}
			
			return true ;
		}
		
		public boolean isReturn() {
			return isReturn ;
		}
		
		public void setReturn(boolean value) {
			isReturn = value ;
		}
		
		public BigDecimal returnItem(BigDecimal amount, List<IdentityAmountEntry> identities,
				boolean updateDb) throws NamingException, RemoteException {
			LossollmaterialDto[] sollDtos = fertigungCall.lossollmaterialFindByLosIIdOrderByISort(getLosId()) ;
			if(sollDtos.length < 1) return amount ;
			
			List<IdentityAmountEntry> workIdentities = cloneIdentities(identities);

			int startIndex = sollDtos.length ;
			while(startIndex != -1 && amount.signum() != 0) {
				startIndex = findAcceptableLossollmaterialDtoIndex(sollDtos, getItemDto().getIId(), amount, startIndex) ;
				if(-1 == startIndex) break ;
				
				LosistmaterialDto[] istDtos = fertigungCall.losistmaterialFindByLossollmaterialIId(sollDtos[startIndex].getIId()) ;
				for(int i = 0 ; i < istDtos.length && amount.signum() > 0; i++) {
					if(!istDtos[i].isAbgang()) continue ;

					BigDecimal amountToRemove = amount.min(istDtos[i].getNMenge().abs()) ;
					if(amountToRemove.signum() <= 0) continue ;

					if(getItemDto().istArtikelSnrOderchargentragend()) {
						amountToRemove = calculateAmountToRemove(
								amountToRemove, istDtos[i], sollDtos[startIndex], workIdentities, updateDb) ;
					}
					
					BigDecimal newAmount = istDtos[i].getNMenge().subtract(amountToRemove) ;
					if(updateDb && !getItemDto().istArtikelSnrOderchargentragend()) {
						if(isReturn()) {
							fertigungCall.updateLossollmaterialMenge(sollDtos[startIndex].getIId(), newAmount) ;
						}
						fertigungCall.updateLosistmaterialMenge(istDtos[i].getIId(), newAmount) ;
					}

					amount = amount.subtract(amountToRemove) ;
				}
			}
			
			return amount ;
		}
	
		protected List<IdentityAmountEntry> cloneIdentities(List<IdentityAmountEntry> identities) {
			if(identities == null) return null ;
			
			List<IdentityAmountEntry> workIdenties = new ArrayList<IdentityAmountEntry>() ;
			for (IdentityAmountEntry identity : identities) {
				IdentityAmountEntry workEntry = new IdentityAmountEntry();
				workEntry.setAmount(BigDecimal.ZERO.add(identity.getAmount()));
				workEntry.setIdentity("" + identity.getIdentity()) ;
				workIdenties.add(workEntry) ;
			}
			return workIdenties ;
		}
	
		protected int findAcceptableLossollmaterialDtoIndex(LossollmaterialDto[] sollDtos,
				Integer itemId, BigDecimal amount, int startIndex) {
			if(startIndex > sollDtos.length || startIndex <= 0) return -1 ;

			for(int i = startIndex - 1; i >= 0; i--) {
				if(itemId.equals(sollDtos[i].getArtikelIId())) {
					return i ;
				}
			}

			return -1 ;
		}
	
		protected BigDecimal calculateAmountToRemove(BigDecimal amountToRemove, 
				LosistmaterialDto istMaterialDto, LossollmaterialDto sollMaterialDto, List<IdentityAmountEntry> workIdentities, boolean updateDb) throws NamingException, RemoteException {

			List<SeriennrChargennrMitMengeDto> allSnrs = lagerCall
					.getAllSeriennrchargennrEinerBelegartposition(
							LocaleFac.BELEGART_LOS, istMaterialDto.getIId());
			System.out.println("Snrs: " + allSnrs.size());

			BigDecimal amountSnr = BigDecimal.ZERO;
			for (SeriennrChargennrMitMengeDto snr : allSnrs) {
				for (IdentityAmountEntry workEntry : workIdentities) {
					if (snr.getCSeriennrChargennr().compareTo(workEntry.getIdentity()) == 0) {
						BigDecimal snrAvailableAmount = amountToRemove
								.min(workEntry.getAmount());
						if (snrAvailableAmount.signum() > 0) {

							System.out.println("Snr: " + workEntry.getIdentity()
									+ " amount: " + workEntry.getAmount());

							workEntry.setAmount(workEntry.getAmount().subtract(
									snrAvailableAmount));
							amountSnr = amountSnr.add(snrAvailableAmount);

							if (updateDb) {
								bucheZu(sollMaterialDto.getIId(), amountSnr, workEntry.getIdentity()) ;
							}
						}
					}
				}
			}

			return amountSnr ;
		}
		
		protected void bucheZu(Integer sollmaterialId, BigDecimal amount, String identity) throws RemoteException, NamingException, EJBExceptionLP {
	
			LosistmaterialDto istmaterialDto = new LosistmaterialDto() ;
			istmaterialDto.setBAbgang(Helper.boolean2Short(false)) ;
			istmaterialDto.setLagerIId(getStockId());
			istmaterialDto.setLossollmaterialIId(sollmaterialId) ;
			istmaterialDto.setNMenge(amount);

			fertigungCall.createLosistmaterial(istmaterialDto, identity) ;
		}
		
		protected Integer getStockId() {
			return stockId ;
		}
		protected Integer getLosId() {
			return losId ;
		}
		protected ArtikelDto getItemDto() {
			return itemDto ;
		}
	}
		
	private List<SeriennrChargennrMitMengeDto> transform(List<IdentityAmountEntry> identities) {
		List<SeriennrChargennrMitMengeDto> hvIdentities = new ArrayList<SeriennrChargennrMitMengeDto>() ;
		if(identities == null) return null ;

		for (IdentityAmountEntry identity : identities) {
			SeriennrChargennrMitMengeDto snrDto = new SeriennrChargennrMitMengeDto() ;
			snrDto.setCSeriennrChargennr(identity.getIdentity()) ;
			snrDto.setNMenge(identity.getAmount()) ;
			hvIdentities.add(snrDto) ;
		}
		
		return hvIdentities.size() == 0 ? null : hvIdentities ;
	}
	
 	private MontageartDto getMontageart() throws NamingException, RemoteException, EJBExceptionLP {
		MontageartDto[] montagearts = stuecklisteCall.montageartFindByMandantCNr() ;
		return montagearts.length > 0 ? montagearts[0] : null ;
	}
	
 	private LagerDto getLager(String stockCnr, Integer stockId) throws NamingException, RemoteException {
 		LagerDto lagerDto = null ;
 		if(stockId != null) {
 			lagerDto = getLagerCall().lagerFindByPrimaryKeyOhneExc(stockId) ;
 			if(lagerDto == null) {
 				respondNotFound("stockId", stockId.toString()) ;
 				return null ;
 			}
 			
 			return lagerDto ;
  		}

		if(StringHelper.isEmpty(stockCnr)) {
			respondBadRequestValueMissing("stockCnr") ;
			return null ;
		}
		
		lagerDto = getLagerCall().lagerFindByCnrOhnExc(stockCnr) ; 		
		if(lagerDto == null) {
			respondNotFound("stockCnr", stockCnr);
		}

		return lagerDto ;
 	}
 	
	
	private BigDecimal getSollPreis(Integer itemId, Integer stockId) throws NamingException, RemoteException {
		return getLagerCall().getGemittelterGestehungspreisEinesLagers(itemId, stockId);		
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
	
//	private LagerDto findLagerDtoById(Integer stockId) throws NamingException {
//		return getLagerCall().lagerFindByPrimaryKeyOhneExc(stockId) ;
//	}
//	
//	private void processMaterialBuchung(LosDto losDto, BigDecimal menge) throws RemoteException, NamingException {
//		if(parameterCall.isKeineAutomatischeMaterialbuchung()) return ;
//		
//		if(parameterCall.isBeiLosErledigenMaterialNachbuchen()) {
//			fertigungCall.bucheMaterialAufLos(losDto, menge) ;
//		} else {
//			if(losDto.getStuecklisteIId() == null) return ; 
//			
//			StuecklisteDto stklDto = stuecklisteCall.stuecklisteFindByPrimaryKey(losDto.getStuecklisteIId()) ;
//			if(!stklDto.isMaterialbuchungbeiablieferung()) return ;
//
//			fertigungCall.bucheMaterialAufLos(losDto, menge) ;
//		}
//	}
//	
//	
//	private void processAblieferung(LosDto losDto, BigDecimal menge) throws NamingException, RemoteException {
//		boolean isLosErledigen = judgeCall.hasFertDarfLosErledigen() ;
//		
//		LosablieferungDto losablieferungDto = new LosablieferungDto() ;
//		losablieferungDto.setLosIId(losDto.getIId()) ;
//		losablieferungDto.setNMenge(menge) ;
//		fertigungCall.createLosablieferung(losablieferungDto, isLosErledigen) ;
//	}
	
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
	
//	private FilterKriterium buildFilterItemNumber(String cnr) throws NamingException, RemoteException {
//		if(cnr == null || cnr.trim().length() == 0) return null ;
//
//		int itemLengthAllowed = parameterCall.getMaximaleLaengeArtikelnummer() ;
//		
//		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
//				"flrlos." + FertigungFac.FLR_LOS_FLRSTUECKLISTE + "."
//				+ StuecklisteFac.FLR_STUECKLISTE_FLRARTIKEL + ".c_nr", StringHelper.removeSqlDelimiters(cnr),
//				FilterKriterium.OPERATOR_LIKE, "",
//				FilterKriteriumDirekt.PROZENT_TRAILING, // Auswertung als 'XX%'
//				true, // wrapWithSingleQuotes
//				true, itemLengthAllowed);		
//		fk.wrapWithProzent() ;
//		fk.wrapWithSingleQuotes() ;
//		return fk ;
//	}
	
	@GET
	@Path("/openwork")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public OpenWorkEntryList getOpenWorkEntries(
			@QueryParam("userid") String userId,
			@QueryParam("limit") Integer limit,
			@QueryParam("startIndex") Integer startIndex,
			@QueryParam("filter_cnr") String filterCnr,
			@QueryParam("filter_customer") String filterCustomer, 
			@QueryParam("filter_itemcnr") String filterItemCnr,
			@QueryParam("filter_startdate") Long startDateMs,
			@QueryParam("filter_enddate") Long endDateMs) throws RemoteException, NamingException, EJBExceptionLP {
		if(connectClient(userId) == null) return new OpenWorkEntryList() ;

		return getOpenWorkEntriesImpl(limit, startIndex, startDateMs, endDateMs);
	}

	public OpenWorkEntryList getOpenWorkEntriesImpl(Integer limit,
			Integer startIndex, Long startDateMs, Long endDateMs) throws NamingException, RemoteException {
		OpenWorkEntryList entries = new OpenWorkEntryList() ;
	
		if(!mandantCall.hasModulLos()) {
			respondNotFound() ;
			return entries ;
		}

		FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
//			collector.add(buildFilterCnr(filterCnr)) ;
		if(startDateMs != null) {
			collector.add(offeneAgQuery.getFilterBeginnDatum(new Date(startDateMs))) ; 
		}
		if(endDateMs != null) {
			collector.add(offeneAgQuery.getFilterEndeDatum(new Date(endDateMs))) ; 			
		}
		FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;

		QueryParameters params = offeneAgQuery.getDefaultQueryParameters(filterCrits) ;
		params.setLimit(limit) ;
		params.setKeyOfSelectedRow(startIndex) ;
	
		log.debug("Query offeneAg started");
		QueryResult result = offeneAgQuery.setQuery(params) ;
		entries.setEntries(offeneAgQuery.getResultList(result)) ;
		log.debug("Query offeneAg done");
		
		return entries ;
	}	
	
	@PUT
	@Path("/openwork")
	@Consumes({FORMAT_JSON, FORMAT_XML})
	public void updateOpenWorkEntry(
			@HeaderParam(ParamInHeader.TOKEN) String headerUserId,
			OpenWorkUpdateEntry updateEntry,
			@QueryParam(Param.USERID) String userId
	) throws NamingException, RemoteException, EJBExceptionLP {
		if(updateEntry == null) {
			respondBadRequestValueMissing("updateEntry") ;
			return ;
		}

		if(connectClient(headerUserId, userId) == null) return ;
		
		updateOpenWorkEntryImpl(updateEntry);
	}

	@PUT
	@Path("/openworklist")
	@Consumes({FORMAT_JSON, FORMAT_XML})
	public void updateOpenWorkEntryList(
			@HeaderParam(ParamInHeader.TOKEN) String headerUserId,
			OpenWorkUpdateEntryList updateList,
			@QueryParam(Param.USERID) String userId
	) throws NamingException, RemoteException, EJBExceptionLP {
		if(updateList == null) {
			respondBadRequestValueMissing("updateList") ;
			return ;
		}
		
		if(updateList.getEntries() == null) {
			respondBadRequestValueMissing("updateList.entries") ;
			return ;			
		}
		
		for (OpenWorkUpdateEntry updateEntry : updateList.getEntries()) {
			if(!updateOpenWorkEntryImpl(updateEntry)) {
				return ;
			}
		}
	}

	
	private boolean updateOpenWorkEntryImpl(OpenWorkUpdateEntry updateEntry)
			throws NamingException, RemoteException {
		if(updateEntry.getId() == null) {
			respondBadRequestValueMissing("updateEntry.id") ;
			return false ;			
		}

		if(StringHelper.isEmpty(updateEntry.getProductionCnr())) {
			respondBadRequestValueMissing("updateEntry.lotCnr");
			return false ;
		}

		LosDto losDto = fertigungCall
				.losFindByCNrMandantCNrOhneExc(updateEntry.getProductionCnr()) ;
		if(losDto == null) {
			respondNotFound("updateEntry.lotCnr", updateEntry.getProductionCnr());
			return false ;
		}
		LossollarbeitsplanDto arbeitsplanDto = fertigungCall
				.lossollarbeitsplanFindByPrimaryKey(updateEntry.getId()) ;
		// Minimale Absicherung gegen Versuche die Id "frei" zu ermitteln
		if(!arbeitsplanDto.getLosIId().equals(losDto.getIId())) {
			respondNotFound("updateEntry.id", updateEntry.getId().toString());
			return false ;
		}
		
		MaschineDto maschineDto = null ;
		if(updateEntry.getMachineId() != null) {
			maschineDto = zeiterfassungCall.maschineFindByPrimaryKey(updateEntry.getMachineId()) ;
		}
		
		Calendar beginCal = Calendar.getInstance() ;
		beginCal.setTime(losDto.getTProduktionsbeginn());

		Calendar newCal = Calendar.getInstance() ;
		newCal.setTimeInMillis(updateEntry.getWorkItemStartDate());
		newCal.set(Calendar.HOUR_OF_DAY, 0);
		newCal.set(Calendar.MINUTE, 0) ;
		newCal.set(Calendar.SECOND, 0) ;
		newCal.set(Calendar.MILLISECOND, 0) ;
		
		if(newCal.compareTo(beginCal) < 0) {
			respondBadRequest("updateEntry.workItemStartDate", 
					newCal.toString() + " < production.startDate" );
			return false ;
		}

		int dayDiff = daysBetween(beginCal, newCal) ;
		arbeitsplanDto.setIMaschinenversatztage(dayDiff) ;
		if(maschineDto != null) {
			arbeitsplanDto.setMaschineIId(maschineDto.getIId()); 
		}
		if(updateEntry.getMachineOffsetMs() != null) {
			arbeitsplanDto.setIMaschinenversatzMs(updateEntry.getMachineOffsetMs()) ;
		} else {
			arbeitsplanDto.setIMaschinenversatzMs(0) ;
		}
		
		fertigungCall.updateLossollarbeitsplan(arbeitsplanDto) ;
		return true ;
	}
	

	private int daysBetween(Calendar startDate, Calendar endDate) {  
		Calendar date = (Calendar) startDate.clone();
		int daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}
}

