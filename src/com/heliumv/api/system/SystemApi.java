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
package com.heliumv.api.system;

import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.ISystemCall;

@Service("hvSystem")
@Path("/api/v1/system/")
public class SystemApi extends BaseApi implements ISystemApi {

	@Autowired
	private ISystemCall systemCall ;
	
	@GET
	@Path("/ping")
	@Produces({FORMAT_JSON, FORMAT_XML, "text/html"})
	@Override
	public PingResult ping() {
		PingResult result = new PingResult() ;
		try {
			long startMs = System.currentTimeMillis() ;
			result.setApiTime(startMs) ;
			result.setServerBuildNumber(systemCall.getServerBuildNumber()) ;
			result.setServerVersionNumber(systemCall.getServerVersion()) ;
			result.setServerTime(systemCall.getServerTimestamp().getTime());
			long stopMs = System.currentTimeMillis() ;
			result.setServerDuration(Math.abs(stopMs - startMs));
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
 		return result ;
	}

	@GET
	@Path("/localping")
	@Produces({FORMAT_JSON, FORMAT_XML, "text/html"})
	@Override
	public LocalPingResult localping() {
		return localpingImpl() ;
	}	
	
//	@GET
//	@Path("/localping")
//	@Produces({"text/html"})
//	@Override
//	public String localpingAsText() {
//		LocalPingResult result = localpingImpl();
//		return result.toString() ;
//	}		

	private LocalPingResult localpingImpl() {
		LocalPingResult result = new LocalPingResult() ;
		result.setApiTime(System.currentTimeMillis());
		result.setApiBuildNumber(1);
		result.setApiVersionNumber("1.0.1") ;
		return result ;		
	}
}
