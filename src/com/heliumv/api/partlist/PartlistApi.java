package com.heliumv.api.partlist;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import net.sf.jasperreports.engine.JRExporterParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IArtikelCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IStuecklisteCall;
import com.heliumv.factory.IStuecklisteReportCall;
import com.heliumv.factory.query.PartlistPositionQuery;
import com.heliumv.factory.query.PartlistQuery;
import com.heliumv.feature.FeatureFactory;
import com.heliumv.session.HvSessionManager;
import com.heliumv.tools.FilterHelper;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.client.frame.HelperClient;
import com.lp.server.artikel.service.ArtikelDto;
import com.lp.server.artikel.service.ArtikelFac;
import com.lp.server.stueckliste.service.KundenStuecklistepositionDto;
import com.lp.server.stueckliste.service.MontageartDto;
import com.lp.server.stueckliste.service.StuecklisteDto;
import com.lp.server.stueckliste.service.StuecklisteFac;
import com.lp.server.stueckliste.service.StuecklisteHandlerFeature;
import com.lp.server.stueckliste.service.StuecklisteQueryResult;
import com.lp.server.stueckliste.service.StuecklisteReportFac;
import com.lp.server.stueckliste.service.StuecklistepositionDto;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.FilterKriterium;
import com.lp.server.util.fastlanereader.service.query.FilterKriteriumDirekt;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryParametersFeatures;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.server.util.report.JasperPrintLP;
import com.lp.util.EJBExceptionLP;
import com.lp.util.HVPDFExporter;
import com.lp.util.Helper;

@Service("hvPartlist")
@Path("/api/v1/partlist")
public class PartlistApi extends BaseApi implements IPartlistApi {
	private static Logger log = LoggerFactory.getLogger(PartlistApi.class) ;

	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Autowired
	private IParameterCall parameterCall ;

	@Autowired
	private PartlistQuery partlistQuery ;
	
	@Autowired
	private PartlistPositionQuery partlistPositionQuery ;
	
	@Autowired
	private IArtikelCall artikelCall ;	
	@Autowired
	private IStuecklisteCall stuecklisteCall ;
	@Autowired
	private IStuecklisteReportCall stuecklisteReportCall ;
	@Autowired
	private IJudgeCall judgeCall ;
	
	@Autowired
	private PartlistPositionEntryMapper partlistPositionEntryMapper ;
	@Autowired
	private HvSessionManager sessionManager ;
	@Autowired
	private FeatureFactory featureFactory ;
	
	@Override
	@GET
	@Path("/list")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public PartlistEntryList getPartlists(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex, 
			@QueryParam(Filter.CNR) String filterCnr, 
			@QueryParam("filter_textsearch") String filterTextSearch,
			@QueryParam(Filter.HIDDEN) Boolean filterWithHidden) throws NamingException, RemoteException, EJBExceptionLP {
		PartlistEntryList element = new PartlistEntryList() ;
		if(connectClient(userId) == null) return element ;

		FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
		collector.add(buildFilterCnr(filterCnr)) ;
//			collector.add(buildFilterTextSearch(filterTextSearch)) ;
		collector.add(buildFilterWithHidden(filterWithHidden)) ;
		FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND") ;
		
//		QueryParameters params = partlistQuery.getDefaultQueryParameters(filterCrits) ;
		QueryParametersFeatures params = partlistQuery.getFeatureQueryParameters(filterCrits) ;
		params.setLimit(limit) ;
		params.setKeyOfSelectedRow(startIndex) ;
		params.addFeature(StuecklisteHandlerFeature.LOS_STATUS);
//		params.addFeature(StuecklisteHandlerFeature.KUNDEN_ARTIKLENUMMER) ;
		
//		QueryResult result = partlistQuery.setQuery(params) ;			
		StuecklisteQueryResult result = (StuecklisteQueryResult) partlistQuery.setQuery(params) ;
		element.setList(partlistQuery.getResultList(result)) ;
		
		return element ;
	}
	
