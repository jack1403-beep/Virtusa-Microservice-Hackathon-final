package com.example.medicationservice.commonservice;

import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;


import com.example.medicationservice.model.Adherence;


@FeignClient(name = "adherenceservice")
public interface ViewAdherence {

	@GetMapping("/adherence/getAdherenceByMedicationId")
	public  List<Adherence> getAdherenceByMedicationId(@RequestParam(value="id" , required = true) int medicationId);


	@DeleteMapping("/adherence/deleteAdherenceByMedicationId")
	public String deleteAdherenceByMedicationId(@RequestParam(value = "id" , required = true) int medicationId ) ;
}