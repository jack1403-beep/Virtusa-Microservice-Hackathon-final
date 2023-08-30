package com.example.adherenceservice.model;


import java.time.LocalTime;

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
@Table(name = "Adherence")
public class Adherence {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int adherenceId;
	
	private LocalTime adherenceTime;
	private boolean medicineTaken;
	private boolean mailSent;
	
	private int medicationId ;
	private int userId;

	public Adherence(LocalTime adherenceTime, int medicationId,int userId) {
		super();
		this.adherenceTime = adherenceTime;
		this.medicationId = medicationId;
		this.userId = userId;
	}
	
	
}
