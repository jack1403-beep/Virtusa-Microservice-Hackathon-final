package com.example.authservice.commonservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "adherenceservice")
public interface ViewAdherence {

	@DeleteMapping("/adherence/deleteAdherenceByUserId")
	public String deleteAdherenceByUserId(@RequestParam(value = "id" , required = true) int userId );
}
