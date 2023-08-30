package com.example.adherenceservice.controller;

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

import com.example.adherenceservice.model.Adherence;
import com.example.adherenceservice.modeldto.AdherenceDto;
import com.example.adherenceservice.service.AdherenceService;


@RestController
@RequestMapping("/adherence")
public class AdherenceController {
	
	@Autowired
	AdherenceService adherenceService;
	
	@PostMapping("/addadherence")
	public String addadherence(@RequestBody AdherenceDto adherenceDto  , @RequestParam(value = "id") int medicationId) {
		return adherenceService.addadherence(adherenceDto,medicationId);
	}
	
	@GetMapping("/getadherence")
	public Optional<Adherence> getadherence(@RequestParam(value="id" , required = true) int adherenceId){
		return adherenceService.getadherence(adherenceId);
	}
	
	@DeleteMapping("/deleteadherence")
	public String deleteadherence(@RequestParam(value = "id" , required = true) int adherenceId ) {
		return adherenceService.deleteadherence(adherenceId);
	}

	@DeleteMapping("/deleteAdherenceByUserId")
	public String deleteAdherenceByUserId(@RequestParam(value = "id" , required = true) int userId ) {
		return adherenceService.deleteAdherenceByUserId(userId);
	}
	
	@DeleteMapping("/deleteAdherenceByMedicationId")
	public String deleteAdherenceByMedicationId(@RequestParam(value = "id" , required = true) int medicationId ) {
		return adherenceService.deleteAdherenceByMedicationId(medicationId);
	}

	@PutMapping("/editadherence")
	public String editadherence(@RequestBody AdherenceDto adherenceDto, @RequestParam(value = "id") int adherenceId) {
		return adherenceService.editadherence(adherenceDto,adherenceId);
	}
	
	@GetMapping("/getAdherenceByMedicationId")
	public  List<Adherence> getAdherenceByMedicationId(@RequestParam(value="id" , required = true) int medicationId) {//use for foreign key
		return adherenceService.getAdherenceByMedicationId(medicationId);
	}
	
	@GetMapping("/getAdherenceBypatientId")
	public  List<Adherence> getAdherenceByUserId(@RequestParam(value="id" , required = true) int userId) {//use for foreign key
		return adherenceService.getAdherenceByUserId(userId);
	}
	
	@GetMapping("/getMail")
	public List<Adherence> getMail(){
		return adherenceService.getMail();
	}
	
	@GetMapping("/setMailToTrue")
	public void setMailToTrue(@RequestParam(value = "id") int adherenceId) {
		adherenceService.setMailToTrue(adherenceId);
	}

}
