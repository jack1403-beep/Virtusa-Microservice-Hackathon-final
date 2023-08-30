package com.example.medicationservice.model;

import java.time.LocalDateTime;
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
@Table(name = "Medication")
public class Medication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int medicationId ;
	
	private String medicationName;
	private String dosage;
	private String frequency;
	private LocalDateTime createdOn;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int patientId;
	
	@Transient
	private List<Adherence> adherence; 
	
	public Medication(String medicationName, String dosage, String frequency, LocalDateTime startDate,
			LocalDateTime endDate,int patientId) {
		super();
		this.medicationName = medicationName;
		this.dosage = dosage;
		this.frequency = frequency;
		this.startDate = startDate;
		this.endDate = endDate;
		this.patientId = patientId;
	}
	
	

}
