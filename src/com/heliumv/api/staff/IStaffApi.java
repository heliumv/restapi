package com.heliumv.api.staff;

import java.util.List;

import javax.ws.rs.QueryParam;

public interface IStaffApi {
	/**
	 * Eine Liste aller Mitarbeiter ermitteln</br>
	 * 
	 * @param userId des am HELIUM V Servers angemeldeten Benutzers
	 * @param limit die Anzahl der auszugebenden Datens&auml;tze. Wird der Parameter nicht angegeben,
	 *  so werden bis zu 50 Datens&auml;tze geliefert. Wird 0 angegeben, werden alle S&auml;tze 
	 *  ausgegeben 
	 * @param startIndex ist jene (staffEntry.)Id ab der die Auflistung erfolgen soll. Speziell bei
	 *  seitenweiser Auflistung - Parameter limit ist gesetzt - hilfreich um den Startdatensatz der
	 *  Seite zu erhalten
	 * @return eine (leere) Liste aller Mitarbeiter auf die der angemeldete Benutzer zugreifen darf
	 */
	List<StaffEntry> getStaff(
			@QueryParam("userid") String userId,
			@QueryParam("limit") Integer limit,
			@QueryParam("startIndex") Integer startIndex) ;
}
