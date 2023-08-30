package com.example.authservice.modeldto;




import com.example.authservice.model.Patient;
import com.example.authservice.model.Role;

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
