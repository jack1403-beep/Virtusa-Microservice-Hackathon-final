package com.hackathon.UserProfile.modeldto;


import com.hackathon.UserProfile.model.Patient;
import com.hackathon.UserProfile.model.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class UserModelDto {

	private Role userRole;

	private String email;

	private String userName;
	
	private String firstName;
	
	private String lastName;

	private String mobileNumber;

	private String password;

	private Patient patient;
}
