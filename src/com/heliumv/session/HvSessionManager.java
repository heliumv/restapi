package com.heliumv.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heliumv.api.user.UserApi;

public class HvSessionManager {
	private static Logger log = LoggerFactory.getLogger(UserApi.class) ;

	@Context
	private HttpServletRequest request ;

	public HvSessionManager() {
	}

	public void setRequest(HttpServletRequest servletRequest) {
		request = servletRequest ;
	}
	
	private HvSessionData createImpl(String userId) {
		log.info("Creating new session info for '" + userId + "'.");
		HvSessionData sessionData = new HvSessionData() ;
		getMap().put(userId, sessionData);		
		return sessionData ;
	}

	public void create(String userId) {
		createImpl(userId) ;
	}
	
	public HvSessionData get(String userId) {
		HvSessionData sessionData = getMap().get(userId);
		if(sessionData == null) {
			sessionData = createImpl(userId) ;
		}
		return sessionData ;
	}

	private HvSessionMap getMap() {
		HvSessionMap m = (HvSessionMap) getContext().getAttribute("com.heliumv.sessionmap") ;
		if(m == null) {
			m = new HvSessionMap() ;
			getContext().setAttribute("com.heliumv.sessionmap", m);
		}
		return m ; 
	}
	
	public void expire(String userId) {
		getMap().expire(userId);
		log.info("Removed session info for '" + userId + "'.");
	}
	
	public void expire() {
		log.info("Removing expired sessions...");
		getMap().expire() ;
	}
	
	private ServletContext getContext() {
		return request.getSession().getServletContext() ;
	}

	
	class HvSessionMap {
		private Map<String, HvSessionData> map ;
		private long timeCreated ;
		
		public HvSessionMap() {
			map = new HashMap<String, HvSessionData>() ;
			timeCreated = System.currentTimeMillis() ;
		}
		
		public HvSessionData get(String userId) {
			return map.get(userId) ;
		}
		
		public void expire(String userId) {
			map.remove(userId) ;
		}
		
		public void expire() {
		}
		
		public void put(String userId, HvSessionData sessionData) {
			map.put(userId, sessionData) ;
			log.info("HvSessionManager has now '" + map.size() + "' entries.");
		}

		public long getTimeCreated() {
			return timeCreated;
		}
	}
}
