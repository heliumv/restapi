package com.heliumv.api.cc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.codec.Charsets;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heliumv.api.BaseApi;
import com.heliumv.factory.IAuftragRestCall;
import com.heliumv.factory.IGlobalInfo;
import com.heliumv.factory.ILieferscheinCall;
import com.heliumv.tools.StringHelper;
import com.lp.server.artikel.service.BaseRequestResult;
import com.lp.server.auftrag.service.CreateOrderResult;
import com.lp.server.lieferschein.service.LieferscheinDto;
import com.lp.server.system.service.WebshopAuthHeader;
import com.lp.util.EJBExceptionLP;
import com.lp.util.Helper;

@Service("hvClevercure")
@Path("/api/beta/cc/")
public class ClevercureApi extends BaseApi implements IClevercureApi {

	@Autowired
	private IGlobalInfo globalInfo ;
	
	@Autowired
	private ILieferscheinCall lieferscheinCall ;
	
	public static class Param {
		public final static String TOKEN = "token" ;
		public final static String COMPANYCODE = "companycode" ;
		public final static String DATATYPE = "datatype" ;
	}

	@Autowired
	IAuftragRestCall auftragRestCall ;
	
	@Override
	@POST
	@Consumes("text/xml")
	public void receiveAnyCCData(
			@QueryParam(Param.COMPANYCODE ) String companyCode,
			@QueryParam(Param.TOKEN) String token,
//			@QueryParam("user") String user,
//			@QueryParam("password") String password, 
			@QueryParam(Param.DATATYPE) @DefaultValue("osd") String datatype, 
			String ccdata) {
		if(StringHelper.isEmpty(Param.COMPANYCODE)) {
			respondBadRequestValueMissing(Param.COMPANYCODE) ;
		}

		try {
			if ("osd".equals(datatype)) {
//				try {
//					String oldString = "\u00DF";
//					String newString = new String(oldString.getBytes("UTF-8"), "UTF-8");
//					System.out.println(newString.equals(oldString));
//				} catch(UnsupportedEncodingException e) {
//					System.out.println("use " +  e.getMessage()) ;
//				}
//
				String encoded = null ;
				try {
					encoded = new String(ccdata.getBytes("UTF-8"), "UTF-8") ;
				} catch(UnsupportedEncodingException e) {					
					System.out.println("uee " +  e.getMessage()) ;
				}
				
//				receiveCCDataOsd(companyCode, token, ccdata);
				receiveCCDataOsd(companyCode, token, encoded);
				return;
			}
			
			respondNotFound();
		} catch (RemoteException e) {
			respondUnavailable(e);
		} catch (NamingException e) {
			respondUnavailable(e);
		}
	}
	

	@Override
	@POST
	@Path("/multipart")
	@Consumes("multipart/mixed;type=text/xml")
	public void receiveAnyCCDataMultipart(
			@QueryParam(Param.COMPANYCODE) String companyCode,
			@QueryParam(Param.TOKEN) String token,
//			@QueryParam("user") String user,
//			@QueryParam("password") String password,
			@QueryParam(Param.DATATYPE) @DefaultValue("osd") String datatype,
			@Multipart(value="file", type="text/xml") String ccdata) {				
		if(StringHelper.isEmpty(ccdata)) {
			respondBadRequestValueMissing("file");
		}
		
		try {
			if ("osd".equals(datatype)) {
				receiveCCDataOsd(companyCode, token, ccdata);
				return;
			}
			
			respondNotFound();
		} catch (RemoteException e) {
			respondUnavailable(e);
		} catch (NamingException e) {
			respondUnavailable(e);
		}
	}
	
	private void receiveCCDataOsd(String companyCode, String token, String ccdata)  throws NamingException, RemoteException  {
		Context env = (Context) new InitialContext().lookup("java:comp/env") ;
		String hvUser = (String) env.lookup("heliumv.credentials.user") ;
		String hvPassword= (String) env.lookup("heliumv.credentials.password") ;
		String hvWebshop = (String) env.lookup("heliumv.credentials.webshop") ;
		Boolean hvStoreReceivedData = (Boolean) env.lookup("heliumv.cc.data.storebefore") ;
	
		System.out.println("Received osd data") ;

		if(hvStoreReceivedData) {
			persistOsdData(ccdata) ;
		}
		
		WebshopAuthHeader authHeader = new WebshopAuthHeader() ;
		authHeader.setUser(hvUser) ;
		authHeader.setPassword(hvPassword) ;
//		authHeader.setIsoCountry("AT") ;
//		authHeader.setIsoLanguage("de") ;
		authHeader.setShopName(hvWebshop) ;
		authHeader.setToken(token) ;
		
		try {
			CreateOrderResult result = auftragRestCall.createOrder(authHeader, ccdata) ;
			if(Helper.isOneOf(result.getRc(), new int[]{
				CreateOrderResult.ERROR_EMPTY_ORDER, CreateOrderResult.ERROR_JAXB_EXCEPTION,
				CreateOrderResult.ERROR_SAX_EXCEPTION, CreateOrderResult.ERROR_UNMARSHALLING})) {
				persistOsdData(ccdata, "error");
				respondBadRequest(result.getRc());
				return ;
			}
			if(result.getRc() == CreateOrderResult.ERROR_AUTHENTIFICATION) {
				respondForbidden();
				persistOsdData(ccdata, "error_auth_.xml");
				return ;
			}
			if(result.getRc() == CreateOrderResult.ERROR_CUSTOMER_NOT_FOUND) {
				respondNotFound() ;
				persistOsdData(ccdata, "error_customer_.xml");
				return ;
			}

			if(result.getRc() >= CreateOrderResult.ERROR_EJB_EXCEPTION) {
				respondBadRequest(result.getRc()) ;
				persistOsdData(ccdata, "error_ejb_.xml");
				return ;
			}
			
			if(result.getRc() == BaseRequestResult.OKAY){
				respondOkay() ;
				persistOsdData(ccdata, "200_.xml");
			} else {
				respondExpectationFailed(result.getRc()) ;
			}
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;

			persistOsdData(ccdata, "error_.xml");
		}
	}

