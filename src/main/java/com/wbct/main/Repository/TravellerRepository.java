package com.wbct.main.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wbct.main.Entity.Traveller;


public interface TravellerRepository extends JpaRepository<Traveller, Long> {
	Traveller findByEmail(String email);
}
