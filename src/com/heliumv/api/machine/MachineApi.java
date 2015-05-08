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
package com.heliumv.api.machine;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.api.production.IProductionApi;
import com.heliumv.api.worktime.DayTypeEntry;
import com.heliumv.factory.IMaschineCall;
import com.heliumv.factory.IZeiterfassungCall;
import com.heliumv.factory.query.MachineGroupQuery;
import com.heliumv.factory.query.MachineQuery;
import com.heliumv.tools.FilterKriteriumCollector;
import com.lp.server.personal.service.MaschinenVerfuegbarkeitsStundenDto;
import com.lp.server.util.fastlanereader.service.query.FilterBlock;
import com.lp.server.util.fastlanereader.service.query.QueryParameters;
import com.lp.server.util.fastlanereader.service.query.QueryResult;
import com.lp.util.EJBExceptionLP;

@Service("hvMachine")
@Path("/api/v1/machine/")
public class MachineApi extends BaseApi implements IMachineApi {	
	@Autowired
	private MachineQuery machineQuery ;
	
	@Autowired
	private MachineGroupQuery machineGroupQuery ;

	@Autowired
	private IMaschineCall maschineCall ;
	@Autowired
	private IZeiterfassungCall zeiterfassungCall ;
	@Autowired
	IProductionApi productionApi ;
	
	@GET
	@Produces({FORMAT_JSON, FORMAT_XML})
	public MachineEntryList getMachines(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex,			
			@QueryParam(Filter.HIDDEN) Boolean filterWithHidden) throws RemoteException, NamingException, EJBExceptionLP {
		if(connectClient(userId) == null) return new MachineEntryList() ;
		return getMachinesImpl(limit, startIndex, filterWithHidden);
	}


	private MachineEntryList getMachinesImpl(Integer limit, Integer startIndex,
			Boolean filterWithHidden)
			throws NamingException, RemoteException {
		MachineEntryList entries = new MachineEntryList() ;
		FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
		collector.add(machineQuery.getFilterWithHidden(filterWithHidden)) ;
		FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
		
		QueryParameters params = machineQuery.getDefaultQueryParameters(filterCrits) ;
		params.setLimit(limit) ;
		params.setKeyOfSelectedRow(startIndex) ;

		QueryResult result = machineQuery.setQuery(params) ;
		entries.setEntries(machineQuery.getResultList(result)) ;
		
		return entries ;
	}


	@GET
	@Path("/{" + Param.MACHINEID + "}/availability")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public MachineAvailabilityEntryList getAvaibilities(
			@PathParam(Param.MACHINEID) Integer machineId,
			@QueryParam(Param.USERID) String userId,
			@QueryParam("dateMs") Long dateMs,
			@QueryParam("days") Integer days,
			@QueryParam(With.DESCRIPTION) Boolean withDescription) throws RemoteException, NamingException, EJBExceptionLP {
		if(connectClient(userId) == null) return new MachineAvailabilityEntryList() ;
		
		return getAvailabilitiesImpl(machineId, dateMs, days, withDescription);
	}


	private MachineAvailabilityEntryList getAvailabilitiesImpl(
			Integer machineId, Long dateMs, Integer days,
			Boolean withDescription)
			throws NamingException, RemoteException {
		MachineAvailabilityEntryList entries = new MachineAvailabilityEntryList() ;
		
		Date d = null ;
		if(dateMs == null) {
			d = new Date(System.currentTimeMillis()) ;
		} else {
			d = new Date(dateMs) ;
		}
		
		if(days == null) {
			days = new Integer(1) ;
		} else {
			if(days < 0) {
				respondBadRequest("days", "value = '" + days + "' is not >= 0.");
				return entries ;
			}
		}
		
		if(withDescription == null) {
			withDescription = new Boolean(false) ;
		}
		
		List<MaschinenVerfuegbarkeitsStundenDto> dtos =
				maschineCall.getVerfuegbarkeitStunden(machineId, d, days) ;

		Map<Integer, DayTypeEntry> mapTypes = null ;
		
		if(withDescription) {
			List<DayTypeEntry> dayTypes = zeiterfassungCall.getAllSprTagesarten() ;
			mapTypes = new HashMap<Integer, DayTypeEntry>();
			for (DayTypeEntry dayTypeEntry : dayTypes) {
				mapTypes.put(dayTypeEntry.getId(), dayTypeEntry) ;
			}
		}
		
		List<MachineAvailabilityEntry> apiDtos = new ArrayList<MachineAvailabilityEntry>() ;
		for (MaschinenVerfuegbarkeitsStundenDto dto : dtos) {
			MachineAvailabilityEntry entry = new MachineAvailabilityEntry() ;
			entry.setMachineId(dto.getMaschineId());
			entry.setDayTypeId(dto.getTagesartId());
			entry.setAvailabilityHours(dto.getVerfuegbarkeitH()) ;
			entry.setDateMs(dto.getDate().getTime()) ;
			if(mapTypes != null) {
				if(mapTypes.get(dto.getTagesartId()) == null) {
					entry.setDayTypeDescription("Unbekannt (" + dto.getTagesartId() + ")." );
				} else {
					entry.setDayTypeDescription(mapTypes.get(dto.getTagesartId()).getDescription());
				}
			}
			apiDtos.add(entry) ;
		}
		
		entries.setEntries(apiDtos) ;
		return entries ;
	}
	
