package com.example.authservice.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.example.authservice.commonservice.ViewAdherence;
import com.example.authservice.commonservice.ViewHealthCareProvider;
import com.example.authservice.commonservice.ViewPatient;
import com.example.authservice.execption.CustomException;
import com.example.authservice.model.HealthCareProvider;
import com.example.authservice.model.Patient;
import com.example.authservice.model.Role;
import com.example.authservice.model.SecureToken;
import com.example.authservice.model.User;
import com.example.authservice.modeldto.AuthEmailDto;
import com.example.authservice.modeldto.Mail;
import com.example.authservice.modeldto.SignUpResponse;
import com.example.authservice.modeldto.UserModelDto;
import com.example.authservice.repo.SecureTokenRepository;
import com.example.authservice.repo.UserRepo;

import net.bytebuddy.utility.RandomString;




@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ViewPatient patient;
	
	@Autowired
	ViewAdherence viewAdherence;
	
	@Autowired
	ViewHealthCareProvider careProvider;
	
	@Autowired
	MailService mailService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	SecureTokenRepository secureTokenRepository;
	
//	secure token configuration
	//	#60 * 60 * 8 setting as 8 hours = 28800 seconds
	private int tokenValidityInSeconds = 28800;
	

	@Transactional
	public SignUpResponse saveUser(UserModelDto userdto, HttpServletRequest request) {

		//check if the user is already present
		if(Objects.nonNull(userRepo.findByEmail(userdto.getEmail()))) {
			//we have an user
			throw new CustomException("User already present");
		}

		//hash the password BCryptPasswordEncoder

		String encryptedpassword = getEncodedPassword(userdto.getPassword());


		//save the user
		User submituser = new User(userdto.getEmail(), userdto.getUserName(), userdto.getFirstName()
				, userdto.getLastName(),
				userdto.getMobileNumber(), encryptedpassword);
		submituser.setUserRole(userdto.getUserRole());
		submituser.setAccountVerified(true);
		userRepo.save(submituser);
		
		
//		if the role is patient only than send the mail
		if(submituser.getUserRole().equals(Role.PATIENT) || submituser.getUserRole().equals(Role.HEALTHCAREPROVIDER)) {
			//			creating token 
			String randomCode = RandomString.make(64);

			//			saving token in token class 
			SecureToken secureToken = new SecureToken();
			secureToken.setToken(randomCode);
			secureToken.setExpireAt(LocalDateTime.now().plusSeconds(tokenValidityInSeconds));
			secureToken.setUser(new User(submituser.getUserId()));
			secureTokenRepository.save(secureToken);		

			//			getting localhost url
			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			System.out.println(appUrl);

			sendVerificationEmail(submituser, appUrl, secureToken);
		}

		SignUpResponse response = new SignUpResponse("success", "User added");
		return response;
	}
	
//	sending the mail for email verificatiion
	private void sendVerificationEmail(User submituser, String appUrl, SecureToken secureToken)  {
		Mail mail = new Mail();
		mail.setMailFrom("aman123196@gmail.com");
		mail.setMailTo(submituser.getEmail());
		mail.setMailSubject("Please verify your registration");
		String content = "Dear [[name]],<br>"
				+ "Please click the link below to verify your registration, Link is only valid for 8 hours:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
				+ "Thank you,<br>"
				+ "Health Care Team.";


		content = content.replace("[[name]]", submituser.getFirstName());
		String verifyURL = appUrl + "/authToken/verify?code=" + secureToken.getToken();

		content = content.replace("[[URL]]", verifyURL);

		mail.setMailContent(content);

		mailService.sendEmail(mail);
	}
	

	public User getUser(int userId) {
		
		//get the user from database wity the help of user repo
		User user = userRepo.findById(userId).get();
		
		//fetch patient of the above user from patient microservice
		if(user.getUserRole().equals(Role.PATIENT)) {
			Patient result =  patient.getPatientByUserId(userId).get();
			user.setPatient(result);
		}else if(user.getUserRole().equals(Role.HEALTHCAREPROVIDER)){
			HealthCareProvider result = careProvider.getHealthCareProviderByUserId(userId).get();
			user.setHealthCareProvider(result);
		}
		
		return user;
	}

	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	public String deleteUser(int userId) {
		String response;

		try {
			User user = userRepo.findById(userId).get();
			userRepo.deleteById(userId);
			
			if(user.getUserRole().equals(Role.PATIENT)) {
				patient.deletePatientByUserId(userId);
				viewAdherence.deleteAdherenceByUserId(userId);
			}else {
				careProvider.deleteHealthCareProviderByUserId(userId);
			}
			response = "Successfully Deleted";
		}
		catch(IllegalArgumentException e) {
			response = "Not Deleted , Please try again";
		}
		catch (EmptyResultDataAccessException e) {
			response = "No User Present with this User Id";
		}
		catch(Exception e) {
			response =  "Something Wrong";
		}
		return response;
	}

	public String editUser(UserModelDto userModel, int userId) {
		String response=null;
		try {
			Optional<User> update = userRepo.findById(userId);
			User result = update.get();
			result.setUserName(userModel.getUserName());
			result.setFirstName(userModel.getFirstName());
			result.setLastName(userModel.getLastName());
			result.setMobileNumber(userModel.getMobileNumber());
			if(userRepo.save(result)!=null) {
				response = "Successfully Updated";
			}
			else {
				response = "Not Updated , Please try again";
			}
		}
		catch(NoSuchElementException e) {
			response = "Not Updated, userId is not present";
		}
		catch(Exception e) {
			response = "Something Wrong";
		}
		return response;
	}


	public User getMail(String email) {
		return userRepo.findByEmail(email);
	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
