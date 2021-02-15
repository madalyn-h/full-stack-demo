package com.harger.flightcheckin.integration;

import com.harger.flightcheckin.integration.dto.Reservation;
import com.harger.flightcheckin.integration.dto.ReservationUpdateRequest;

//RESTful client layer that will invoke the web services exposed by flight reservation integration layer
public interface ReservationRestClient {

	public Reservation findReservation(Long id);
	
	public Reservation updateReservation(ReservationUpdateRequest request);
	
}
