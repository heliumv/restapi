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

import java.util.List;

import javax.ws.rs.core.Response;

import com.heliumv.api.item.ItemEntry;
import com.heliumv.api.order.OrderApi;
import com.heliumv.api.production.ProductionApi;
import com.heliumv.api.project.ProjectApi;


/**
 * Funktionalit&auml;t rund um die Zeit(daten)erfassung</br>
 *
 * @author Gerold
 */
public interface IWorktimeApi {
	/**
	 * Eine KOMMT-Buchung durchf&uuml;hren.</br>
	 * @param entry ist die Standardzeitbuchung Datenstruktur <code>TimeRecordingEntry</code>
	 * @return
	 */
	Response bookComing(TimeRecordingEntry entry) ;
	
	/**
	 * Eine GEHT-Buchung durchf&uuml;hren.</br>
	 * @param entry ist die Standardzeitbuchung Datenstruktur <code>TimeRecordingEntry</code>
	 * @return
	 */
	Response bookGoing(TimeRecordingEntry entry) ;

	/**
	 * Eine PAUSE (Unterbrechung)-Buchung durchf&uuml;hren.</br>
	 * <p>Eine Pause (zum Beispiel Mittagspause) wird durch <b>zwei</b> PAUSE
	 * Buchungen erzielt.</p>
	 * @param entry ist die Standardzeitbuchung Datenstruktur <code>TimeRecordingEntry</code>
	 * @return
	 */
	Response bookPausing(TimeRecordingEntry entry) ;

	/**
	 * Eine ENDE Buchung durchf&uuml;hren</br>
	 * <p>Eine Belegbuchung wie beispielsweise Auftrags-, Projekt oder Los-Buchung beenden</p>
	 * @param entry
	 * @return
	 */
	Response bookStopping(TimeRecordingEntry entry) ;

//	Response bookComing(String userId,
//			Integer year, Integer month, Integer day,
//			Integer hour, Integer minute, Integer second) ;

	/** 
	 * Eine (Beginn) Buchung eines Los durchf&uuml;hren.
	 * 
	 * @param entry ist die Datenstruktur zur Speicherung einer Los-Buchung</br>
	 * <p>Die anzugebende Los-Id kann &uuml;ber die Resource <code>production</code> ermittelt werden @see {@link ProductionApi}
	 * {@link ProductionApi} </p>
	 * 
	 * @return
	 */
	Response bookProduction(ProductionRecordingEntry entry) ;

	/** 
	 * Eine (Beginn) Buchung eines Projekts durchf&uuml;hren.
	 * 
	 * @param entry ist die Datenstruktur zur Speicherung einer Projekt-Buchung</br>
	 * <p>Die anzugebende Project-Id kann &uuml;ber die Resource <code>project</code> ermittelt werden
	 * @see {@link ProjectApi} </p>
	 * @return
	 */
	Response bookProject(ProjectRecordingEntry entry) ;
	
	/**
	 * Eine (Beginn) Buchung mit Auftragsbezug erzeugen.</br>
	 * <p>Die anzugebende Order-Id kann &uuml;ber die Resource <code>order</code> ermittelt werden
	 * @see {@link OrderApi} </p>
	 * <p>Die anzugebende Position-Id kann &&uml;ber die Resource <code>order/{orderId}/position</code>
	 * ermittelt werden.</p>
	 *
	 * @param entry ist dabei die Auftragszeit Datenstruktur
	 * @return
	 */
	Response bookOrder(OrderRecordingEntry entry) ;

