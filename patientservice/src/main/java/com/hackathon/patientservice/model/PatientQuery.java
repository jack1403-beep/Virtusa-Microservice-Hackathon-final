package com.hackathon.patientservice.model;

import java.time.LocalDateTime;


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
public class PatientQuery {

	private int queryId ;
	
	private String query;
	private LocalDateTime createdOn;
	private int patientId;
	
	
}