package com.example.authservice.service;

import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authservice.model.SecureToken;
import com.example.authservice.model.User;
import com.example.authservice.repo.SecureTokenRepository;
import com.example.authservice.repo.UserRepo;

@Service
@Transactional
public class UserVerifyService {
	
	@Autowired
	SecureTokenRepository secureTokenRepository;
	
	@Autowired
	UserRepo userRepo;

//	enabling the account
	@Transactional
	public boolean confirmEmail(String code) {
		SecureToken secureToken = secureTokenRepository.findByToken(code);
		System.out.print(code);

		//		checking if token is null
		if(Objects.isNull(secureToken) || !StringUtils.equals(code, secureToken.getToken()) || secureToken.isExpired()) {
			if(!Objects.isNull(secureToken) && secureToken.isExpired()) {
				secureTokenRepository.removeByToken(code);
				userRepo.deleteById(secureToken.getUser().getUserId());//removing the user so that he can register again if token expired
				return false;
			}
			return false;
		}

		@SuppressWarnings("deprecation")
		User user = userRepo.getOne(secureToken.getUser().getUserId());
		System.out.println(secureToken.getUser().getUserId());

		if(Objects.isNull(user) || user.isAccountVerified()) {
			System.out.println(user.isAccountVerified());
			return false;
		}else {  
			//verify the user and store in table
			user.setAccountVerified(true);
			userRepo.save(user);
			System.out.println(user.isAccountVerified());

			//we don't need invalid password
			secureTokenRepository.removeByToken(code);
			return true;
		}
	}
}
