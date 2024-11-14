package com.wbct.main.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbct.main.Entity.Traveller;
import com.wbct.main.Services.TravelServices;

//import ch.qos.logback.core.model.Model;
@Controller
@RestController
@RequestMapping("/")
public class WBCTController 
{
	@Autowired
	private TravelServices travelServices;
	
//<-------- To Open The Registration Page -------->	
	
	@GetMapping("/ApplicationPage")
	public String openPage(Model model) {
		model.addAttribute("traveller", new Traveller());
		return "Application";
	}
//<-------- To Open The Sign In Page -------->
	
	@GetMapping("/SigninPage")
	public String openSigninPage(Model model) {
		model.addAttribute("traveller", new Traveller());
		return "signin";
		}
	
	@PostMapping("/RegForm")
	public String SubmitForm(@ModelAttribute("traveller") Traveller traveller, Model model) {
		boolean msg = travelServices.RegisterUser(traveller);
		if(msg) {
			model.addAttribute("successMSG", "User Registered Successfully");
		}
		else {
			model.addAttribute("errorMSG", "Something Went Wrong, Please Try Again Later");
		}
		return "Application";
	}
	
	@PostMapping("/SigninForm")
	public String LoginTraveller(@ModelAttribute("traveller") Traveller traveller, Model model) {
		Traveller validTraveller = travelServices.LoginTraveller( traveller.getEmail(), traveller.getCity());
		
		if(validTraveller != null) {
			model.addAttribute("successMSG", "Successfully Logged In");
			return "profile";
			}
		else {
			model.addAttribute("errorMSG", "Invalid Credentials, Please Try Again Later");
			return "signin";
		}
	}
	
// <-------- For Google Sign in ------->
	
	@GetMapping
	public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
		return oAuth2AuthenticationToken.getPrincipal().getAttributes();
	}
}