	@GET
	@Path("/groups")
	@Produces({FORMAT_JSON, FORMAT_XML})
	public MachineGroupEntryList getMachineGroups(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex) throws RemoteException, NamingException, EJBExceptionLP {
		if(connectClient(userId) == null) return new MachineGroupEntryList() ;
		return getMachineGroupsImpl(limit, startIndex);
	}


	private MachineGroupEntryList getMachineGroupsImpl(
			Integer limit, Integer startIndex) throws NamingException, RemoteException {
		MachineGroupEntryList entries = new MachineGroupEntryList() ;
		FilterKriteriumCollector collector = new FilterKriteriumCollector() ;
		FilterBlock filterCrits = new FilterBlock(collector.asArray(), "AND")  ;
		
		QueryParameters params = machineGroupQuery.getDefaultQueryParameters(filterCrits) ;
		params.setLimit(limit) ;
		params.setKeyOfSelectedRow(startIndex) ;

		QueryResult result = machineGroupQuery.setQuery(params) ;
		entries.setEntries(machineGroupQuery.getResultList(result)) ;
		
		return entries ;
	}

	@GET
	@Path("/planningview")
	public PlanningView getPlanningView(
			@QueryParam(Param.USERID) String userId,
			@QueryParam("dateMs") Long dateMs,
			@QueryParam("days") Integer days,
			@QueryParam(Param.LIMIT) Integer limit,
			@QueryParam(Param.STARTINDEX) Integer startIndex,			
			@QueryParam(Filter.HIDDEN) Boolean filterWithHidden,
			@QueryParam("filter_startdate") Boolean filterStartDate,
			@QueryParam(With.DESCRIPTION) Boolean withDescription) throws RemoteException, NamingException, EJBExceptionLP {
		PlanningView planningView = new PlanningView() ;
		if(connectClient(userId) == null) return planningView ;
		
		planningView.setMachineList(getMachinesImpl(limit, startIndex, filterWithHidden)) ;

		if(filterStartDate == null) {
			filterStartDate = new Boolean(false) ;
		}
		
		if(dateMs == null) {
			dateMs = new Long(System.currentTimeMillis()) ;
		}
		if(days == null) {
			days = new Integer(1) ;
		}
		
		Calendar c = Calendar.getInstance() ;
		c.setTimeInMillis(dateMs);
		c.add(Calendar.DAY_OF_YEAR, days);
		long endDateMs = c.getTimeInMillis() ;
		planningView.setOpenWorkList(productionApi.getOpenWorkEntriesImpl(
				limit, startIndex, filterStartDate ? dateMs : null, endDateMs)) ;
		planningView.setMachineGroupList(getMachineGroupsImpl(limit, startIndex));
		Map<Integer, MachineAvailabilityEntryList> mapAvailability = new HashMap<Integer, MachineAvailabilityEntryList>() ;
		for (MachineEntry machine : planningView.getMachineList().getEntries()) {
			mapAvailability.put(machine.getId(), 
				getAvailabilitiesImpl(machine.getId(), dateMs, days, withDescription)) ;
		}
		planningView.setMachineAvailabilityMap(mapAvailability);
		return planningView ;
	}
}
