package com.harger.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harger.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
