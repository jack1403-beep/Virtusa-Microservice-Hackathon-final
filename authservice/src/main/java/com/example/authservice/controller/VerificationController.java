package com.example.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.authservice.service.UserVerifyService;




@Controller//@RestController is String Specific thats why we used Controller
public class VerificationController {
	
	@Autowired
	UserVerifyService userVerifyService;

//	when user click on verification link
	@RequestMapping(value = "/authToken/verify",method = RequestMethod.GET)
	public String verify(@Param("code") String code)
	{	
		System.out.print(code);
		if (userVerifyService.confirmEmail(code)) {
			return "verify_success";
		} else {
			return "verify_fail";
		}
	}
}
