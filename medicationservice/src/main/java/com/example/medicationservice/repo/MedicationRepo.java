package com.example.medicationservice.repo;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.medicationservice.model.Medication;


@Repository
public interface MedicationRepo extends JpaRepository<Medication,Integer> {
	 public List<Medication> findByPatientId(int patientId);
	 public List<Medication> findAllByPatientIdAndCreatedOnBetween(int patientId,LocalDateTime startDate, 
			 LocalDateTime endDate);

	public void deleteByPatientId(int patientId);
}