	private void persistOsdData(String ccdata, String fileSuffix) {
		persistDatatype("Osd", ccdata, fileSuffix) ;
	}

	private void persistDatatype(String datatype, String ccdata, String fileSuffix) {
		try {
			File f = File.createTempFile("CC" + datatype + "_", fileSuffix != null ? ("_" + fileSuffix) : "_.xml") ;
			FileWriter fw = new FileWriter(f) ;
			fw.write(ccdata) ;
			fw.close() ;
			System.out.println("Stored osd data to file '" + f.getName() + "'.") ;
		} catch(IOException e) {
			System.out.println("Can't write to file " + e.getMessage()) ;
		}				
	}

	private void persistOsdData(String ccdata) {
		persistOsdData(ccdata, null);
	}
	
	private void persistDndData(String ccdata, String fileSuffix) {
		persistDatatype("Dnd", ccdata, fileSuffix) ;
	}	

	@Override
	@POST
	@Path("/aviso")
	@Produces({FORMAT_XML})
	public String createDispatchNotification(
			@QueryParam(BaseApi.Param.USERID) String userId, 
			@QueryParam(BaseApi.Param.DELIVERYID) Integer deliveryId, 
			@QueryParam(BaseApi.Param.DELIVERYCNR) String deliveryCnr,
			@QueryParam("post") @DefaultValue(value="false") Boolean doPost) {
		try {
			if(connectClient(userId) == null) return null ;
			if(deliveryId != null) {
				return createDispatchNotificationId(deliveryId, doPost) ;
			}
			
			if(!StringHelper.isEmpty(deliveryCnr)) {
				return createDispatchNotificationCnr(deliveryCnr, doPost) ;
			}

			respondBadRequestValueMissing(BaseApi.Param.DELIVERYID) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		} catch(RemoteException e) {
			respondUnavailable(e);
		} catch(EJBExceptionLP e) {
			respondBadRequest(e) ;
		}

		return null;
	}

	private String createDispatchNotificationId(Integer deliveryId, Boolean doPost) throws NamingException, RemoteException {
		LieferscheinDto lsDto = lieferscheinCall.lieferscheinFindByPrimaryKey(deliveryId) ; 
		if(lsDto == null) {
			respondNotFound(BaseApi.Param.DELIVERYID, deliveryId.toString());
			return null ;				
		}
		
		return createDispatchNotificationImpl(lsDto, doPost) ;
	}

	private String createDispatchNotificationCnr(String deliveryCnr, Boolean doPost) throws NamingException, RemoteException {
		LieferscheinDto lsDto = lieferscheinCall.lieferscheinFindByCNr(deliveryCnr) ;
		if(lsDto == null) {
			respondNotFound(BaseApi.Param.DELIVERYCNR, deliveryCnr);
			return null ;
		}
		
		return createDispatchNotificationImpl(lsDto, doPost) ;
	}

	private String createDispatchNotificationImpl(LieferscheinDto deliveryDto, Boolean doPost) throws NamingException, RemoteException {
		String avisoContent = null ;
		if(!doPost) {
			avisoContent = lieferscheinCall.createLieferscheinAvisoToString(deliveryDto, globalInfo.getTheClientDto()) ;
		} else {
			avisoContent = lieferscheinCall.createLieferscheinAvisoPost(deliveryDto, globalInfo.getTheClientDto());
			if(avisoContent != null) {
				persistDndData(avisoContent, null) ;
			}
		}
		
		return avisoContent ;
	}
	

//	private StatusLine postToCleverCure(String datatype, String content) throws NamingException, ClientProtocolException, IOException {
//		Context env = (Context) new InitialContext().lookup("java:comp/env") ;
//		String ccEndpoint = (String) env.lookup("clevercure.endpoint") ;
//		String uri = ccEndpoint + "&datatype=" + datatype ;
//		
//		HttpPost post = new HttpPost(uri) ;
//		StringEntity entity = new StringEntity(content, Charsets.UTF_8) ;
//		entity.setContentType("text/xml") ;
//		post.setEntity(entity) ;
//
//		HttpClient client = new DefaultHttpClient() ;
//		HttpResponse response = client.execute(post) ;
//		StatusLine status = response.getStatusLine() ;
////		HttpEntity anEntity = response.getEntity() ;
////		InputStream s = anEntity.getContent() ;
////		BufferedReader br = new BufferedReader(new InputStreamReader(s)) ;
////		String theContent = "" ;
////		String line = ""; 
////		while((line = br.readLine()) != null) {
////			theContent += line + "\n" ;
////		}		
//		return status ;
//	}
}
