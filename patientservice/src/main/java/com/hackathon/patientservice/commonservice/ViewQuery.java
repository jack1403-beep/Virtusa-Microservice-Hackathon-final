package com.hackathon.patientservice.commonservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestParam;


import com.hackathon.patientservice.model.PatientQuery;

@FeignClient(name = "feedbackservice")
public interface ViewQuery {

	@GetMapping("/feedback/getQueryByPatientId")
	public  List<PatientQuery> getQueryByPatientId(@RequestParam(value="id" , required = true) int patientId);

	@DeleteMapping("/feedback/deleteQueryByPatientId")
	public String deleteQueryByPatientId(@RequestParam(value = "id" , required = true) int  patientId) ;
}