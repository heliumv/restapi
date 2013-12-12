package com.heliumv.api.worktime;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.api.item.ItemEntry;
import com.heliumv.factory.IAuftragCall;
import com.heliumv.factory.IAuftragpositionCall;
import com.heliumv.factory.IFertigungCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.IJudgeCall;
import com.heliumv.factory.IMandantCall;
import com.heliumv.factory.IPersonalCall;
import com.heliumv.factory.IProjektCall;
import com.heliumv.factory.IZeiterfassungCall;
import com.heliumv.factory.query.ArtikelArbeitszeitQuery;
import com.heliumv.factory.query.ZeitdatenQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.auftrag.service.AuftragDto;
import com.lp.server.auftrag.service.AuftragpositionDto;
import com.lp.server.fertigung.service.LosDto;
import com.lp.server.personal.service.PersonalDto;
import com.lp.server.personal.service.TaetigkeitDto;
import com.lp.server.personal.service.ZeitdatenDto;
import com.lp.server.personal.service.ZeiterfassungFac;
import com.lp.server.projekt.service.ProjektDto;
import com.lp.server.system.service.LocaleFac;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.util.EJBExceptionLP;

/**
 * Funktionalit&auml;t rund um die Zeit(daten)erfassung</br>
 *
 * Generell gilt, dass nur am HELIUM V angemeldete REST-API Benutzer diese Funktionen durchf&uuml;hren k&ouml;nnen.
 * Weiters werden die Rechte des Benutzers ber&uuml;cksichtigt. Er kann - wenn er darf - im Namen einer 
 * anderen Person/Mitarbeiter die Buchungen durchf&uuml;hren.
 *
 * <p>Der Benutzer der API ist daf&uuml;r verantwortlich, dass chronologisch richtige
 * Zeitbuchungen entstehen, da der HELIUM V Server zum gegebenen Zeitpunkt (noch)
 * nicht in Zukunft schauen kann.</p>
 * 
 * <p>Weiterf&uuml;hrende Dokumentation kann im 
 * <a href="http://www.heliumv.com/documentation?token=Zeiterfassung">HELIUM V Benutzerhandbuch</a> nachgelesen werden.
 * 
 * @author Gerold
 */
@Service("hvWorktime")
@Path("/api/v1/worktime/")
public class WorktimeApi extends BaseApi implements IWorktimeApi {
	
	@Autowired
	private IAuftragCall auftragCall ;
	
	@Autowired
	private IAuftragpositionCall auftragpositionCall ;
	
	@Autowired
	private IJudgeCall judgeCall ;
	
	@Autowired
	private IFertigungCall fertigungCall ;
	
	@Autowired
	private IMandantCall mandantCall ;
	
	@Autowired
	private IProjektCall projektCall ;
	
	@Autowired
	private IZeiterfassungCall zeiterfassungCall ;
	
	@Autowired
	private IPersonalCall personalCall ;
	
	@Autowired
	private ArtikelArbeitszeitQuery workItemQuery ;
	
	@Autowired 
	private IGlobalInfo globalInfo ;

	@Autowired
	private ZeitdatenQuery zeitdatenQuery ;
	
