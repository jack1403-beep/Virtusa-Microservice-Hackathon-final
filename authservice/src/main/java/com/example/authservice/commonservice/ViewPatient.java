package com.example.authservice.commonservice;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.authservice.model.Patient;




@FeignClient(name = "patientuser")
public interface ViewPatient {

	@GetMapping("/patient/getPatientByUserId")
	public  Optional<Patient> getPatientByUserId(@RequestParam(value="id" , required = true) int userId);
	
	@DeleteMapping("/patient/deletePatientByUserId")
	public String deletePatientByUserId(@RequestParam(value = "id" , required = true) int userId );
}
