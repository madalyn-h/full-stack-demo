package com.harger.clinicals.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.harger.clinicals.model.ClinicalData;
import com.harger.clinicals.model.Patient;
import com.harger.clinicals.repos.PatientRepository;
import com.harger.clinicals.util.BMICalculator;

//retrieve patient list, retrieve patient and patient clinical data by id
@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {

	private PatientRepository patientRepository;
	Map<String, String> filters = new HashMap<>();

	@Autowired
	PatientController(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	// add or edit patient
	@RequestMapping(value = "/patients", method = RequestMethod.POST)
	public Patient savePatient(@RequestBody Patient patient) {
		System.out.println(patient.getFirstName());
		return patientRepository.save(patient);
	}

	// retrieve specific patient data
	@RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
	public Patient getPatient(@PathVariable("id") int id) {
		return patientRepository.findById(id).get();
	}

	// retrieve patients
	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public List<Patient> getPatients() {
		return patientRepository.findAll();
	}

	// loop through entries, calculate BMI if necessary
	@RequestMapping(value = "/patients/analyze/{id}", method = RequestMethod.GET)
	public Patient analyze(@PathVariable("id") int id) {
		Patient patient = patientRepository.findById(id).get();
		List<ClinicalData> clinicalData = new ArrayList<>(patient.getClinicalData());
		for (ClinicalData entry : clinicalData) {
			// delete duplicate entry types
			if (filters.containsKey(entry.getComponentName())) {
				clinicalData.remove(entry);
				continue;
			} else {
				filters.put(entry.getComponentName(), null);
			}

			BMICalculator.calculateBMI(clinicalData, entry);
		}
		filters.clear();
		return patient;
	}

	

}
