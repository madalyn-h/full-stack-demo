package com.harger.clinicals.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harger.clinicals.model.ClinicalData;

public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Integer> {
	List<ClinicalData> findByPatientIdAndComponentNameOrderByMeasuredDateTime(int patientId, String componentName);
}
