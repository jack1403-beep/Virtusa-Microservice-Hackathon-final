package com.hackathon.patientservice.commonservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.hackathon.patientservice.model.Medication;



@FeignClient(name = "medicationservice")
public interface ViewMedication {

	@GetMapping("/medication/getMedicationByPatientId")
	public  List<Medication> getMedicationByPatientId(@RequestParam(value="id" , required = true) int patientId);

	@DeleteMapping("/medication/deleteMedicationByPatientId")
	public String deleteMedicationByPatientId(@RequestParam(value = "id" , required = true) int patientId );
}