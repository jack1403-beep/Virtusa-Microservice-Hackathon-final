package com.example.feedbackservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.feedbackservice.model.PatientQuery;



@Repository
public interface FeedbackRepo extends JpaRepository<PatientQuery,Integer> {
	public List<PatientQuery> findByPatientId(int patientId);
	void deleteByPatientId(int patientId);
}
