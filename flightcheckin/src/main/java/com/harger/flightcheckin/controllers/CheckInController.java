package com.harger.flightcheckin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import com.harger.flightcheckin.integration.ReservationRestClient;
import com.harger.flightcheckin.integration.dto.Reservation;
import com.harger.flightcheckin.integration.dto.ReservationUpdateRequest;

@Controller
public class CheckInController {
	
	@Autowired
	ReservationRestClient restClient;
	
	@RequestMapping("/showStartCheckin")
	public String showStartCheckIn() {
		return "startCheckIn";
	}
	
	//invoke reservationrestclient
	@RequestMapping("/startCheckIn")
	public String startCheckIn(@RequestParam("reservationId") Long reservationId, ModelMap modelMap) {
		Reservation reservation = restClient.findReservation(reservationId);
		modelMap.addAttribute("reservation", reservation);
		return "displayReservationDetails";
	}
	
	@Transactional
	@RequestMapping("/completeCheckIn")
	public String completeCheckIn(@RequestParam("reservationId") Long reservationId, @RequestParam("numberOfBags") int numberOfBags) {
		ReservationUpdateRequest reservationUpdateRequest = new ReservationUpdateRequest();
		reservationUpdateRequest.setId(reservationId);
		reservationUpdateRequest.setCheckedIn(true);
		reservationUpdateRequest.setNumberOfBags(numberOfBags);
		return "checkInConfirmation";
	}
}
