package com.heliumv.api.soapcalls;

import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.impl.PersonalApiCall;
import com.lp.util.EJBExceptionLP;

/**
 * Funktionalit&auml;t rund um die Resource <b>Personal</b></br>
 * <p>Es handelt sich um bereits bestehende Soap-Calls die nun 
 * zus&auml;tzlich als REST Call zur Verf&uuml;gung stehen. 
 * </p>
 * @author Gerold
 */
@Service("hvSoapCallsPersonal")
@Path("/api/soapcalls/personal")
public class SoapCallsPersonalApi extends BaseApi implements ISoapCallsPersonalApi {
	public static class SoapParam {
		public final static String SERIALNRREADER = "serialnoreader" ;
		public final static String USERID = "idUser" ;
		public final static String STATION = "station" ;	
		public final static String IDCARD  = "idCard" ;
		
		public final static String PRODUCTIONCNR = "productionCnr" ;
		public final static String AMOUNT  = "amount" ;

		public final static String ITEMCNR = "itemCnr" ;
		public final static String SERIALNR = "serialnr" ;
		
	}

	@Autowired
	private PersonalApiCall personalApiCall ;
	
	@POST
	@Path("/losgroessenaenderung")
	@Produces({FORMAT_JSON, FORMAT_XML})
	@Override
	public SoapCallPersonalResult bucheLosGroessenAenderung(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(SoapParam.SERIALNRREADER) String serialNrReader,
			@QueryParam(SoapParam.STATION) String station,
			@QueryParam(SoapParam.PRODUCTIONCNR) String productionCnr,
			@QueryParam(SoapParam.AMOUNT) Integer amount,
			@QueryParam(SoapParam.IDCARD) String idCard) throws NamingException, RemoteException, EJBExceptionLP {
		SoapCallPersonalResult result = new SoapCallPersonalResult(
				personalApiCall.bucheLosGroessenAenderung(serialNrReader, userId, station, productionCnr, amount, idCard)) ;
		return result ;
	}

	@POST
	@Path("/losablieferung")
	@Produces({FORMAT_JSON, FORMAT_XML})
	@Override
	public SoapCallPersonalResult bucheLosAblieferung(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(SoapParam.SERIALNRREADER) String serialNrReader,
			@QueryParam(SoapParam.STATION) String station,
			@QueryParam(SoapParam.PRODUCTIONCNR) String productionCnr,
			@QueryParam(SoapParam.AMOUNT) Integer amount,			
			@QueryParam(SoapParam.IDCARD) String idCard)
			throws NamingException, RemoteException {
		SoapCallPersonalResult result = new SoapCallPersonalResult(
				personalApiCall.bucheLosAblieferung(serialNrReader, userId, station, productionCnr, amount, idCard)) ;
		return result ;
	}	
	
	@POST
	@Path("/losablieferungsnr")
	@Produces({FORMAT_JSON, FORMAT_XML})
	@Override
	public SoapCallPersonalResult bucheLosAblieferungSeriennummer(
			@QueryParam(Param.USERID) String userId,
			@QueryParam(SoapParam.STATION) String station,
			@QueryParam(SoapParam.PRODUCTIONCNR) String productionCnr,
			@QueryParam(SoapParam.ITEMCNR) String itemCnr,
			@QueryParam(SoapParam.SERIALNR) String serialNr,
			@QueryParam("version") String version) throws NamingException,
			RemoteException {
		SoapCallPersonalResult result = new SoapCallPersonalResult(
				personalApiCall.bucheLosAblieferungSeriennummer(userId, station, productionCnr, itemCnr, serialNr, version));
		return result ;
	}
}
