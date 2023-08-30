package com.example.medicationservice.model;


import java.time.LocalTime;

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
public class Adherence {
	
	private int adherenceId;
	
	private LocalTime adherenceTime;
	private boolean medicineTaken;
	private boolean mailSent;
	private int medicationId ;
	private int userId;
}
