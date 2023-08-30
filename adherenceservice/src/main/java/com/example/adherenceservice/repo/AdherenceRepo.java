package com.example.adherenceservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adherenceservice.model.Adherence;


@Repository
public interface AdherenceRepo extends JpaRepository<Adherence,Integer> {
	 public List<Adherence> findByMedicationId(int medicationId);
	 public List<Adherence> findAllByMedicineTaken(boolean flag);
	 public List<Adherence> findByUserId(int userId);
	 void deleteByUserId(int userId);
	 void deleteByMedicationId(int medicationId);
	
}
