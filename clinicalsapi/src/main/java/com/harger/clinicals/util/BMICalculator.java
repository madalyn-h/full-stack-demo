package com.harger.clinicals.util;

import java.util.List;

import com.harger.clinicals.model.ClinicalData;
import com.harger.clinicals.model.Patient;

public class BMICalculator {

	public static void calculateBMI(List<ClinicalData> clinicalData, ClinicalData entry) {
		if (entry.getComponentName().equals("hw")) {
			String[] heightAndWeight = entry.getComponentValue().split("/");
			if (heightAndWeight != null && heightAndWeight.length > 1) {
				float feetToMeters = Float.parseFloat(heightAndWeight[0]) * 0.4536F;
				float bmi = Float.parseFloat(heightAndWeight[1]) / (feetToMeters * feetToMeters);
				ClinicalData bmiEntry = new ClinicalData();
				bmiEntry.setComponentName("BMI");
				bmiEntry.setComponentValue(Float.toString(bmi));
				clinicalData.add(bmiEntry);
			}
		}
	}
}
