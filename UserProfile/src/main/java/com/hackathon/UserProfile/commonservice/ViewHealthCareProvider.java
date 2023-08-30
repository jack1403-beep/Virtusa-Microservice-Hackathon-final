package com.hackathon.UserProfile.commonservice;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hackathon.UserProfile.model.HealthCareProvider;


@FeignClient(name = "healthCareProviderUser")
public interface ViewHealthCareProvider {
	
	@GetMapping("/healthCareProvider/getHealthCareProviderByUserId")
	public  Optional<HealthCareProvider> getHealthCareProviderByUserId(@RequestParam(value="id" , required = true) int userId);
}
