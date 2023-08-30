package com.example.reminderservice.commonservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reminderservice.modeldto.Adherence;
import com.example.reminderservice.modeldto.Medication;

@FeignClient(name = "medicationservice")
public interface ViewMedication {

	@GetMapping("/medication/getmedication")
	public  Medication getmedication(@RequestParam(value="id" , required = true) int medicationId);
}
