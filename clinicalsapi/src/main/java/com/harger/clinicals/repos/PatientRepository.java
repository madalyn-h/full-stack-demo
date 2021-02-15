package com.harger.clinicals.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harger.clinicals.model.Patient;

//find by name
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	List<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
