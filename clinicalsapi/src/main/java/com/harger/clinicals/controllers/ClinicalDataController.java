package com.harger.clinicals.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.harger.clinicals.model.ClinicalData;
import com.harger.clinicals.model.Patient;
import com.harger.clinicals.repos.ClinicalDataRepository;
import com.harger.clinicals.repos.PatientRepository;
import com.harger.clinicals.util.BMICalculator;
import com.harger.clinicals.controllers.dto.ClinicalDataRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {

	private ClinicalDataRepository repository;
	private PatientRepository patientRepository;

	// dependency injection
	@Autowired
	ClinicalDataController(ClinicalDataRepository repository, PatientRepository patientRepository) {
		this.repository = repository;
		this.patientRepository = patientRepository;
	}

	// add selected data to user based on user id
	@RequestMapping(value = "/clinicals", method = RequestMethod.POST)
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest clinicalDataRequest) {
		Patient patient = patientRepository.findById(clinicalDataRequest.getPatientId()).get();
		ClinicalData data = new ClinicalData();
		data.setComponentName(clinicalDataRequest.getComponentName());
		data.setComponentValue(clinicalDataRequest.getComponentValue());
		data.setPatient(patient);
		return repository.save(data);
	}

	@RequestMapping(value = "/clinicals/{patientId}/{componentName}", method = RequestMethod.GET)
	public List<ClinicalData> getClinicalData(@PathVariable("patientId") int patientId,
			@PathVariable("componentName") String componentName) {
		if(componentName.equals("bmi")) {
			componentName = "hw";
		}
		
		// spring data syntax, will generate query
		List<ClinicalData> clinicalData = repository.findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId,
				componentName);
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for (ClinicalData entry : duplicateClinicalData) {
			BMICalculator.calculateBMI(clinicalData, entry);
		}
		return clinicalData;
	}
}
