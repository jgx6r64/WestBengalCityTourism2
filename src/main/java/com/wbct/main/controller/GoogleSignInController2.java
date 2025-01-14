package com.wbct.main.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbct.main.Entity.CredVerification;
import com.wbct.main.Entity.Traveller;
import com.wbct.main.Services.SessionService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/js")
public class GoogleSignInController2 {
	
	@Autowired
	SessionService sessionService;
	
	public Traveller jsCallback(@RequestBody CredVerification credential,
            HttpServletResponse httpServletResponse) throws GeneralSecurityException, IOException {
		
		Traveller traveller = this.sessionService.verifyIdTokenAndGetSessionData(credential.getCredential());
		this.sessionService.addSessionToCookieAndCache(httpServletResponse, traveller);
		return traveller;
	}
}
