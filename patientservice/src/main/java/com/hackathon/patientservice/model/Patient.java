package com.hackathon.patientservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
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
@Table(name = "Patients")
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int patientId;
	
	private String medicalCondition;
	private int userId;
	
	@Transient
	private List<Medication> medications = new ArrayList<>();
	
	@Transient
	private List<PatientQuery> patientQueries  = new ArrayList<>();
	
	public Patient(String medicalCondition, int userId) {
		super();
		this.medicalCondition = medicalCondition;
		this.userId = userId;
	}
	
	
	
	
}