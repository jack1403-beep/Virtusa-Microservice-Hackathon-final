package com.hackathon.UserProfile.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hackathon.UserProfile.commonservice.ViewHealthCareProvider;
import com.hackathon.UserProfile.commonservice.ViewPatient;
import com.hackathon.UserProfile.exception.CustomException;
import com.hackathon.UserProfile.model.HealthCareProvider;
import com.hackathon.UserProfile.model.Medication;
import com.hackathon.UserProfile.model.Patient;
import com.hackathon.UserProfile.model.Role;
import com.hackathon.UserProfile.model.User;
import com.hackathon.UserProfile.modeldto.AuthEmailDto;
import com.hackathon.UserProfile.modeldto.SignUpResponse;
import com.hackathon.UserProfile.modeldto.UserModelDto;
import com.hackathon.UserProfile.repo.UserRepo;


@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ViewPatient patient;
	
	@Autowired
	ViewHealthCareProvider careProvider;

//	@Autowired
//	PasswordEncoder passwordEncoder;

	@Transactional
	public SignUpResponse saveUser(UserModelDto userdto) {

		//check if the user is already present
		if(Objects.nonNull(userRepo.findByEmail(userdto.getEmail()))) {
			//we have an user
			throw new CustomException("User already present");
		}

		//hash the password BCryptPasswordEncoder
		String encryptedpassword = userdto.getPassword();

		//save the user
		User submituser = new User(userdto.getEmail(), userdto.getUserName(), userdto.getFirstName()
				, userdto.getLastName(),
				userdto.getMobileNumber(), encryptedpassword);
		submituser.setUserRole(userdto.getUserRole());
		submituser.setAccountVerified(false);
		userRepo.save(submituser);

		SignUpResponse response = new SignUpResponse("success", "User added");
		return response;
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
			userRepo.deleteById(userId);
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
			result.setPassword(userModel.getPassword());
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


	public User getMail(AuthEmailDto authEmailDto) {
		return userRepo.findByEmail(authEmailDto.getEmail());
	}

//	public String getEncodedPassword(String password) {
//		return passwordEncoder.encode(password);
//	}
}
