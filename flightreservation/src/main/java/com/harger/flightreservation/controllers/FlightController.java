package com.harger.flightreservation.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harger.flightreservation.entities.Flight;
import com.harger.flightreservation.repos.FlightRepository;

@Controller
public class FlightController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);
	
	@Autowired
	FlightRepository flightRepository;
	
	//search flights, run query to return flights, setting ModelMap and sending with view
	@RequestMapping("findFlights")
	public String findFlights(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("departureDate") @DateTimeFormat(pattern="MM-DD-YYYY") Date departureDate, ModelMap modelMap) {
		LOGGER.info("Inside findFlights()" + from + "to: " + to + "departure date: " +departureDate);
		List<Flight> flights = flightRepository.findFlights(from, to, departureDate);
		modelMap.addAttribute("flights", flights);
		LOGGER.info("flights found: " + flights);
		return "displayFlights.jsp";
	}
	
	//only for Users with Admin role
	@RequestMapping("admin/showAddFlight")
	public String showAddFlight() {
		return "addFlight.jsp";
	}
}