	@Override
	@GET
	@Path("{" + Param.PARTLISTID + "}/browseposition")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public PartlistPositionEntryList getBrowsePositions(
			@PathParam(Param.PARTLISTID) Integer partlistId,
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex) throws RemoteException, NamingException, EJBExceptionLP {
		PartlistPositionEntryList element = new PartlistPositionEntryList() ;
		if(connectClient(userId) == null) return element ;

		FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
		collector.add(partlistPositionQuery.getPartlistIdFilter(partlistId)) ;

		FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND") ;
		
		QueryParameters params = partlistPositionQuery.getDefaultQueryParameters(filterCrits) ;
		params.setLimit(limit) ;
		params.setKeyOfSelectedRow(startIndex) ;
		
		QueryResult result = partlistPositionQuery.setQuery(params) ;			
		element.setList(partlistPositionQuery.getResultList(result)) ;
		return element ;
	}
	
	@Override
	@GET
	@Path("{" + Param.PARTLISTID + "}/position")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public PartlistPositionEntryList getPositions(
			@PathParam(Param.PARTLISTID) Integer partlistId,
			@QueryParam(Param.USERID) String userId,
			@QueryParam("price") Boolean withPrice) throws RemoteException, NamingException, EJBExceptionLP {
		PartlistPositionEntryList element = new PartlistPositionEntryList() ;
		if(connectClient(userId) == null) return element ;
		
		if(withPrice == null) {
			withPrice = new Boolean(false) ;
		}
		
		BigDecimal p = BigDecimal.ZERO ;
		List<KundenStuecklistepositionDto> dtos = stuecklisteCall
				.stuecklistepositionFindByStuecklisteIIdAllData(partlistId, withPrice) ;
		for (KundenStuecklistepositionDto stuecklistepositionDto : dtos) {
			element.getList().add(partlistPositionEntryMapper.mapEntry(stuecklistepositionDto)) ;
			
			if(withPrice) {
				if(stuecklistepositionDto.getVkPreis() != null) {
					p = p.add(stuecklistepositionDto.getVkPreis().multiply(
							stuecklistepositionDto.getPositionDto().getNMenge())) ;
				}				
			}
		}
		
		if(withPrice) {
			element.setSalesPrice(p);
		}
		sessionManager.setRequest(getServletRequest());
		sessionManager.get(userId).setKundenStklPositionen(dtos);
		return element ;
	}
	
	@Override
	@POST
	@Path("{" + Param.PARTLISTID + "}/position")
	@Consumes({FORMAT_JSON, FORMAT_XML})
	@Produces({FORMAT_JSON, FORMAT_XML})
	public PartlistPositionEntry createPosition(
			@PathParam(Param.PARTLISTID) Integer partlistId,
			@QueryParam(Param.USERID) String userId,
			PartlistPositionPostEntry positionEntry) throws RemoteException, NamingException, EJBExceptionLP {
		if(connectClient(userId) == null) return null ;
		if(positionEntry == null) {
			respondBadRequestValueMissing("positionEntry") ;
			return null ;
		}
		
		if(StringHelper.isEmpty(positionEntry.getItemCnr())) {
			respondBadRequestValueMissing("itemCnr") ;
			return null ;
		}
		
		if(positionEntry.getAmount() == null) {
			respondBadRequestValueMissing("amount") ;
			return null ;
		}
		
		if(positionEntry.getUnitCnr() == null) {
			respondBadRequestValueMissing("unitCnr") ;
			return null ;
		}
		
		ArtikelDto itemDto = artikelCall.artikelFindByCNrOhneExc(positionEntry.getItemCnr()) ;
		if(itemDto == null) {
			respondNotFound("itemCnr", positionEntry.getItemCnr());
			return null ;
		}

//		List<StuecklistepositionDto> dtos = sessionManager.get(userId).getStklPositionen();
		
		StuecklistepositionDto positionDto = new StuecklistepositionDto() ;
		positionDto.setStuecklisteIId(partlistId) ;
		positionDto.setArtikelIId(itemDto.getIId()) ;
		positionDto.setEinheitCNr(positionEntry.getUnitCnr()) ;
		positionDto.setNMenge(positionEntry.getAmount()) ;
		positionDto.setPositionsartCNr(LocaleFac.POSITIONSART_IDENT);
		positionDto.setMontageartIId(getDefaultMontageartId());
		positionDto.setCPosition(positionEntry.getPosition()); 
		positionDto.setBMitdrucken(Helper.boolean2Short(false));
		positionDto.setCKommentar(positionEntry.getComment());
		
		Integer id = stuecklisteCall.createStuecklisteposition(positionDto) ;
		positionDto = stuecklisteCall.stuecklistepositionFindByPrimaryKey(id) ;
		sessionManager.get(userId).addStklPosition(positionDto);

		return partlistPositionEntryMapper.mapEntry(positionDto) ;		
	}

