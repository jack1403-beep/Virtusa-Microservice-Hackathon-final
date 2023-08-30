package com.example.healthcareprovider.controller;

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

import com.example.healthcareprovider.model.HealthCareProvider;
import com.example.healthcareprovider.modeldto.HealthCareProviderDto;
import com.example.healthcareprovider.service.HealthCareService;


@RestController
@RequestMapping("/healthCareProvider")
public class HealthCareController {
	
	@Autowired
	HealthCareService careService;
	
	@PostMapping("/addHealthCareProvider")
	public String addHealthCareProvider(@RequestBody HealthCareProviderDto careProviderDto  , @RequestParam(value = "id") int healthCareProviderId) {
		return careService.addHealthCareProvider(careProviderDto,healthCareProviderId);
	}
	
	@GetMapping("/getHealthCareProvider")
	public Optional<HealthCareProvider> getHealthCareProvider(@RequestParam(value="id" , required = true) int healthCareProviderId){
		return careService.getHealthCareProvider(healthCareProviderId);
	}
	
	@DeleteMapping("/deleteHealthCareProvider")
	public String deleteHealthCareProvider(@RequestParam(value = "id" , required = true) int healthCareProviderId ) {
		return careService.deleteHealthCareProvider(healthCareProviderId);
	}
	
	@PutMapping("/editHealthCareProvider")
	public String editHealthCareProvider(@RequestBody HealthCareProviderDto careProviderDto, @RequestParam(value = "id") int healthCareProviderId) {
		return careService.editHealthCareProvider(careProviderDto,healthCareProviderId);
	}
	
	@GetMapping("/getHealthCareProviderByUserId")
	public  Optional<HealthCareProvider> getHealthCareProviderByUserId(@RequestParam(value="id" , required = true) int userId) {//use for foreign key
		return careService.getHealthCareProviderByUserId(userId);
	}

	@DeleteMapping("/deleteHealthCareProviderByUserId")
	public String deleteHealthCareProviderByUserId(@RequestParam(value = "id" , required = true) int userId ) {
		return careService.deleteHealthCareProviderByUserId(userId);
	}

}
