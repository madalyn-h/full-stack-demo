package com.harger.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harger.flightreservation.dto.ReservationUpdateRequest;
import com.harger.flightreservation.entities.Reservation;
import com.harger.flightreservation.repos.ReservationRepository;

//Integration Layer, will communicate with CheckIn app or any other app that requires this data
@RestController
//connect angular app
@CrossOrigin
public class ReservationRestController {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);
	
	@RequestMapping("/reservations/{id}")
	public Reservation findReservation(@PathVariable("id") Long id) {
		
		LOGGER.info("inside findReservation() searching id " + id);
		
		return reservationRepository.findById(id).get();
	}
	
	@RequestMapping("/reservations")
	public Reservation updateReservation(@RequestBody ReservationUpdateRequest request) {
		
		LOGGER.info("inside updateReservation() for" + request);
		
		Reservation reservation = reservationRepository.findById(request.getId()).get();
		reservation.setNumberOfBags(request.getNumberOfBags());
		reservation.setCheckedIn(request.getCheckedIn());
		
		LOGGER.info("saving reservation");
		return reservationRepository.save(reservation);
		
		
	}
}
