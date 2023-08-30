package com.example.authservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authservice.model.RefreshToken;



@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long>{
	Optional<RefreshToken> findByToken(String token);
	void deleteByToken(String token);
	public RefreshToken findByUserEmail(String email);
	public RefreshToken findByUserUserId(int userId);

}
