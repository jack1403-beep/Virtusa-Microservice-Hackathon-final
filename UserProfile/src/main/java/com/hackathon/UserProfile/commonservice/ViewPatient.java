package com.hackathon.UserProfile.commonservice;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hackathon.UserProfile.model.Patient;

@FeignClient(name = "patientuser")
public interface ViewPatient {

	@GetMapping("/patient/getPatientByUserId")
	public  Optional<Patient> getPatientByUserId(@RequestParam(value="id" , required = true) int userId);
}
