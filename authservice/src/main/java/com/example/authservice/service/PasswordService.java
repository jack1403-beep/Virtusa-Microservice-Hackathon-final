package com.example.authservice.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authservice.execption.CustomException;
import com.example.authservice.model.PasswordOtp;
import com.example.authservice.model.User;
import com.example.authservice.modeldto.ChangePasswordDto;
import com.example.authservice.modeldto.ForgotPasswordDto;
import com.example.authservice.modeldto.Mail;
import com.example.authservice.repo.PasswordOtpRepository;
import com.example.authservice.repo.UserRepo;



@Service
public class PasswordService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PasswordOtpRepository passwordOtpRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	MailService mailService;
	
//	generating otp of 6 digit
	Random random = new Random(100000);
	
//	5 * 60 = 300 seconds
	private int otpValidityInSeconds =  300;
	
//	create otp for forgot password
	public ForgotPasswordDto sendOTP(ForgotPasswordDto forgotPasswordDto) throws CustomException {

		String email = forgotPasswordDto.getEmail();
		System.out.println(email);

		int otp = random.nextInt(999999);

		System.out.println("OTP " +otp);

		boolean flag =  sendForgotPasswordOTP(email,otp);
		if(flag) {
			return forgotPasswordDto;
		}else {
			throw new CustomException("Email Id not Registered");
		}


	}

	//	Send the otp through mail
	public boolean sendForgotPasswordOTP(String email,int otp) {

		Mail mail = new Mail();
		mail.setMailFrom("aman123196@gmail.com");

		User user = userRepo.findByEmail(email);


		if(user != null) {

			PasswordOtp present  = passwordOtpRepository.findByUserUserId(user.getUserId());

			if(present != null) {
				passwordOtpRepository.deleteById(present.getId());
			}//if otp is present than remove the otp and genertae new otp
			PasswordOtp forgotpasswordOtp = new PasswordOtp();
			forgotpasswordOtp.setOtp(otp);
			forgotpasswordOtp.setExpireAt(LocalDateTime.now().plusSeconds(otpValidityInSeconds));
			forgotpasswordOtp.setUser(new User(user.getUserId()));
			passwordOtpRepository.save(forgotpasswordOtp);

			String userName = user.getUserName();

			mail.setMailTo(email);
			mail.setMailSubject("OTP Verfication from Health Care Team");

			String content = "Dear [[name]],<br>"
					+ "Below is your OTP to reset your Password:<br>"
					+ "<h3> OTP = "+otp+" </h3>"
					+ "Thank you,<br>"
					+ "Health Care Team.";

			content = content.replace("[[name]]", userName);

			mail.setMailContent(content);

			mailService.sendEmail(mail);
			return true;
		}
		else {
			return false;
		}
	}

//	verify  the otp sent by the user
	@Transactional
	public boolean verifyOTP(ForgotPasswordDto forgotPasswordDto) {
		int otp = forgotPasswordDto.getOtp();
		String email = forgotPasswordDto.getEmail();

		User user = userRepo.findByEmail(email);
		int userId = user.getUserId();

		PasswordOtp forgotpasswordOtp = passwordOtpRepository.findByOtp(otp);
		PasswordOtp forgotpasswordOtp1 =  passwordOtpRepository.findByUserUserId(userId);

		if(Objects.isNull(forgotpasswordOtp) || !(otp == forgotpasswordOtp1.getOtp()) || forgotpasswordOtp.isExpired()) {
			if(!Objects.isNull(forgotpasswordOtp) && forgotpasswordOtp.isExpired()) {
				passwordOtpRepository.removeByOtp(otp);
				return false;
			}
			return false;
		}

		@SuppressWarnings("deprecation")
		User user1 = userRepo.getOne(forgotpasswordOtp.getUser().getUserId());  
		if(Objects.isNull(user1)) {
			return false;
		}else {
			passwordOtpRepository.removeByOtp(otp);
			return true;
		}	
	}

	//	Forgot password changing password 
	public boolean resetPassword(ForgotPasswordDto forgotPasswordDto) throws CustomException{
		String email = forgotPasswordDto.getEmail();

		User user = userRepo.findByEmail(email);

		if(user != null) {
			//hash the password BCryptPasswordEncoder
			String encryptedpassword = getEncodedPassword(forgotPasswordDto.getPassword());
			user.setPassword(encryptedpassword);
			userRepo.save(user);
			return true;
		}else {
			throw new CustomException("User not registered");
		}

	}

	//	change Password of user
	public boolean changePassword(ChangePasswordDto changePasswordDto) {

		String newPassword = changePasswordDto.getNewPassword();
		String oldpassword = changePasswordDto.getOldPassword();

		int userId = changePasswordDto.getUserId();
		User user = userRepo.findById(userId).get();
		String originalPassword = user.getPassword();
		System.out.print(originalPassword);

		//hash the password BCryptPasswordEncoder
		if(passwordEncoder.matches(oldpassword, originalPassword)) {

			String encryptedpassword = getEncodedPassword(newPassword);
			user.setPassword(encryptedpassword);
			userRepo.save(user);
			return true;
		}else {
			return false;
		}


	}	
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