	/**
	 * Liefert eine Liste aller verf&uuml;gbaren T&auml;tigkeiten (Arbeitszeitartikel) die innerhalb der Zeiterfassung 
	 * durchgef&uuml;hrt werden k&ouml;nnen. </br>
	 * 
	 * @param userId des am HELIUM V Servers angemeldeten Benutzers
	 * @param limit (optional) die maximale Anzahl von gelieferten Eintr&auml;gen. Default ist 50.
	 * @param startIndex (optional) die Id (eines <code>ItemEntry</code> Eintrags, mit dem die Liste beginnen soll
	 * @param filterCnr (optional) die Sondert&auml;tigkeiten auf diese Kennung einschr&auml;nken
	 * @return eine (leere) Liste der f&uuml;r den Benutzer verf&uuml;gbaren T&auml;tigkeiten
	 */
	List<ItemEntry> getActivities(
			String userId,
			Integer limit,
			Integer startIndex,String filterCnr);

	/**
	 * Liefert eine Liste aller verf&uuml;gbaren Sondert&auml;tigkeiten die f&uuml;r die Zeiterfassung
	 * zur Verf&uuml;gung stehen.</br>
	 * <p>Sondert&auml;tigkeiten sind T&auml;tigkeiten wie "KOMMT", "GEHT", "ARZT", "BEH&Ouml;RDE", ...</p>
	 * <p>Es k&ouml;nnen nur jene Sondert&auml;tigkeiten gebucht werden, die laut HELIUM V Konfiguration
	 * f&uuml;r den Anwender beziehungsweise dessen Benutzerrolle zur Verf&uuml;gung steht.</p>
	 * @param userId
	 * @return eine (leere) Liste der f&uuml;r den Benutzer verf&uuml;gbaren Sondert&auml;tigkeiten
	 */
	public List<SpecialActivity> getSpecialActivities(String userId) ;
	
	/**
	 * Liefert eine Liste aller verf&uuml;gbaren Belegarten die f&uuml;r die Zeiterfassung verwendet werden k&ouml;nnen.</br>
	 * <p>Belegarten sind typischerweise Angebot, Auftrag, Los oder Projekt</p>
	 * 
	 * @param userId der am HELIUM V Server angemeldete Benutzer
	 * @return eine (leere) Liste von verf&uuml;gbaren/bebuchbaren Belegarten
	 */
	List<DocumentType> getDocumentTypes(String userId) ; 

	/**
	 * Liefert alle <code>ZeitdatenEntry</code> f&uuml;r den angegebenen Tag.</br>
	 * 
	 * @param userId enth&auml;lt den angemeldeten Benutzer
	 * @param year ist das Jahr f&uuml;r das die Zeitdaten abgerufen werden sollen
	 * @param month ist das Monat (1-12) 
	 * @param day der Tag (1-31)
	 * @param forStaffId ist jene Benutzer-Id f&uuml;r welche die Zeitdaten abgerufen werden sollen. Kann auch leer sein
	 * @param limit Ist die maximale Anzahl an Datens&auml;tzen. Default 50.
	 * @return eine (leere) Liste von <code>ZeitdatenEntry </code> f&uuml;r den gew&uuml;nschten Tag
	 */
	List<ZeitdatenEntry> getWorktimeEntries(
			String userId,
			Integer year,
			Integer month,
			Integer day,
			Integer forStaffId,
			Integer limit) ;
	
	/**
	 * Eine Zeitbuchung l&ouml;schen
	 * 
	 * @param userId der angemeldete Benutzer
	 * @param worktimeId die Id der zu l&ouml;schenden Buchung.</br><p>Die Zeitbuchungen (und damit auch deren Id)
	 * kann &uuml;ber die GET Methode ermittelt werden.</p>
	 * @param forStaffId ist die optionale PersonalId f&uuml;r die gel&ouml;scht werden soll. 
	 * <p>Der angemeldete Benutzer kann f&uuml;r jene Personen f&uuml;r die er ausreichend Rechte hat Zeitbuchungen l&ouml;schen.
	 * </p>
	 * <p>Wird sie nicht angegeben, so wird der Zeitbuchung mit der angemeldeten Person verkn&uuml;pft.
	 */
	void removeWorktime(
			String userId,
			Integer worktimeId,
			Integer forStaffId) ;
}
