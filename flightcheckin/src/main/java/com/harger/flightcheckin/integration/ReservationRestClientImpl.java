package com.harger.flightcheckin.integration;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.harger.flightcheckin.integration.dto.Reservation;
import com.harger.flightcheckin.integration.dto.ReservationUpdateRequest;

@Component
public class ReservationRestClientImpl implements ReservationRestClient {

	private static final String RESERVATION_REST_URL = "http://localhost:8080/flightreservation/reservations/";

	@Override
	public Reservation findReservation(Long id) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(RESERVATION_REST_URL + id, Reservation.class);
	}

	@Transactional
	@Override
	public Reservation updateReservation(ReservationUpdateRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		Reservation reservation = restTemplate.postForObject(RESERVATION_REST_URL, request, Reservation.class);
		return reservation;
	}

}
