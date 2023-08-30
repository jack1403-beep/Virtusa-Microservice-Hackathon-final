package com.example.reminderservice.commonservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reminderservice.modeldto.Adherence;



@FeignClient(name = "adherenceservice")
public interface ViewAdherence {

	@GetMapping("/adherence/getMail")
	public List<Adherence> getMail();
	
	@GetMapping("/adherence/setMailToTrue")
	public void setMailToTrue(@RequestParam(value = "id") int adherenceId);
}