	@Override
	@PUT
	@Path("{" + Param.PARTLISTID + "}/position")
	@Consumes({FORMAT_JSON, FORMAT_XML})
	@Produces({FORMAT_JSON, FORMAT_XML})
	public PartlistPositionEntry updatePosition(
			@PathParam(Param.PARTLISTID) Integer partlistId,
			@QueryParam(Param.USERID) String userId,
			PartlistPositionPostEntry positionEntry) throws RemoteException, NamingException, EJBExceptionLP {
		if(connectClient(userId) == null) return null ;
		if(positionEntry == null) {
			respondBadRequestValueMissing("positionEntry") ;
			return null ;
		}
		
		if(positionEntry.getId() == null) {
			respondBadRequestValueMissing("id") ;
			return null ;
		}
		
		if(StringHelper.isEmpty(positionEntry.getItemCnr())) {
			respondBadRequestValueMissing("itemCnr") ;
			return null ;
		}
		
		if(positionEntry.getAmount() == null) {
			respondBadRequestValueMissing("amount") ;
			return null ;
		}
		
		if(positionEntry.getUnitCnr() == null) {
			respondBadRequestValueMissing("unitCnr") ;
			return null ;
		}

		sessionManager.setRequest(getServletRequest());
		StuecklistepositionDto positionDto = 
				findStuecklisteDtoFromSession(userId, positionEntry.getId()) ;
		if(positionDto == null) {
			respondNotFound("positionId", positionEntry.getId().toString());
			return null ;
		}
		
		ArtikelDto itemDto = artikelCall.artikelFindByCNrOhneExc(positionEntry.getItemCnr()) ;
		if(itemDto == null) {
			respondNotFound("itemCnr", positionEntry.getItemCnr());
			return null ;
		}
		
		StuecklistepositionDto aenderungDto = new StuecklistepositionDto() ;
		aenderungDto.setIId(positionDto.getIId());
		aenderungDto.setStuecklisteIId(partlistId) ;
		aenderungDto.setArtikelIId(itemDto.getIId()) ;
		aenderungDto.setEinheitCNr(itemDto.getEinheitCNr()) ;
		aenderungDto.setNMenge(positionEntry.getAmount()) ;
		aenderungDto.setPositionsartCNr(LocaleFac.POSITIONSART_IDENT);
		aenderungDto.setMontageartIId(getDefaultMontageartId());
		aenderungDto.setCPosition(positionEntry.getPosition()); 
		aenderungDto.setBMitdrucken(Helper.boolean2Short(false));
		aenderungDto.setCKommentar(positionEntry.getComment());
		
		try {
			lock(partlistId);
			stuecklisteCall.updateStuecklisteposition(positionDto, aenderungDto);			
		} catch(EJBExceptionLP e) {
			if(e.getCode() == EJBExceptionLP.FEHLER_BEIM_ANLEGEN) {
				respondLocked() ;
				return null ;
			}
			if(e.getCode() == EJBExceptionLP.FEHLER_DATEN_MODIFIZIERT_UPDATE) {
				respondModified() ;

				removeLock(partlistId);
				return null ;
			} 
			if(e.getCode() == EJBExceptionLP.FEHLER_DATEN_INKOMPATIBEL) {
				respondUnauthorized();
				
				removeLock(partlistId);
				return null ;
			}
			if(e.getCode()  == EJBExceptionLP.FEHLER_BEI_FINDBYPRIMARYKEY) {
				respondGone();

				removeLock(partlistId);
				return null ;
			}
			
			throw e ;
		}
		
		positionDto = stuecklisteCall.stuecklistepositionFindByPrimaryKey(positionDto.getIId()) ;
		sessionManager.get(userId).updateStklPosition(positionDto);
		removeLock(partlistId);
		return partlistPositionEntryMapper.mapEntry(positionDto) ;		
	}

