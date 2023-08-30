package com.example.healthgateway.model;

import java.util.ArrayList;
import java.util.List;



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

	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public boolean isAccountVerified() {
		return accountVerified;
	}

	public void setAccountVerified(boolean accountVerified) {
		this.accountVerified = accountVerified;
	}

	public HealthCareProvider getHealthCareProvider() {
		return healthCareProvider;
	}

	public void setHealthCareProvider(HealthCareProvider healthCareProvider) {
		this.healthCareProvider = healthCareProvider;
	}

	public List<Adherence> getAdherences() {
		return adherences;
	}

	public void setAdherences(List<Adherence> adherences) {
		this.adherences = adherences;
	}

	private int userId;
	private Role userRole;

	private String email;

	private String userName;
	
	private String firstName;
	
	private String lastName;

	private String mobileNumber;

	private String password;
	
	private Patient patient;
	
	private boolean accountVerified;
	
	private HealthCareProvider healthCareProvider; 
	
	private List<Adherence> adherences = new ArrayList<>();
}
