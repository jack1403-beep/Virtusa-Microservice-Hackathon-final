package com.example.reminderservice.modeldto;

import java.time.LocalTime;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
	private int userId;

	
	private Role userRole;

	private String email;

	private String userName;
	
	private String firstName;
	
	private String lastName;

	private String mobileNumber;

	private String password;
}
