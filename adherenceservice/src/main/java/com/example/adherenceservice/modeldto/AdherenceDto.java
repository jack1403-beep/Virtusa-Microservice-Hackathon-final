package com.example.adherenceservice.modeldto;


import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class AdherenceDto {

	private LocalTime adherenceTime;
	private boolean medicineTaken;
	private int userId;
	private boolean mailSent;
}
