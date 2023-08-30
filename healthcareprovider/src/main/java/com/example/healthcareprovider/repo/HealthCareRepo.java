package com.example.healthcareprovider.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.healthcareprovider.model.HealthCareProvider;


@Repository
public interface HealthCareRepo extends JpaRepository<HealthCareProvider,Integer> {
	 Optional<HealthCareProvider> findByUserId(int userid);
	 void deleteByUserId(int userId);
} 
