package com.wbct.main.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbct.main.Entity.Traveller;
import com.wbct.main.Repository.TravellerRepository;

@Service
public class Implementation implements TravelServices {

	@Autowired
	private TravellerRepository travellerRepository;
	@Override
	public boolean RegisterUser(Traveller traveller) {
		boolean status = false;
		try {
			travellerRepository.save(traveller);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	@Override
	public Traveller LoginTraveller(String email, String city) {
		Traveller validTraveller = travellerRepository.findByEmail(email);
		if(validTraveller != null && validTraveller.getCity().equals(city)) {
			return validTraveller;
		}
		return null;
	}
	

}
