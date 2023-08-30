package com.example.medicationservice.modeldto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class ResponseDto {

	private LocalDateTime startDate;
	private LocalDateTime endDate; 
}
