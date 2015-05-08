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
import com.heliumv.factory.IParameterCall;
import com.heliumv.factory.IPersonalCall;
import com.heliumv.factory.IProjektCall;
import com.heliumv.factory.IZeiterfassungCall;
import com.heliumv.factory.query.ArtikelArbeitszeitQuery;
import com.heliumv.factory.query.ZeitdatenQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.heliumv.tools.StringHelper;
import com.lp.server.auftrag.service.AuftragDto;
import com.lp.server.auftrag.service.AuftragpositionDto;
import com.lp.server.fertigung.service.FertigungFac;
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
	
	@Autowired
	private IParameterCall parameterCall ;
	
	@GET
	@Path("/{year}/{month}/{day}")
	@Produces({FORMAT_JSON, FORMAT_XML})
	@Override
	public List<ZeitdatenEntry> getWorktimeEntries(
			@QueryParam("userId") String userId,
			@PathParam("year") Integer year,
			@PathParam("month") Integer month,
			@PathParam("day") Integer day,
			@QueryParam("forStaffId") Integer forStaffId,
			@QueryParam("forStaffCnr") String forStaffCnr,
			@QueryParam("limit") Integer limit) {
		List<ZeitdatenEntry> entries = new ArrayList<ZeitdatenEntry>();
		if(connectClient(userId) == null) return entries ;

		Integer personalId = globalInfo.getTheClientDto().getIDPersonal() ;
		try {
			ValidPersonalId validator = new ValidPersonalId(personalId, forStaffId, forStaffCnr);
			if(!validator.validate()) return entries ;
			personalId = validator.getStaffIdToUse() ;

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
		} catch(RemoteException e) {
			respondUnavailable(e);
		}
		
		return entries ;
	}
	
	@DELETE
	@Path("/{worktimeId}")
	@Override
	public void removeWorktime(
			@QueryParam("userId") String userId,
			@PathParam("worktimeId") Integer worktimeId,
			@QueryParam("forStaffId") Integer forStaffId,
			@QueryParam("forStaffCnr") String forStaffCnr) {
		if(connectClient(userId) == null) return ;
		Integer personalId = globalInfo.getTheClientDto().getIDPersonal() ;
		try {
			ValidPersonalId validator = new ValidPersonalId(personalId, forStaffId, forStaffCnr);
			if(!validator.validate()) return ;
			personalId = validator.getStaffIdToUse() ;
			
			ZeitdatenDto zDto = zeiterfassungCall.zeitdatenFindByPrimaryKey(worktimeId) ;
			if(zDto == null) {
				respondBadRequest("worktimeId", worktimeId.toString()) ;
				return ;
			}

			if(personalId.equals(zDto.getPersonalIId())) {
				zeiterfassungCall.removeZeitdaten(zDto) ;
			} else {
				respondUnauthorized() ;
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
	public void bookComing(TimeRecordingEntry entry) {
		bookTimeEntryImpl(entry, ZeiterfassungFac.TAETIGKEIT_KOMMT) ;
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
	public void bookGoing(TimeRecordingEntry entry) {
		bookTimeEntryImpl(entry, ZeiterfassungFac.TAETIGKEIT_GEHT) ;
	}

	
	@POST
	@Path("/pausing/")
	@Consumes({"application/json", "application/xml"})
	public void bookPausing(TimeRecordingEntry entry) {
		bookTimeEntryImpl(entry, ZeiterfassungFac.TAETIGKEIT_UNTER) ;
	}

	@POST
	@Path("/stopping/")
	@Consumes({"application/json", "application/xml"})
	public void bookStopping(TimeRecordingEntry entry) {
		bookTimeEntryImpl(entry, ZeiterfassungFac.TAETIGKEIT_ENDE) ;
	}
	
	@POST
	@Path("/order/")
	@Consumes({"application/json", "application/xml"})
	public void bookOrder(OrderRecordingEntry entry) {
		if(connectClient(entry.getUserId()) == null) return ;

		try {
			if(!isValidBeleg(LocaleFac.BELEGART_AUFTRAG)) {
				respondUnauthorized(); 
				return ;
			}

			ValidPersonalId validator = new ValidPersonalId(
					globalInfo.getTheClientDto().getIDPersonal(), 
					entry.getForStaffId(), entry.getForStaffCnr());
			if(!validator.validate()) return ;

			entry.setForStaffId(validator.getStaffIdToUse());
			
			if(!isValidOrderId(entry.getOrderId())) {
				respondBadRequest("orderId",  entry.getOrderId().toString()) ;				
				return ;
			}
			
			if(!isValidOrderPositionId(entry.getOrderId(), entry.getOrderPositionId())) {
				respondBadRequest("orderPositionId",  entry.getOrderPositionId().toString()) ;
				return ;
			}

			ZeitdatenDto zDto = createDefaultZeitdatenDto(entry) ;
			zDto.setIBelegartid(entry.getOrderId()) ;
			zDto.setIBelegartpositionid(entry.getOrderPositionId()) ;
			zeiterfassungCall.createAuftragZeitdaten(zDto, true, true, true) ;
		} catch(NamingException e) {
			respondUnavailable(e);
		} catch(RemoteException e) {
			respondUnavailable(e);
		} catch(EJBExceptionLP e) {
			respondBadRequest(e);
		}	
	}

	@POST
	@Path("/production/")
	@Consumes({"application/json", "application/xml"})
	public void bookProduction(ProductionRecordingEntry entry) {
		if(connectClient(entry.getUserId()) == null) return ;
		try {
			if(!isValidBeleg(LocaleFac.BELEGART_LOS)) {
				respondUnauthorized(); 
				return ;
			}
			
			ValidPersonalId validator = new ValidPersonalId(
					globalInfo.getTheClientDto().getIDPersonal(), 
					entry.getForStaffId(), entry.getForStaffCnr());
			if(!validator.validate()) return ;

			entry.setForStaffId(validator.getStaffIdToUse());

			if(!isValidProductionId(entry.getProductionId())) {
				respondBadRequest("productionId",  entry.getProductionId().toString()) ;
				return ;
			}
			
			ZeitdatenDto zDto = createDefaultZeitdatenDto(entry) ;
			zDto.setCBelegartnr(LocaleFac.BELEGART_LOS) ;
			zDto.setIBelegartid(entry.getProductionId()) ;
			zeiterfassungCall.createZeitdaten(zDto, true, true, true) ;
		} catch(NamingException e) {
			respondUnavailable(e); 
		} catch(RemoteException e) {
			respondUnauthorized(); 			
		} catch(EJBExceptionLP e) {
			respondBadRequest(e); 
		}
	}
	
	@POST
	@Path("/project/")
	@Consumes({"application/json", "application/xml"})
	public void bookProject(ProjectRecordingEntry entry) {
		if(connectClient(entry.getUserId()) == null) return ;
		try {
			if(!isValidBeleg(LocaleFac.BELEGART_PROJEKT)) {
				respondUnauthorized(); 
				return ;
			}
	
			ValidPersonalId validator = new ValidPersonalId(
					globalInfo.getTheClientDto().getIDPersonal(), 
					entry.getForStaffId(), entry.getForStaffCnr());
			if(!validator.validate()) return ;

			entry.setForStaffId(validator.getStaffIdToUse());

			if(!isValidProjectId(entry.getProjectId())) {
				respondBadRequest("projectId",  entry.getProjectId().toString()) ;
				return ;
			}
			
			ZeitdatenDto zDto = createDefaultZeitdatenDto(entry) ;
			zDto.setArtikelIId(null) ;
			zDto.setCBelegartnr(LocaleFac.BELEGART_PROJEKT) ;
			zDto.setIBelegartid(entry.getProjectId()) ;
			zDto.setArtikelIId(entry.getWorkItemId()) ;
			zeiterfassungCall.createZeitdaten(zDto, true, true, true) ;
		} catch(NamingException e) {
			respondUnavailable(e);
		} catch(RemoteException e) {
			respondUnavailable(e); 			
		} catch(EJBExceptionLP e) {
			respondBadRequest(e);
		}
	}
	
	private ZeitdatenDto createDefaultZeitdatenDto(DocumentRecordingEntry entry) {
		ZeitdatenDto zDto = new ZeitdatenDto() ;
		zDto.setPersonalIId(entry.getForStaffId()) ;
		zDto.setArtikelIId(entry.getWorkItemId()) ;
		zDto.setCBemerkungZuBelegart(entry.getRemark()) ;
		zDto.setXKommentar(entry.getExtendedRemark()) ;
		Timestamp t = getTimestamp(entry) ;
		zDto.setTZeit(t) ;
		zDto.setTAendern(t);
		zDto.setCWowurdegebucht(StringHelper.trim(entry.getWhere()));
		return zDto ;
	}	
	
	private boolean isValidProjectId(Integer projectId) throws NamingException, RemoteException {
		ProjektDto projektDto = projektCall.projektFindByPrimaryKeyOhneExc(projectId) ;
		if(projektDto == null) return false ;
		
		return projektDto.getMandantCNr().equals(globalInfo.getMandant()) ;
	}
		
	private boolean isValidProductionId(Integer productionId) throws RemoteException, NamingException {
		LosDto losDto = fertigungCall.losFindByPrimaryKeyOhneExc(productionId) ;
		if(losDto == null) return false ;
		
		if(!losDto.getMandantCNr().equals(globalInfo.getMandant())) return false ;
		if(FertigungFac.STATUS_GESTOPPT.equals(losDto.getStatusCNr())) return false ;
		if(FertigungFac.STATUS_ANGELEGT.equals(losDto.getStatusCNr())  && 
				!parameterCall.isZeitdatenAufAngelegteLoseBuchbar()) return false ;
		if(FertigungFac.STATUS_ERLEDIGT.equals(losDto.getStatusCNr()) &&
				!parameterCall.isZeitdatenAufErledigteBuchbar()) return false ;
		return true ;
	}
	
	protected class ValidPersonalId {
		private Integer staffId ;
		private String staffCnr ;
		private Integer myPersonalId ;
		private Integer staffIdToUse ;

		public ValidPersonalId(Integer myPersonalId) {
			this.myPersonalId = myPersonalId ;
		}
		
		public ValidPersonalId(Integer myPersonalId, Integer forStaffId, String forStaffCnr) {			
			this.myPersonalId = myPersonalId ;
			this.staffId = forStaffId ;
			this.staffCnr = forStaffCnr ;
			this.staffIdToUse = null ;
		}

		public Integer getStaffIdToUse() {
			return staffIdToUse ;
		}
		
		public boolean validate(Integer forStaffId, String forStaffCnr) throws RemoteException, NamingException {
			this.staffId = forStaffId ;
			this.staffCnr = forStaffCnr ;
			this.staffIdToUse = null ;
			return validate() ;
		}

		public boolean validate() throws RemoteException, NamingException {
			if(staffId != null) {
				PersonalDto forPers = personalCall.byPrimaryKeySmall(staffId) ;
				return validatePersonalDto("staffId", staffId.toString(), forPers) ;
			}
			
			if(staffCnr != null) {
				PersonalDto forPers = personalCall.byCPersonalnrMandantCNrOhneExc(staffCnr) ;
				return validatePersonalDto("staffCnr", staffCnr, forPers) ;
			}

			staffIdToUse = myPersonalId ;
			return true ;
		}
		
		private boolean validatePersonalDto(String fieldName, String value, PersonalDto forPers) throws NamingException {
			if(forPers != null) {
				if(judgeCall.hasPersSichtbarkeitAlle()) {
					staffIdToUse = forPers.getIId() ;
					return true ;
				}

				if(judgeCall.hasPersSichtbarkeitAbteilung()) {
					PersonalDto mePers = personalCall.byPrimaryKeySmall(myPersonalId) ;
					if(mePers.getKostenstelleIIdAbteilung() != null) {
						if(mePers.getKostenstelleIIdAbteilung().equals(
								forPers.getKostenstelleIIdAbteilung())){
							staffIdToUse = forPers.getIId() ;
							return true ;
						}						
					}
				}				
			}

			respondBadRequest(fieldName, value);
			return false ;
		}
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
			@QueryParam(Param.USERID) String userId) {
		List<DocumentType> documentTypes = new ArrayList<DocumentType>() ;
		
		try {
			if(connectClient(userId) == null) return documentTypes ;

			documentTypes = zeiterfassungCall.getBebuchbareBelegarten(globalInfo.getTheClientDto()) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
		return documentTypes ;
	}

	
	
	private void bookTimeEntryImpl(TimeRecordingEntry entry, String bookingType) {
		try {
			if(connectClient(entry.getUserId()) == null) return ; 
	
			ValidPersonalId validator = new ValidPersonalId(
					globalInfo.getTheClientDto().getIDPersonal(), 
					entry.getForStaffId(), entry.getForStaffCnr());
			if(!validator.validate()) return ;

			entry.setForStaffId(validator.getStaffIdToUse());

			Integer taetigkeitIId = getTaetigkeitIId(bookingType);
			if (null == taetigkeitIId) {
				respondBadRequest(bookingType, "undefined"); 
				return ;
			}
			
			Timestamp timestamp = getTimestamp(entry);

			bucheZeitPersonalID(validator.getStaffIdToUse(), timestamp,
					taetigkeitIId, entry.getWhere());
		} catch (NamingException e) {
			respondUnavailable(e);  
		} catch(RemoteException e) {
			respondUnavailable(e);
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
	
	private void bucheZeitPersonalID(Integer personalIId, Timestamp timestamp, 
			Integer taetigkeitIId, String station) {
		ZeitdatenDto zd = new ZeitdatenDto();
		zd.setPersonalIId(personalIId) ;
		zd.setTZeit(timestamp);
		zd.setTaetigkeitIId(taetigkeitIId);		
		zd.setCWowurdegebucht(StringHelper.trim(station)) ;
		zd.setTAendern(timestamp);
	
		try {
			zeiterfassungCall.createZeitdaten(zd, true, true, false) ;
		} catch(EJBExceptionLP e) {
			respondBadRequest(e);
		} catch(RemoteException e) {			
			respondUnavailable(e);
		} catch(NamingException e) {
			respondUnavailable(e);
		}
	}
	
	
	private Integer getTaetigkeitIId(String cNr) throws NamingException {
		TaetigkeitDto taetigkeitDto = zeiterfassungCall.taetigkeitFindByCNr(cNr.trim()) ;
		return taetigkeitDto == null ? null : taetigkeitDto.getIId() ;
	}
	
	private boolean isValidBeleg(String belegart) throws NamingException {
		List<DocumentType> allowedTypes = zeiterfassungCall.getBebuchbareBelegarten() ;
		for (DocumentType documentType : allowedTypes) {
			if(belegart.equals(documentType.getId())) return true ;
		}

		return false ;
	}
}
