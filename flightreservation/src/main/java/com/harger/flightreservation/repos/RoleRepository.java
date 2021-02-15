package com.harger.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harger.flightreservation.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
