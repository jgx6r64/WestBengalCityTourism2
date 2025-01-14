package com.wbct.main.controller;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.wbct.main.Entity.Traveller;
import com.wbct.main.Services.SessionService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/html")
public class GoogleSignInController1 {

	@Autowired
	SessionService sessionService;
	
	@GetMapping("/profile")
	public String getHomePage(@CookieValue(value = "sessionId") String sessionId, Model model) {
		Traveller traveller = this.sessionService.getSessionDataBySessionId(sessionId);
		model.addAttribute("userId", traveller.getUserId());
		model.addAttribute("email", traveller.getEmail());
		model.addAttribute("name", traveller.getName());
		return "profile";
	}

	@PostMapping("/html-callback")
	public String htmlCallback(@RequestParam String credential, @RequestParam String g_csrf_token,
			@CookieValue(value = "g_csrf_token", defaultValue = "default") String csrfToken,
			HttpServletResponse httpServletResponse) throws IOException, GeneralSecurityException {

		this.validateCookie(g_csrf_token, csrfToken);
		Traveller traveller = this.sessionService.verifyIdTokenAndGetSessionData(credential);
		this.sessionService.addSessionToCookieAndCache(httpServletResponse, traveller);
		return "redirect:/html/profile";
	}

	private void validateCookie(String csrfTokenFromPayload, String csrfTokenFromCookie) throws AccessDeniedException {
		if (csrfTokenFromCookie == null || csrfTokenFromCookie.trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CSRF token in cookie is empty");
		}

		if (csrfTokenFromPayload == null || csrfTokenFromPayload.trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CSRF token in param is empty");
		}

		if (!csrfTokenFromPayload.equalsIgnoreCase(csrfTokenFromCookie)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"CSRF token in body is not equal to CSRF token in payload");
		}
	}

}
