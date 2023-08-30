package com.example.authservice.modeldto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForgotPasswordDto {
	private String email;
	private int otp;
	private String password;
	private String confirmPassword;
}
