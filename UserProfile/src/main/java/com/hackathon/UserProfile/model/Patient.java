package com.hackathon.UserProfile.model;

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
public class Patient {
	
	private int patientId;
	private int userId;
	private String medicalCondition;
	private List<Medication> medications;
	private List<Adherence> adherences;
}
