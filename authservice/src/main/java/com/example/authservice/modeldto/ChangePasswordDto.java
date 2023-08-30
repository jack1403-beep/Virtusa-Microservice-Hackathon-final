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
public class ChangePasswordDto {
	private int userId;
	private String oldPassword;
	private String newPassword;
}
