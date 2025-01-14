package com.wbct.main.Services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.wbct.main.Entity.Traveller;

@Component
public class SessionCache {
	  private Map<String, Traveller> sessionDataMap;

	    public SessionCache() {
	        this.sessionDataMap= new HashMap<>();
	    }

	    public Map<String, Traveller> getSessionDataMap() {
	        return sessionDataMap;
	    }

	    public void setSessionDataMap(Map<String, Traveller> sessionDataMap) {
	        this.sessionDataMap = sessionDataMap;
	    }
}
