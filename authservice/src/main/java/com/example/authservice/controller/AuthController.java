package com.example.authservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.authservice.model.User;
import com.example.authservice.modeldto.ChangePasswordDto;
import com.example.authservice.modeldto.CheckValidTokenDto;
import com.example.authservice.modeldto.ForgotPasswordDto;
import com.example.authservice.modeldto.JwtRequest;
import com.example.authservice.modeldto.JwtResponse;
import com.example.authservice.modeldto.RefreshTokenRequest;
import com.example.authservice.modeldto.SignUpResponse;
import com.example.authservice.modeldto.UserModelDto;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.PasswordService;
import com.example.authservice.service.RefreshTokenService;
import com.example.authservice.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;




@RestController
@RequestMapping("/authToken")
public class AuthController {

	@Autowired
	AuthService authService;
	
	@Autowired
	RefreshTokenService refreshTokenService;
	
	@Autowired
	PasswordService passwordService;
	
	@Autowired
	UserService userService;
	
	@PostMapping({"/authenticate"})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		System.out.println("Enytered");
		
		return authService.createJwtToken(jwtRequest);
	}

	@PostMapping("/refresh/token")
	public JwtResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		System.out.println("refresh token worked");
		return authService.refreshToken(refreshTokenRequest);
	}

	@PostMapping("/getUserName")
	public String getUserName(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		System.out.println("a");
		return authService.getUserName(refreshTokenRequest);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
		refreshTokenService.deleteToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully");
	}
	
	@PostMapping("/checkValidToken")
	public boolean checkValidToken(@RequestBody CheckValidTokenDto checkValidTokenDto) {
		return authService.checkValidToken(checkValidTokenDto);
	}
	
//	user CRUD methods
	@PostMapping("/signup")
	public SignUpResponse saveUser(@RequestBody UserModelDto user, HttpServletRequest request) {
		return userService.saveUser(user,request);
	}
	
	@CircuitBreaker(name="${spring.application.name}" , fallbackMethod= "getUserInfoByFallBackMethod")
	@GetMapping("/getUser")
	public User getUser(@RequestParam(value="id" , required = true) int userId){
		return userService.getUser(userId);
	}
	
	//creating fallback method  for circuit Breker
	public User getUserInfoByFallBackMethod(@RequestParam(value="id" , required = true) int userId, Exception ex){
		User user=new User("12345", "Dummy Role", "Dummy First Name", "Dummy Last Name", "9876543210", "*******");
		return user;
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
	
//	forgot password API
	@PostMapping("/sendOTP")
	public ForgotPasswordDto sendOTP(@RequestBody ForgotPasswordDto forgotPasswordDto)  {
		return passwordService.sendOTP(forgotPasswordDto);
	}

	@PostMapping("/verifyOTP")
	public boolean verifyOTP(@RequestBody ForgotPasswordDto forgotPasswordDto) {
		return passwordService.verifyOTP(forgotPasswordDto);
	}

	@PutMapping("/resetPassword")
	public boolean resetPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
		return passwordService.resetPassword(forgotPasswordDto);
	}

	@PutMapping("/changePassword")
	public boolean changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
		return passwordService.changePassword(changePasswordDto);
	}
	
}

