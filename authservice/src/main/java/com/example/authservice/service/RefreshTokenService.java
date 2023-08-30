package com.example.authservice.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authservice.model.RefreshToken;
import com.example.authservice.model.User;
import com.example.authservice.modeldto.AuthEmailDto;
import com.example.authservice.modeldto.RefreshTokenRequest;
//import com.example.authservice.repo.TokenRepo;
import com.example.authservice.repo.RefreshTokenRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
	
	@Autowired
	RefreshTokenRepo refreshTokenRepo;
	
	@Autowired
	UserService userService;
	

	
	//1 hr = 1 * 60 * 60 = 3600 in seconds
	private static final int REFRESH_TOKEN_VALIDITY = 3600;

	public RefreshToken gerenrateRefreshToken(String email) {

		User user = userService.getMail(email);
		
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedDate(Instant.now());
		refreshToken.setExpireAt(LocalDateTime.now().plusSeconds(REFRESH_TOKEN_VALIDITY));
		refreshToken.setUser((new User(user.getUserId())));	

		return refreshTokenRepo.save(refreshToken);
	}
	
	public boolean validateRefreshToken(RefreshTokenRequest refreshTokenRequest) {
		String email = refreshTokenRequest.getEmail();
		String token = refreshTokenRequest.getRefreshToken();

		User user = userService.getMail(email);
		
		RefreshToken tokenFromDB = refreshTokenRepo.findByUserUserId(user.getUserId());
		//		System.out.println(refreshToken.getExpireAt());
		if(Objects.isNull(tokenFromDB)  || !(tokenFromDB.getToken().equals(token))  || tokenFromDB.isExpired()) {
			//			System.out.println("o"+ " " + Objects.isNull(refreshToken));
			//			System.out.println("e" + " "+ refreshToken.getToken() != token);
			//			System.out.println(refreshToken.isExpired());
			if(!Objects.isNull(tokenFromDB) && tokenFromDB.isExpired()) {
				refreshTokenRepo.deleteByToken(token);
				return false;
			}	
			return false;
		}else {
			return true;
		}
	}
	
	public void deleteToken(String token) {
		refreshTokenRepo.deleteByToken(token);
	}
}
