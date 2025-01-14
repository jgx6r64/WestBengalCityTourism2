package com.wbct.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wbct.main.Entity.Traveller;
import com.wbct.main.Services.TravelServices;

//import ch.qos.logback.core.model.Model;

@Controller
public class WBCTController {
	@Autowired
	private TravelServices travelServices;
	private Traveller traveller;
	
	@GetMapping("/ApplicationPage")
	public String openPage(Model model) {
		model.addAttribute("traveller", new Traveller());
		return "Application";
	}
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
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
	
	
// <-------- For Google Sign in ------->
	
	//@GetMapping
	//public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
		//return oAuth2AuthenticationToken.getPrincipal().getAttributes();
	//}
}

