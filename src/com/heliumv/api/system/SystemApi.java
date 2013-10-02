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
	@Produces({FORMAT_JSON, FORMAT_XML})
	@Override
	public LocalPingResult localping() {
		LocalPingResult result = new LocalPingResult() ;
		result.setApiTime(System.currentTimeMillis());
		result.setApiBuildNumber(1);
		result.setApiVersionNumber("1.0.1") ;
		return result ;
	}	
}