	@GET
	@Path("/{year}/{month}/{day}")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<ZeitdatenEntry> getWorktimeEntries(
			@QueryParam("userId") String userId,
			@PathParam("year") Integer year,
			@PathParam("month") Integer month,
			@PathParam("day") Integer day,
			@QueryParam("forStaffId") Integer forStaffId,
			@QueryParam("limit") Integer limit) {
		List<ZeitdatenEntry> entries = new ArrayList<ZeitdatenEntry>();
		if(connectClient(userId) == null) return entries ;

		Integer personalId = globalInfo.getTheClientDto().getIDPersonal() ;
		try {
			if(isValidPersonalId(forStaffId)) {
				personalId = forStaffId ;
			}
			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(zeitdatenQuery.getFilterForPersonalId(personalId)) ;
			collector.addAll(zeitdatenQuery.getFilterForDate(year, month, day)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
			
			QueryParameters params = zeitdatenQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
//			params.setKeyOfSelectedRow(startIndex) ;

			QueryResult result = zeitdatenQuery.setQuery(params) ;
			entries = zeitdatenQuery.getResultList(result) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
		return entries ;
	}
	
	@DELETE
	@Path("/{worktimeId}")	
	public void removeWorktime(
			@QueryParam("userId") String userId,
			@PathParam("worktimeId") Integer worktimeId,
			@QueryParam("forStaffId") Integer forStaffId) {
		if(connectClient(userId) == null) return ;
		Integer personalId = globalInfo.getTheClientDto().getIDPersonal() ;
		try {
			if(isValidPersonalId(forStaffId)) {
				personalId = forStaffId ;				
			}
			ZeitdatenDto zDto = zeiterfassungCall.zeitdatenFindByPrimaryKey(worktimeId) ;
			if(zDto == null) {
				respondBadRequest("worktimeId", worktimeId.toString()) ;
				return ;
			}
			if(zDto.getPersonalIId().equals(personalId)) {
				zeiterfassungCall.removeZeitdaten(zDto) ;
			}
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {			
			respondUnavailable(e) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}
	}
	
	@POST
	@Path("/coming/")
	@Consumes({"application/json", "application/xml"})
	public Response bookComing(TimeRecordingEntry entry) {
		return bookTimeEntryImpl(entry, ZeiterfassungFac.TAETIGKEIT_KOMMT) ;
	}

	
//	@POST
//	@Path("/coming/{userid}/{year}/{month}/{day}/{hour}/{minute}/{second}")
//	public Response bookComing(
//			@PathParam("userId") String userId,
//			@PathParam("year") Integer year,
//			@PathParam("month") Integer month,
//			@PathParam("day") Integer day,
//			@PathParam("hour") Integer hour,
//			@PathParam("minute") Integer minute,
//			@PathParam("second") Integer second) {
//		return bookComing(convertFrom(userId, year, month, day, hour, minute, second)) ;
//	}
	
	@POST
	@Path("/going/")
	@Consumes({"application/json", "application/xml"})
	public Response bookGoing(TimeRecordingEntry entry) {
		return bookTimeEntryImpl(entry, ZeiterfassungFac.TAETIGKEIT_GEHT) ;
	}

	
	@POST
	@Path("/pausing/")
	@Consumes({"application/json", "application/xml"})
	public Response bookPausing(TimeRecordingEntry entry) {
		return bookTimeEntryImpl(entry, ZeiterfassungFac.TAETIGKEIT_UNTER) ;
	}

	@POST
	@Path("/stopping/")
	@Consumes({"application/json", "application/xml"})
	public Response bookStopping(TimeRecordingEntry entry) {
		return bookTimeEntryImpl(entry, ZeiterfassungFac.TAETIGKEIT_ENDE) ;
	}
	
	@POST
	@Path("/order/")
	@Consumes({"application/json", "application/xml"})
	public Response bookOrder(OrderRecordingEntry entry) {
		if(connectClient(entry.getUserId()) == null) return getUnauthorized() ;

		try {
			if(!mandantCall.hasModulAuftrag()) return getUnauthorized() ;
			if(!isValidPersonalId(entry.getForStaffId())) {
				entry.setForStaffId(globalInfo.getTheClientDto().getIDPersonal()) ;
			}
			if(!isValidOrderId(entry.getOrderId())) {
				return getBadRequest("orderId",  entry.getOrderId().toString()) ;				
			}
			if(!isValidOrderPositionId(entry.getOrderId(), entry.getOrderPositionId())) {
				return getBadRequest("orderPositionId",  entry.getOrderPositionId().toString()) ;								
			}

			ZeitdatenDto zDto = createDefaultZeitdatenDto(entry) ;
//			zDto.setCBelegartnr(LocaleFac.BELEGART_AUFTRAG) ;
			zDto.setIBelegartid(entry.getOrderId()) ;
			zDto.setIBelegartpositionid(entry.getOrderPositionId()) ;
			zeiterfassungCall.createAuftragZeitdaten(zDto, true, true, true) ;
		} catch(NamingException e) {
			return getUnavailable(e) ;
		} catch(RemoteException e) {
			return getUnavailable(e) ;			
		} catch(EJBExceptionLP e) {
			return getBadRequest(e) ;
		}
	
		return getNoContent() ;
	}

	@POST
	@Path("/production/")
	@Consumes({"application/json", "application/xml"})
	public Response bookProduction(ProductionRecordingEntry entry) {
		if(connectClient(entry.getUserId()) == null) return getUnauthorized() ;
		try {
			if(!mandantCall.hasModulLos()) return getUnauthorized() ;
			if(!isValidPersonalId(entry.getForStaffId())) {
				entry.setForStaffId(globalInfo.getTheClientDto().getIDPersonal()) ;
			}

			if(!isValidProductionId(entry.getProductionId())) {
				return getBadRequest("productionId",  entry.getProductionId().toString()) ;
			}
			
			ZeitdatenDto zDto = createDefaultZeitdatenDto(entry) ;
			zDto.setCBelegartnr(LocaleFac.BELEGART_LOS) ;
			zDto.setIBelegartid(entry.getProductionId()) ;
			zeiterfassungCall.createZeitdaten(zDto, true, true, true) ;
		} catch(NamingException e) {
			return getUnavailable(e) ;
		} catch(RemoteException e) {
			return getUnavailable(e) ;			
		} catch(EJBExceptionLP e) {
			return getBadRequest(e) ;
		}
		return getNoContent() ;
	}
	
	@POST
	@Path("/project/")
	@Consumes({"application/json", "application/xml"})
	public Response bookProject(ProjectRecordingEntry entry) {
		if(connectClient(entry.getUserId()) == null) return getUnauthorized() ;
		try {
//			if(!mandantCall.hasModulProjekt()) return getUnauthorized() ;
			if(!isValidPersonalId(entry.getForStaffId())) {
				entry.setForStaffId(globalInfo.getTheClientDto().getIDPersonal()) ;
			}

			if(!isValidProjectId(entry.getProjectId())) {
				return getBadRequest("projectId",  entry.getProjectId().toString()) ;
			}
			
			ZeitdatenDto zDto = createDefaultZeitdatenDto(entry) ;
			zDto.setArtikelIId(null) ;
			zDto.setCBelegartnr(LocaleFac.BELEGART_PROJEKT) ;
			zDto.setIBelegartid(entry.getProjectId()) ;
			zDto.setArtikelIId(entry.getWorkItemId()) ;
			zeiterfassungCall.createZeitdaten(zDto, true, true, true) ;
		} catch(NamingException e) {
			return getUnavailable(e) ;
		} catch(RemoteException e) {
			return getUnavailable(e) ;			
		} catch(EJBExceptionLP e) {
			return getBadRequest(e) ;
		}
		return getNoContent() ;
	}
	
	private ZeitdatenDto createDefaultZeitdatenDto(DocumentRecordingEntry entry) {
		ZeitdatenDto zDto = new ZeitdatenDto() ;
		zDto.setPersonalIId(entry.getForStaffId()) ;
		zDto.setArtikelIId(entry.getWorkItemId()) ;
		zDto.setCBemerkungZuBelegart(entry.getRemark()) ;
		zDto.setXKommentar(entry.getExtendedRemark()) ;
		zDto.setTZeit(getTimestamp(entry)) ;	
		return zDto ;
	}	
	
	private boolean isValidProjectId(Integer projectId) throws NamingException, RemoteException {
		ProjektDto projektDto = projektCall.projektFindByPrimaryKeyOhneExc(projectId) ;
		if(projektDto == null) return false ;
		
		return projektDto.getMandantCNr().equals(globalInfo.getMandant()) ;
	}
		
	private boolean isValidProductionId(Integer productionId) throws NamingException {
		LosDto losDto = fertigungCall.losFindByPrimaryKeyOhneExc(productionId) ;
		if(losDto == null) return false ;
		
		return losDto.getMandantCNr().equals(globalInfo.getMandant()) ;
	}
	
	private boolean isValidPersonalId(Integer forStaffId) throws NamingException {
		if(forStaffId == null) return false ;
		
		if(judgeCall.hasPersSichtbarkeitAlle()) {
			PersonalDto forPers = personalCall.byPrimaryKeySmall(forStaffId) ;
			return forPers != null ;
		}
		
		if(judgeCall.hasPersSichtbarkeitAbteilung()) {
			PersonalDto forPers = personalCall.byPrimaryKeySmall(forStaffId) ;
			if(forPers == null) return false ;
			PersonalDto mePers = personalCall.byPrimaryKeySmall(globalInfo.getTheClientDto().getIDPersonal()) ;
			if(mePers.getKostenstelleIIdAbteilung() == null) return false ;
			
			return mePers.getKostenstelleIIdAbteilung().equals(forPers.getKostenstelleIIdAbteilung()) ;
		}

		return false ;
	}
	
	
	private boolean isValidOrderId(Integer orderId) throws NamingException {
		AuftragDto auftragDto = auftragCall.auftragFindByPrimaryKeyOhneExc(orderId) ;
		if(auftragDto == null) return false ;
		
		return auftragDto.getMandantCNr().equals(globalInfo.getMandant()) ;
	}
	
	
	private boolean isValidOrderPositionId(Integer orderId, Integer positionId) throws NamingException {
		AuftragpositionDto auftragPositionDto = auftragpositionCall.auftragpositionFindByPrimaryKeyOhneExc(positionId) ;
		if(auftragPositionDto == null) return false ;
		
		return orderId.equals(auftragPositionDto.getBelegIId()) ;
	}
	
	@GET
	@Path("/activities/")
	@Produces({FORMAT_JSON, FORMAT_XML})
	@Override
	public List<ItemEntry> getActivities(
			@QueryParam("userid") String userId,
			@QueryParam("limit") Integer limit,
			@QueryParam("startIndex") Integer startIndex, 
			@QueryParam("filter_cnr") String filterCnr) {
		List<ItemEntry> activities = new ArrayList<ItemEntry>() ;
		try {
			if(null == connectClient(userId)) return activities ;

			FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
			collector.add(workItemQuery.getFilterArtikelNummer(filterCnr)) ;
			FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;

			QueryParameters params = workItemQuery.getDefaultQueryParameters(filterCrits) ;
			params.setLimit(limit) ;
			params.setKeyOfSelectedRow(startIndex) ;

			QueryResult result = workItemQuery.setQuery(params) ;
			activities = workItemQuery.getResultList(result) ;	
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		}
		
		return activities ;
	}	
	
	@GET
	@Path("/specialactivities/")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<SpecialActivity> getSpecialActivities(
			@QueryParam("userid") String userId) {
		List<SpecialActivity> activities = new ArrayList<SpecialActivity>() ;
		
		try {			
			if(connectClient(userId) == null) return activities ;
			boolean hasRechtNurBuchen = judgeCall.hasPersZeiteingabeNurBuchen() ;
			
			if(hasRechtNurBuchen) {
				activities = zeiterfassungCall.getAllSprSondertaetigkeitenNurBDEBuchbar(globalInfo.getTheClientDto().getLocUiAsString());
			} else {
				activities = zeiterfassungCall.getAllSprSondertaetigkeiten(globalInfo.getTheClientDto().getLocUiAsString());
			}
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
		return activities ;
	}

	
	@GET
	@Path("/documenttypes/")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public List<DocumentType> getDocumentTypes(
			@QueryParam("userid") String userId) {
		List<DocumentType> documentTypes = new ArrayList<DocumentType>() ;
		
		try {
			if(connectClient(userId) == null) return documentTypes ;

			documentTypes = zeiterfassungCall.getBebuchbareBelegarten(globalInfo.getTheClientDto()) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
		return documentTypes ;
	}

	
	private Response bookTimeEntryImpl(TimeRecordingEntry entry, String bookingType) {
		try {
			if(connectClient(entry.getUserId()) == null) {
				return getBadRequest("userId", entry.getUserId()) ;
			}
	
			if(!isValidPersonalId(entry.getForStaffId())) {
				entry.setForStaffId(globalInfo.getTheClientDto().getIDPersonal()) ;
			}
			
			PersonalDto personalDto = personalCall.byPrimaryKeySmall(entry.getForStaffId());
			if (null == personalDto) {
				return getUnauthorized();
			}
			
			Integer taetigkeitIId = getTaetigkeitIId(bookingType);
			if (null == taetigkeitIId) {
				return getInternalServerError(bookingType + " not defined");
			}
			
			Timestamp timestamp = getTimestamp(entry);

			return bucheZeitPersonalID(personalDto.getIId(), timestamp,
					taetigkeitIId, entry.getWhere());
		} catch (NamingException e) {
			return getUnavailable(e);
		}
	}
		
//	private TimeRecordingEntry convertFrom(String userId, int year, int month, int day, int hour, int minute, int second) {
//		TimeRecordingEntry entry = new TimeRecordingEntry() ;
//		entry.setUserId(userId) ;
//		entry.setYear(year) ;
//		entry.setMonth(month) ;
//		entry.setDay(day) ;
//		entry.setHour(hour) ;
//		entry.setMinute(minute) ;
//		entry.setSecond(second) ;
//		return entry ;
//	}
	
	private Timestamp getTimestamp(TimeRecordingEntry entry) {
		Calendar c = Calendar.getInstance();
		c.set(entry.getYear(), entry.getMonth() - 1, entry.getDay(),
				entry.getHour(), entry.getMinute(), entry.getSecond());
		return new Timestamp(c.getTimeInMillis()) ;		
	}
	
//	private Timestamp getTimestamp(int year, int month, int day, int hour, int minute, int second) {
//		Calendar c = Calendar.getInstance();
//		c.set(year, month - 1, day, hour, minute, second);
//		return new Timestamp(c.getTimeInMillis()) ;
//	}

	private Response bucheZeitPersonalID(Integer personalIId, Timestamp timestamp, 
			Integer taetigkeitIId, String station) {
		ZeitdatenDto zd = new ZeitdatenDto();
		zd.setPersonalIId(personalIId) ;
		zd.setTZeit(timestamp);
		zd.setTaetigkeitIId(taetigkeitIId);		
		zd.setCWowurdegebucht(StringHelper.trim(station)) ;
		zd.setTAendern(timestamp);
		
		
		try {
			zeiterfassungCall.createZeitdaten(zd, true, true, false) ;
			return getNoContent() ;
		} catch(EJBExceptionLP e) {
			return getInternalServerError(e) ;
		} catch(RemoteException e) {			
			return getUnavailable(e) ;
		} catch(NamingException e) {			
			return getUnavailable(e) ;
		}
	}
	
	
	private Integer getTaetigkeitIId(String cNr) throws NamingException {
		TaetigkeitDto taetigkeitDto = zeiterfassungCall.taetigkeitFindByCNr(cNr.trim()) ;
		return taetigkeitDto == null ? null : taetigkeitDto.getIId() ;
	}
}
