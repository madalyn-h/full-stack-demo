package com.harger.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harger.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
