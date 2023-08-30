package com.example.healthgateway.model;



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
	public int getAdherenceId() {
		return adherenceId;
	}
	public void setAdherenceId(int adherenceId) {
		this.adherenceId = adherenceId;
	}
	public LocalTime getAdherenceTime() {
		return adherenceTime;
	}
	public void setAdherenceTime(LocalTime adherenceTime) {
		this.adherenceTime = adherenceTime;
	}
	public boolean isMedicineTaken() {
		return medicineTaken;
	}
	public void setMedicineTaken(boolean medicineTaken) {
		this.medicineTaken = medicineTaken;
	}
	public boolean isMailSent() {
		return mailSent;
	}
	public void setMailSent(boolean mailSent) {
		this.mailSent = mailSent;
	}
	public int getMedicationId() {
		return medicationId;
	}
	public void setMedicationId(int medicationId) {
		this.medicationId = medicationId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
