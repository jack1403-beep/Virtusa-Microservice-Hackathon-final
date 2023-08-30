package com.example.feedbackservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.feedbackservice.model.PatientQuery;
import com.example.feedbackservice.modeldto.PatientQueryDto;
import com.example.feedbackservice.service.FeedbackService;



@RestController
@RequestMapping("/feedback")
public class FeedbackController {
	
	@Autowired
	FeedbackService feedbackService;
	

	@PostMapping("/addQuery")
	public String addQuery(@RequestBody PatientQueryDto patientQueryDto, @RequestParam(value = "id") int patientId) {
		return feedbackService.addQuery(patientQueryDto, patientId);
	}
	
	@GetMapping("/getQuery")
	public PatientQuery getQuery(@RequestParam(value="id" , required = true) int queryId){
		return feedbackService.getQuery(queryId);
	}

	@GetMapping("/getAllQuery")
	public List<PatientQuery> getAllQuery(){
		return feedbackService.getAllQuery();
	}

	@DeleteMapping("/deleteQuery")
	public String deleteQuery(@RequestParam(value = "id" , required = true) int  queryId) {
		return feedbackService.deleteQuery(queryId);
	}

	@DeleteMapping("/deleteQueryByPatientId")
	public String deleteQueryByPatientId(@RequestParam(value = "id" , required = true) int  patientId) {
		return feedbackService.deleteQueryByPatientId(patientId);
	}

	@PutMapping("/editQuery")
	public String editUser(@RequestBody PatientQueryDto patientQueryDto, @RequestParam(value = "id") int queryId) {
		return feedbackService.editUser(patientQueryDto,queryId);
	}
	
	@GetMapping("/getQueryByPatientId")
	public  List<PatientQuery> getQueryByPatientId(@RequestParam(value="id" , required = true) int patientId) {//use for foreign key
		return feedbackService.getQueryByPatientId(patientId);
	}

}