	@Override
	@DELETE
	@Path("{" + Param.PARTLISTID + "}/position/{" + Param.POSITIONID + "}")
	public void removePosition(
			@PathParam(Param.PARTLISTID) Integer partlistId,
			@QueryParam(Param.USERID) String userId,
			@PathParam(Param.POSITIONID) Integer partlistPositionId) throws RemoteException, NamingException, EJBExceptionLP {
		if(connectClient(userId) == null) return ;
		
		sessionManager.setRequest(getServletRequest());
		StuecklistepositionDto positionDto = 
				findStuecklisteDtoFromSession(userId, partlistPositionId) ;
		if(positionDto == null) {
			respondNotFound(Param.POSITIONID, partlistPositionId.toString());
			return ;
		}
		
		try {
			lock(partlistId) ;
			stuecklisteCall.removeStuecklisteposition(positionDto, positionDto);
			removeLock(partlistId) ;
		} catch(EJBExceptionLP e) {
			if(e.getCode() == EJBExceptionLP.FEHLER_BEIM_ANLEGEN) {
				respondLocked() ;
				return ;				
			}
			if(e.getCode() == EJBExceptionLP.FEHLER_BEI_FINDBYPRIMARYKEY) {
				respondGone() ;
				
				removeLock(partlistId) ;
				return ;
			}
			
			throw e ;
		}
		
		sessionManager.get(userId).removeStklPosition(positionDto);
	}
	

