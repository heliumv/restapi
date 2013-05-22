package com.heliumv.api.worktime;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.lp.server.personal.service.PersonalDto;
import com.lp.server.personal.service.TaetigkeitDto;
import com.lp.server.personal.service.ZeitdatenDto;
import com.lp.server.personal.service.ZeiterfassungFac;
import com.lp.server.system.service.TheClientDto;
import com.lp.util.EJBExceptionLP;

@Service("hvWorktime")
@Path("/api/v1/worktime/")
public class WorktimeApi extends BaseApi implements IWorktimeApi {
	
	@POST
	@Path("/coming/")
	@Consumes({"application/json", "application/xml"})
	public Response bookComing(TimeRecordingEntry entry) {
		return bookTimeEntryImpl(entry, ZeiterfassungFac.TAETIGKEIT_KOMMT) ;
	}

	
	@POST
	@Path("/coming/{userid}/{year}/{month}/{day}/{hour}/{minute}/{second}")
	public Response bookComing(
			@PathParam("userid") String userId,
			@PathParam("year") Integer year,
			@PathParam("month") Integer month,
			@PathParam("day") Integer day,
			@PathParam("hour") Integer hour,
			@PathParam("minute") Integer minute,
			@PathParam("second") Integer second) {
		return bookComing(convertFrom(userId, year, month, day, hour, minute, second)) ;
	}

	
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

	@GET
	@Path("/specialactivities/{userid}")
	@Produces({"application/json", "application/xml"})
	public List<SpecialActivity> getActivities(
			@PathParam("userid") String userId) {
		List<SpecialActivity> activities = new ArrayList<SpecialActivity>() ;
		
		if(null == userId || 0 == userId.trim().length()) {
			respondBadRequest("userid", "{empty}") ;
			return activities ;
		}

		try {
			TheClientDto theClientDto = getServer().getClientCall().theClientFindByUserLoggedIn(userId) ;
			if (null == theClientDto || null == theClientDto.getIDPersonal()) {
				respondUnauthorized() ; 
				return activities ;
			}
			
			boolean hasRechtNurBuchen = getServer().getJudgeCall().hasPersZeiteingabeNurBuchen(theClientDto) ;
			
			if(hasRechtNurBuchen) {
				activities = getServer().getZeiterfassungCall().getAllSprSondertaetigkeitenNurBDEBuchbar(theClientDto.getLocUiAsString());
			} else {
				activities = getServer().getZeiterfassungCall().getAllSprSondertaetigkeiten(theClientDto.getLocUiAsString());
			}
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
		return activities ;
	}

	
	@GET
	@Path("/documenttypes/{userid}")
	@Produces({"application/json", "application/xml"})
	public List<DocumentType> getDocumentTypes(
			@PathParam("userid") String userId) {
		List<DocumentType> documentTypes = new ArrayList<DocumentType>() ;
		
		if(null == userId || 0 == userId.trim().length()) {
			respondBadRequest("userid", "{empty}") ;
			return documentTypes ;
		}

		try {
			TheClientDto theClientDto = getServer().getClientCall().theClientFindByUserLoggedIn(userId) ;
			if (null == theClientDto || null == theClientDto.getIDPersonal()) {
				respondUnauthorized() ; 
				return documentTypes ;
			}

			documentTypes = getServer().getZeiterfassungCall().getBebuchbareBelegarten(theClientDto) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
		return documentTypes ;
	}

	
	private Response bookTimeEntryImpl(TimeRecordingEntry entry, String bookingType) {
		try {
			if(null == entry.getUserId() || 0 == entry.getUserId().trim().length()) {
				return getBadRequest("userId", entry.getUserId()) ;
			}
			
			TheClientDto theClientDto = getServer().getClientCall()
					.theClientFindByUserLoggedIn(entry.getUserId());
			if (null == theClientDto || null == theClientDto.getIDPersonal()) {
				return getUnauthorized();
			}

			PersonalDto personalDto = getServer().getPersonalCall()
					.byPrimaryKeySmall(theClientDto.getIDPersonal());
			if (null == personalDto) {
				return getUnauthorized();
			}
			
			Integer taetigkeitIId = getTaetigkeitIId(bookingType);
			if (null == taetigkeitIId) {
				return getInternalServerError(bookingType + " not defined");
			}
			
			Timestamp timestamp = getTimestamp(entry);

			return bucheZeitPersonalID(personalDto.getIId(), timestamp,
					taetigkeitIId, null, theClientDto);
		} catch (NamingException e) {
			return getUnavailable(e);
		}
	}
		
	private TimeRecordingEntry convertFrom(String userId, int year, int month, int day, int hour, int minute, int second) {
		TimeRecordingEntry entry = new TimeRecordingEntry() ;
		entry.setUserId(userId) ;
		entry.setYear(year) ;
		entry.setMonth(month) ;
		entry.setDay(day) ;
		entry.setHour(hour) ;
		entry.setMinute(minute) ;
		entry.setSecond(second) ;
		return entry ;
	}
	
	private Timestamp getTimestamp(TimeRecordingEntry entry) {
		Calendar c = Calendar.getInstance();
		c.set(entry.getYear(), entry.getMonth() - 1, entry.getDay(),
				entry.getHour(), entry.getMinute(), entry.getSecond());
		return new Timestamp(c.getTimeInMillis()) ;		
	}
	
	private Timestamp getTimestamp(int year, int month, int day, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, hour, minute, second);
		return new Timestamp(c.getTimeInMillis()) ;
	}
	
	private Response bucheZeitPersonalID(Integer personalIId, Timestamp timestamp, 
			Integer taetigkeitIId, String station, TheClientDto theClientDto) {
		ZeitdatenDto zd = new ZeitdatenDto();
		zd.setPersonalIId(personalIId) ;
		zd.setTZeit(timestamp);
		zd.setTaetigkeitIId(taetigkeitIId);
		
		if(station != null && station.trim().length() > 0) {
			zd.setCWowurdegebucht(station.trim()) ;
		}
		
		try {
			getServer().getZeiterfassungCall().createZeitdaten(
				zd, true, true, false, theClientDto) ;
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
		TaetigkeitDto taetigkeitDto = getServer().getZeiterfassungCall().taetigkeitFindByCNrSmall(cNr) ;
		return taetigkeitDto == null ? null : taetigkeitDto.getIId() ;
	}	
}
