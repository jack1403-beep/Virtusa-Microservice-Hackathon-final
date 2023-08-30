package com.example.healthcareprovider.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "HealthCareProviders")
public class HealthCareProvider {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int providerId;
	private String specialization;
	private int licenseNumber;
	private String hospitalName;
	private int userId;
	
	public HealthCareProvider(String specialization, int licenseNumber, String hospitalName, int userId) {
		super();
		this.specialization = specialization;
		this.licenseNumber = licenseNumber;
		this.hospitalName = hospitalName;
		this.userId = userId;
	}
	
	
}
