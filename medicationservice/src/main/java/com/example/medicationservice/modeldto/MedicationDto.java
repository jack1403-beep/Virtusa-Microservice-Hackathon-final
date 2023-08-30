package com.example.medicationservice.modeldto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class MedicationDto {
	private String medicationName;
	private String dosage;
	private String frequency;
	private LocalDateTime startDate;
	private LocalDateTime endDate; 
	private boolean medicineTaken;

}
