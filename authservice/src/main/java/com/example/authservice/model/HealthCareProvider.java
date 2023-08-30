package com.example.authservice.model;

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
public class HealthCareProvider {

	private int providerId;
	private int userId;
	private String specialization;
	private int licenseNumber;
	private String hospitalName;
	
	
	
	
}
