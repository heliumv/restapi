package com.heliumv.api.worktime;

import java.util.List;

import javax.ws.rs.core.Response;

import com.heliumv.api.item.ItemEntry;


public interface IWorktimeApi {
	Response bookComing(TimeRecordingEntry entry) ;
	
	Response bookGoing(TimeRecordingEntry entry) ;

	Response bookPausing(TimeRecordingEntry entry) ;

	Response bookComing(String userId,
			Integer year, Integer month, Integer day,
			Integer hour, Integer minute, Integer second) ;

	Response bookProduction(ProductionRecordingEntry entry) ;

	Response bookOrder(OrderRecordingEntry entry) ;

	/**
	 * Verfuegbare Sondertaetigketen fuer die Zeiterfassung durchgefuehrt werden kann
	 * 
	 * @param userId
	 * @return eine (leere) Liste von Sondertaetigkeiten
	 */
	List<ItemEntry> getActivities(
			String userId,
			Integer limit,
			Integer startIndex,String filterCnr);
	
	/**
	 * Verfuegbare Belegarten fuer die Zeiterfassung durchgefuehrt werden kann
	 * 
	 * @param userId
	 * @return eine (leere) Liste von verfuegbaren/bebuchbaren Belegarten
	 */
	List<DocumentType> getDocumentTypes(String userId) ; 

}
