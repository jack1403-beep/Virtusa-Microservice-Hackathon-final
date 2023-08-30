package com.example.healthcareprovider.modeldto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class HealthCareProviderDto {

	private String specialization;
	private int licenseNumber;
	private String hospitalName;
}
