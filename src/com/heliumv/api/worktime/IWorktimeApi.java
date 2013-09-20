package com.heliumv.api.worktime;

import java.util.List;

import javax.ws.rs.core.Response;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.api.order.OrderApi;
import com.heliumv.api.production.ProductionApi;
import com.heliumv.api.project.ProjectApi;


/**
 * Funktionalität rund um die Zeit(daten)erfassung</br>
 *
 * @author Gerold
 */
public interface IWorktimeApi {
	/**
	 * Eine KOMMT-Buchung durchführen.</br>
	 * @param entry ist die Standardzeitbuchung Datenstruktur <code>TimeRecordingEntry</code>
	 * @return
	 */
	Response bookComing(TimeRecordingEntry entry) ;
	
	/**
	 * Eine GEHT-Buchung durchführen.</br>
	 * @param entry ist die Standardzeitbuchung Datenstruktur <code>TimeRecordingEntry</code>
	 * @return
	 */
	Response bookGoing(TimeRecordingEntry entry) ;

	/**
	 * Eine PAUSE (Unterbrechung)-Buchung durchführen.</br>
	 * <p>Eine Pause (zum Beispiel Mittagspause) wird durch <b>zwei</b> PAUSE
	 * Buchungen erzielt.</p>
	 * @param entry ist die Standardzeitbuchung Datenstruktur <code>TimeRecordingEntry</code>
	 * @return
	 */
	Response bookPausing(TimeRecordingEntry entry) ;

	/**
	 * Eine ENDE Buchung durchführen</br>
	 * <p>Eine Belegbuchung wie beispielsweise Auftrags-, Projekt oder Los-Buchung beenden</p>
	 * @param entry
	 * @return
	 */
	Response bookStopping(TimeRecordingEntry entry) ;

//	Response bookComing(String userId,
//			Integer year, Integer month, Integer day,
//			Integer hour, Integer minute, Integer second) ;

	/** 
	 * Eine (Beginn) Buchung eines Los durchführen.
	 * 
	 * @param entry ist die Datenstruktur zur Speicherung einer Los-Buchung</br>
	 * <p>Die anzugebende Los-Id kann über die Resource <code>production</code> ermittelt werden @see {@link ProductionApi}
	 * {@link ProductionApi} </p>
	 * 
	 * @return
	 */
	Response bookProduction(ProductionRecordingEntry entry) ;

	/** 
	 * Eine (Beginn) Buchung eines Projekts durchführen.
	 * 
	 * @param entry ist die Datenstruktur zur Speicherung einer Projekt-Buchung</br>
	 * <p>Die anzugebende Project-Id kann über die Resource <code>project</code> ermittelt werden
	 * @see {@link ProjectApi} </p>
	 * @return
	 */
	Response bookProject(ProjectRecordingEntry entry) ;
	
	/**
	 * Eine (Beginn) Buchung mit Auftragsbezug erzeugen.</br>
	 * <p>Die anzugebende Order-Id kann über die Resource <code>order</code> ermittelt werden
	 * @see {@link OrderApi} </p>
	 *
	 * @param entry ist dabei die Auftragszeit Datenstruktur
	 * @return
	 */
	Response bookOrder(OrderRecordingEntry entry) ;

	/**
	 * Liefert eine Liste aller verfügbaren Tätigkeiten(Artikel) die innerhalb der Zeiterfassung 
	 * durchgeführt werden können. </br>
	 * 
	 * @param userId des am HELIUM V Servers angemeldeten Benutzers
	 * @param limit (optional) die maximale Anzahl von gelieferten Einträgen. Default ist 50.
	 * @param startIndex (optional) die Id (eines <code>ItemEntry</code> Eintrags, mit dem die Liste beginnen soll
	 * @param filterCnr (optional) die Sondertätigkeiten auf diese Kennung einschränken
	 * @return eine (leere) Liste der für den Benutzer verfügbaren Sondertätigkeiten
	 */
	List<ItemEntry> getActivities(
			String userId,
			Integer limit,
			Integer startIndex,String filterCnr);
	
	/**
	 * Liefert eine Liste aller verfügbaren Belegarten die für die Zeiterfassung verwendet werden können.</br>
	 * <p>Belegarten sind typischerweise Angebot, Auftrag, Los oder Projekt</p>
	 * 
	 * @param userId der am HELIUM V Server angemeldete Benutzer
	 * @return eine (leere) Liste von verfuegbaren/bebuchbaren Belegarten
	 */
	List<DocumentType> getDocumentTypes(String userId) ; 

	/**
	 * Liefert alle <code>ZeitdatenEntry</code> für den angegebenen Tag.</br>
	 * 
	 * @param userId enthält den angemeldeten Benutzer
	 * @param year ist das Jahr für das die Zeitdaten abgerufen werden sollen
	 * @param month ist das Monat (1-12) 
	 * @param day der Tag (1-31)
	 * @param forStaffId ist jene Benutzer-Id für welche die Zeitdaten abgerufen werden sollen. Kann auch leer sein
	 * @param limit Ist die maximale Anzahl an Datensätzen. Default 50.
	 * @return eine (leere) Liste von <code>ZeitdatenEntry </code> für den gewünschten Tag
	 */
	List<ZeitdatenEntry> getWorktimeEntries(
			String userId,
			Integer year,
			Integer month,
			Integer day,
			Integer forStaffId,
			Integer limit) ;
	
	/**
	 * Eine Zeitbuchung löschen
	 * 
	 * @param userId der angemeldete Benutzer
	 * @param worktimeId die Id der zu löschenden Buchung.</br><p>Die Zeitbuchungen (und damit auch deren Id)
	 * kann über die GET ermittelt werden.</p>
	 * @param forStaffId ist die optionale PersonalId für die gelöscht werden soll. 
	 * <p>Der angemeldete Benutzer kann für jene Personen für die er ausreichend Rechte hat Zeitbuchungen löschen.
	 * </p>
	 * <p>Wird sie nicht angegeben, so wird der Zeitbuchung mit der angemeldeten Person verknüpft.
	 */
	void removeWorktime(
			String userId,
			Integer worktimeId,
			Integer forStaffId) ;
}
