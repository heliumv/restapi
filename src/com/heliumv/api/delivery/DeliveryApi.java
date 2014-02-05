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
package com.heliumv.api.delivery;

import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.ILieferscheinCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.lieferschein.service.LieferscheinDto;
import com.lp.util.EJBExceptionLP;

@Service("hvDelivery")
@Path("/api/v1/delivery/")
public class DeliveryApi extends BaseApi implements IDeliveryApi {

	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Autowired
	private ILieferscheinCall lieferscheinCall ;
	
	@Override
	@POST
	@Path("/aviso")
	@Produces({FORMAT_XML})
	public String createDispatchNotification(
			@QueryParam(Param.USERID) String userId, 
			@QueryParam(Param.DELIVERYID) Integer deliveryId, 
			@QueryParam(Param.DELIVERYCNR) String deliveryCnr) {
		try {
			if(connectClient(userId) == null) return null ;
			if(deliveryId != null) {
				LieferscheinDto lsDto = lieferscheinCall.lieferscheinFindByPrimaryKey(deliveryId) ; 
				return createDispatchNotificationImpl(lsDto) ;
			}
			
			if(!StringHelper.isEmpty(deliveryCnr)) {
				return createDispatchNotificationCnr(deliveryCnr) ;
			}

			respondBadRequestValueMissing(Param.DELIVERYID) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e);
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}

		return null;
	}

	private String createDispatchNotificationCnr(String deliveryCnr) {
		try {
			LieferscheinDto lsDto = lieferscheinCall.lieferscheinFindByCNr(deliveryCnr) ;
			if(lsDto == null) {
				respondNotFound(Param.DELIVERYCNR, deliveryCnr);
				return null ;
			}
			
			return createDispatchNotificationImpl(lsDto) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e);
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}

		return null;
	}

	private String createDispatchNotificationImpl(LieferscheinDto deliveryDto) throws NamingException, RemoteException {
		return null ;
//		return lieferscheinCall.createLieferscheinAviso(deliveryDto, globalInfo.getTheClientDto()) ;
	}
}
