package com.harger.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.harger.flightreservation.controllers.ReservationController;
import com.harger.flightreservation.dto.ReservationRequest;
import com.harger.flightreservation.entities.Flight;
import com.harger.flightreservation.entities.Passenger;
import com.harger.flightreservation.entities.Reservation;
import com.harger.flightreservation.repos.FlightRepository;
import com.harger.flightreservation.repos.PassengerRepository;
import com.harger.flightreservation.repos.ReservationRepository;
import com.harger.flightreservation.util.EmailUtil;
import com.harger.flightreservation.util.PDFGenerator;


//Service Layer class
public class ReservationServiceImpl implements ReservationService {
	
	@Value("${com.harger.flightreservation.itinerary.dirpath}")
	private String ITINERARY_DIR;

	@Autowired
	PDFGenerator pdfGenerator;

	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	EmailUtil emailUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	@Override
	@Transactional
	public Reservation bookFlight(ReservationRequest request) {
		
		//would make payment by invoking third party to process and request.getCardNumber()
		
		LOGGER.info("Inside bookFlight()");
		
		Long flightId = request.getFlightId();
		Flight flight = flightRepository.findById(flightId).get();
		
		LOGGER.info("fetching flight for id " + flightId);
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setPhone(request.getPassengerPhone());
		passenger.setEmail(request.getPassengerEmail());
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		LOGGER.info("Saving passenger " +passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		
		LOGGER.info("saving reservation: " + reservation);
		
		Reservation savedReservation = reservationRepository.save(reservation);
		
		//generate itinerary and send to passenger email
		String filePath = ITINERARY_DIR +savedReservation.getId()+".pdf";
		pdfGenerator.generateItinerary(savedReservation, filePath);
		
		LOGGER.info("generating itinerary");
		
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		
		LOGGER.info("emailing itinerary");
		return savedReservation;
	}

}
