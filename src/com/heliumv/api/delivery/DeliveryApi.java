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
