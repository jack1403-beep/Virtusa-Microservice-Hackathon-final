package com.example.reminderservice.modeldto;

import java.time.LocalDateTime;
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
public class Medication {

	private int medicationId ;
	private String medicationName;
	private String dosage;
	private String frequency;
	private LocalDateTime startDate;
	private LocalDateTime endDate; 
	private LocalDateTime reminderTiming;
	private int patientId;
	private List<Adherence> adherence;
}
