package com.example.authservice.modeldto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckValidTokenDto 
{
	String email;
	String jwtToken;
	
	
	
}
