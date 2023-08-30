package com.example.healthgateway.commonservice;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.healthgateway.config.FeignConfiguration;
import com.example.healthgateway.modeldto.CheckValidTokenDto;
import com.example.healthgateway.modeldto.RefreshTokenRequest;







@FeignClient(name = "authservice", configuration = FeignConfiguration.class)
public interface ViewAuth {

	@PostMapping("/authToken/getUserName")
	public String getUserName(@RequestBody RefreshTokenRequest refreshTokenRequest);
	
	@PostMapping("/authToken/checkValidToken")
	public boolean checkValidToken(@RequestBody CheckValidTokenDto checkValidTokenDto);
}
