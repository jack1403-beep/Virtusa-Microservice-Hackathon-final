package com.example.authservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authservice.model.PasswordOtp;




@Repository
public interface PasswordOtpRepository extends JpaRepository<PasswordOtp, Integer> {

	public PasswordOtp findByUserUserId(int userId);

	PasswordOtp findByOtp(final int otp);

	int removeByOtp(int otp);

}
