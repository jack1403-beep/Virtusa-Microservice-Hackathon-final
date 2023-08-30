package com.hackathon.UserProfile.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "UserProfile")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int userId;

	@Enumerated(EnumType.STRING)
	private Role userRole;

	@Column(unique = true)
	private String email;

	private String userName;
	
	private String firstName;
	
	private String lastName;

	private String mobileNumber;

	private String password;
	
	@Transient
	private Patient patient;
	
	private boolean accountVerified;
	
	@Transient
	private HealthCareProvider healthCareProvider; 
	
	@Transient
	private List<Adherence> adherences = new ArrayList<>();
	

	public User(String email, String userName, String firstName, String lastName, String mobileNumber,
			String password) {
		super();
		this.email = email;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.password = password;
	}
	
	
	
	
	

}
