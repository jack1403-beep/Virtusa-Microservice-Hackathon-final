package com.hackathon.patientservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.patientservice.model.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer> {
	 Optional<Patient> findByUserId(int userid);
	 void deleteByUserId(int userId);
}
