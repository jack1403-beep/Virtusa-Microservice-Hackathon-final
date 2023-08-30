package com.hackathon.UserProfile.controller;

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


import com.hackathon.UserProfile.model.User;
import com.hackathon.UserProfile.modeldto.AuthEmailDto;
import com.hackathon.UserProfile.modeldto.SignUpResponse;
import com.hackathon.UserProfile.modeldto.UserModelDto;
import com.hackathon.UserProfile.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public SignUpResponse saveUser(@RequestBody UserModelDto user) {
		return userService.saveUser(user);
	}
	
	@GetMapping("/getUser")
	public User getUser(@RequestParam(value="id" , required = true) int userId){
		return userService.getUser(userId);
	}

	@GetMapping("/getAllUser")
	public List<User> getAllUser(){
		return userService.getAllUser();
	}

	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestParam(value = "id" , required = true) int  userId) {
		return userService.deleteUser(userId);
	}

	@PutMapping("/editUser")
	public String editUser(@RequestBody UserModelDto userModel, @RequestParam(value = "id") int userId) {
		return userService.editUser(userModel,userId);
	}
	
	@GetMapping("/getMail")
	public User getMail(@RequestBody AuthEmailDto authEmailDto) {
		return userService.getMail(authEmailDto);
	}
}
