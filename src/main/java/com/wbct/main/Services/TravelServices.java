package com.wbct.main.Services;

import com.wbct.main.Entity.Traveller;

public interface TravelServices {
	public boolean RegisterUser(Traveller traveller);
	public Traveller LoginTraveller(String email, String city);
}
