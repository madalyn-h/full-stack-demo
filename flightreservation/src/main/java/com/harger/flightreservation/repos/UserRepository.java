package com.harger.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harger.flightreservation.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
//Spring will generate query for us
	User FindByEmail(String email);

}