	@GET
	@Path("{" + Param.PARTLISTID + "}/print")
//	@Produces("application/pdf")
	public Response printPartlist(
			@PathParam(Param.PARTLISTID) Integer partlistId,
			@QueryParam(Param.USERID) String userId) throws RemoteException, NamingException, EJBExceptionLP {
		if(connectClient(userId) == null) return getUnauthorized() ;

		StuecklisteDto dto = stuecklisteCall.stuecklisteFindByPrimaryKey(partlistId) ;
		
		try {
			Integer sortierung = new Integer(0) ; // Artikelnummer
			JasperPrintLP print = stuecklisteReportCall.printStuecklisteAllgemeinMitPreis(partlistId, 
					StuecklisteReportFac.REPORT_STUECKLISTE_OPTION_PREIS_VERKAUFSSPREIS, 
					true, false, false, false,
					StuecklisteReportFac.REPORT_STUECKLISTE_OPTION_SORTIERUNG_OHNE, true, 
					globalInfo.getTheClientDto(), sortierung, sortierung, sortierung) ;

			final ByteArrayOutputStream baos = new ByteArrayOutputStream(100000) ;
			HVPDFExporter exporter = new HVPDFExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print.getPrint());
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

			exporter.exportReport();
			StreamingOutput so = new StreamingOutput() {				
				@Override
				public void write(OutputStream os) throws IOException, WebApplicationException {
					os.write(baos.toByteArray()) ;
				}
			} ;
			String filename = encodeFileName(dto.getArtikelDto().getCNr()) + ".pdf" ;
			return Response.ok()
				.header("Content-Disposition", "filename=" + filename)
				.header("Content-Type", "application/pdf")
				.entity(so).build() ;
		} catch(Throwable e) {
			log.error("Exception", e) ;
//			return getInternalServerError() ;
			return Response.status(404).build();
//			return Response.ok()
//					.header("Content-Type", "application/json")
//					.entity("{\"error\" : 42, \"code:\" : \"" + e.getMessage() + "\"}").build();
//			return Response.ok()
//				.header("Content-Type", "text/html")
//				.entity("<html>Ein Fehler ist aufgetreten</html>").build() ;
		}
	}


	@POST
	@Path("{" + Param.PARTLISTID + "}/email")
	@Consumes({FORMAT_JSON, FORMAT_XML})
	public void sendEmail(
			@PathParam(Param.PARTLISTID) Integer partlistId,
			@QueryParam(Param.USERID) String userId,
			PartlistEmailEntry emailEntry) throws RemoteException, NamingException, EJBExceptionLP, Exception {
		if(connectClient(userId) == null) return ;

		if(!featureFactory.hasCustomerPartlist()) {
			respondExpectationFailed();
			return ;
		}

		if(emailEntry == null || StringHelper.isEmpty(emailEntry.getEmailText())) {
			respondBadRequestValueMissing("emailText");
			return ;
		}
		
		featureFactory.getObject().sendEmailToProvisionAccount(partlistId, emailEntry) ;
	}
	
	private void lock(Integer partlistId) throws RemoteException, NamingException {
		judgeCall.addLock(HelperClient.LOCKME_STUECKLISTE, partlistId);		
	}
	
	private void removeLock(Integer partlistId) throws RemoteException, NamingException {
		judgeCall.removeLock(HelperClient.LOCKME_STUECKLISTE, partlistId);				
	}
	
	private String encodeFileName(String filename) {
		return filename.replace("/", "").replace("<", "").replace(">", "") ;
	}
	
	private StuecklistepositionDto findStuecklisteDtoFromSession(String userId, Integer id) {
		List<StuecklistepositionDto> dtos = sessionManager.get(userId).getStklPositionen();
		for (StuecklistepositionDto stuecklistepositionDto : dtos) {
			if(id.compareTo(stuecklistepositionDto.getIId()) == 0) {
				return stuecklistepositionDto ;
			}
		}
		return null ;
	}
	
	private Integer getDefaultMontageartId() throws NamingException, RemoteException, EJBExceptionLP {
		MontageartDto[] montagearts = stuecklisteCall.montageartFindByMandantCNr() ;
		return montagearts.length > 0 ? montagearts[0].getIId() : null ;
	}
	
	private FilterKriterium buildFilterCnr(String filterCnr) throws RemoteException, NamingException {
		if(StringHelper.isEmpty(filterCnr)) return null ;
		
		int itemCnrLength = parameterCall.getMaximaleLaengeArtikelnummer() ;
		
		FilterKriteriumDirekt fk = new FilterKriteriumDirekt(
				"stueckliste." +
				StuecklisteFac.FLR_STUECKLISTE_FLRARTIKEL + ".c_nr", 
				StringHelper.removeSqlDelimiters(filterCnr),
				FilterKriterium.OPERATOR_LIKE, "",
				FilterKriteriumDirekt.PROZENT_TRAILING,
				true, 
				true, itemCnrLength); 
		fk.wrapWithProzent() ;
		fk.wrapWithSingleQuotes() ;
		return fk ;
	}
	
	private FilterKriterium buildFilterWithHidden(Boolean withHidden) {
		return FilterHelper.createWithHidden(withHidden,
				"stueckliste." + StuecklisteFac.FLR_STUECKLISTE_FLRARTIKEL + "." +
						ArtikelFac.FLR_ARTIKELLISTE_B_VERSTECKT) ;
	}	
}
