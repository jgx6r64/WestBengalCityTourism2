package com.wbct.main.Services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.wbct.main.Entity.Traveller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionService {

	NetHttpTransport transport = new NetHttpTransport();

	com.google.api.client.json.JsonFactory jsonFactory = new GsonFactory();

	@Autowired
	SessionCache sessionCache;

	public void addSessionToCookieAndCache(HttpServletResponse httpServletResponse, Traveller traveller) {
		String sessionId = UUID.randomUUID().toString();
		this.sessionCache.getSessionDataMap().put(sessionId, traveller);
		Cookie cookie = new Cookie("sessionId", sessionId);
		httpServletResponse.addCookie(cookie);
	}
	
	public Traveller verifyIdTokenAndGetSessionData(String credential) throws GeneralSecurityException, IOException {
		 GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
	                // Specify the CLIENT_ID of the app that accesses the backend:
	                .setAudience(
	                        Collections.singletonList("395791288206-mbr43mbnui8k7vp2o9aeuc13ibgp061n.apps.googleusercontent.com"))
	                // Or, if multiple clients access the backend:
	                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
	                .build();

	        GoogleIdToken idToken = verifier.verify(credential);
	        if(idToken == null){
	            throw new ResponseStatusException( HttpStatus.FORBIDDEN, "invalid id token found");
	        }
	        GoogleIdToken.Payload payload = idToken.getPayload();
	        
	        String userId = payload.getSubject();
	        String email = payload.getEmail();
	        String name = (String)payload.get("name");
	        
	        Traveller traveller = new Traveller();
	        traveller.setUserId(userId);
	        traveller.setEmail(email);
	        traveller.setName(name);
	        
	        return traveller;
	}
	
	public Traveller getSessionDataBySessionId(String sessionId) {
		Traveller traveller = this.sessionCache.getSessionDataMap().get(sessionId);
		return traveller;
	}
}
