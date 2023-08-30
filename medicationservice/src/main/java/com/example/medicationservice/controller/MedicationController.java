package com.example.medicationservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.medicationservice.model.Medication;
import com.example.medicationservice.modeldto.MedicationDto;
import com.example.medicationservice.modeldto.ResponseDto;
import com.example.medicationservice.service.MedicationService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/medication")
public class MedicationController {

	@Autowired
	MedicationService medicationService;
	
	@PostMapping("/addmedication")
	public String addmedication(@RequestBody MedicationDto medicationDto  , @RequestParam(value = "id") int patientId) {
		return medicationService.addmedication(medicationDto,patientId);
	}
	
	@CircuitBreaker(name="${spring.application.name}" , fallbackMethod= "getMedicationInfoByFallBackMethod")
	@GetMapping("/getmedication")
	public Medication getmedication(@RequestParam(value="id" , required = true) int medicationId){
		return medicationService.getmedication(medicationId);
	}
	
	//fallback Method
	public Medication getMedicationInfoByFallBackMethod(@RequestParam(value="id" , required = true) int medicationId,Exception ex){
		System.out.println("I am in fallback");
		return new Medication("Dummy Medicine", null, null, null, null, 12345);
	}
	
	
	@DeleteMapping("/deletemedication")
	public String deletemedication(@RequestParam(value = "id" , required = true) int medicationId ) {
		return medicationService.deletemedication(medicationId);
	}

	@DeleteMapping("/deleteMedicationByPatientId")
	public String deleteMedicationByPatientId(@RequestParam(value = "id" , required = true) int patientId ) {
		return medicationService.deleteMedicationByPatientId(patientId);
	}
	
	@PutMapping("/editmedication")
	public String editmedication(@RequestBody MedicationDto medicationDto, @RequestParam(value = "id") int medicationId) {
		return medicationService.editmedication(medicationDto,medicationId);
	}
	
	@GetMapping("/getMedicationByPatientId")
	public  List<Medication> getMedicationByPatientId(@RequestParam(value="id" , required = true) int patientId) {//use for foreign key
		return medicationService.getMedicationByPatientId(patientId);
	}

	@GetMapping("/getReport")
	public  List<Medication> getReport(@RequestBody ResponseDto responseDto,@RequestParam(value="id" , required = true) int patientId){
		return medicationService.getReport(responseDto,patientId);
	}
}
