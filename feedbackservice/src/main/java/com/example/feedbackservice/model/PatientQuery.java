package com.example.feedbackservice.model;

import java.time.LocalDateTime;


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
@Table(name = "PatientQuery")
public class PatientQuery {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int queryId ;
	
	private String query;
	private LocalDateTime createdOn;
	private int patientId;
	public PatientQuery(String query, int patientId) {
		super();
		this.query = query;
		this.patientId = patientId;
	}
	
	
}
