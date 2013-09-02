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
	@Produces({FORMAT_JSON, FORMAT_XML})
	@Override
	public PingResult ping() {
		PingResult result = new PingResult() ;
		try {
			result.setApiTime(System.currentTimeMillis()) ;
			result.setServerBuildNumber(systemCall.getServerBuildNumber()) ;
			result.setServerVersionNumber(systemCall.getServerVersion()) ;
			result.setServerTime(systemCall.getServerTimestamp().getTime());
		} catch(RemoteException e) {
			respondUnavailable(e) ;
		} catch(NamingException e) {
			respondUnavailable(e) ;
		}
		
 		return result ;
	}
}
