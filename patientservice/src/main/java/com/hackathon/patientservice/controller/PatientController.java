package com.hackathon.patientservice.controller;

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

import com.hackathon.patientservice.model.Patient;
import com.hackathon.patientservice.modeldto.PatientDto;
import com.hackathon.patientservice.service.PatientService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	PatientService patientService;
	
	
	@PostMapping("/addPatient")
	public String addPatient(@RequestBody PatientDto patientDto  , @RequestParam(value = "id") int userId) {
		return patientService.addPatient(patientDto,userId);
	}
	
	
	@CircuitBreaker(name="${spring.application.name}" , fallbackMethod= "getPatientInfoByFallBackMethod")
	@GetMapping("/getPatient")
	public Patient getPatient(@RequestParam(value="id" , required = true) int patientId){
		return patientService.getPatient(patientId);
	}
	
	//creating fallback method  for circuit Breker
	public Patient getPatientInfoByFallBackMethod(@RequestParam(value="id" , required = true) int patientId,Exception ex){
		return new Patient("Dummy Medical Condition", 1234);
	}
	
	@DeleteMapping("/deletePatientByUserId")
	public String deletePatientByUserId(@RequestParam(value = "id" , required = true) int userId ) {
		return patientService.deletePatientByUserId(userId);
	}
	
	
	@DeleteMapping("/deletePatient")
	public String deletePatient(@RequestParam(value = "id" , required = true) int patientId ) {
		return patientService.deletePatient(patientId);
	}
	
	@PutMapping("/editPatient")
	public String editPatient(@RequestBody PatientDto patientDto, @RequestParam(value = "id") int patientId) {
		return patientService.editUser(patientDto,patientId);
	}
	
	@GetMapping("/getPatientByUserId")
	public  Optional<Patient> getPatientByUserId(@RequestParam(value="id" , required = true) int userId) {//use for foreign key
		return patientService.getPatientByUserId(userId);
	}
	
}
