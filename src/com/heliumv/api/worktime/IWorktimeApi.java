package com.heliumv.api.worktime;

import java.util.List;

import javax.ws.rs.core.Response;


public interface IWorktimeApi {
	Response bookComing(TimeRecordingEntry entry) ;
	
	Response bookGoing(TimeRecordingEntry entry) ;

	Response bookPausing(TimeRecordingEntry entry) ;

	Response bookComing(String userId,
			Integer year, Integer month, Integer day,
			Integer hour, Integer minute, Integer second) ;

	/**
	 * Verfuegbare Sondertaetigketen fuer die Zeiterfassung durchgefuehrt werden kann
	 * 
	 * @param userId
	 * @return eine (leere) Liste von Sondertaetigkeiten
	 */
	List<SpecialActivity> getActivities(String userId) ;
	
	/**
	 * Verfuegbare Belegarten fuer die Zeiterfassung durchgefuehrt werden kann
	 * 
	 * @param userId
	 * @return eine (leere) Liste von verfuegbaren/bebuchbaren Belegarten
	 */
	List<DocumentType> getDocumentTypes(String userId) ; 

}
