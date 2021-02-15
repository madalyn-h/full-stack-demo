package com.harger.flightreservation.services;

import com.harger.flightreservation.dto.ReservationRequest;
import com.harger.flightreservation.entities.Reservation;

public interface ReservationService {
	
	public Reservation bookFlight(ReservationRequest request); 
}
