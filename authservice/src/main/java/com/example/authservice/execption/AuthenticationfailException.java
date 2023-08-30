package com.example.authservice.execption;

public class AuthenticationfailException extends IllegalArgumentException  {
	public AuthenticationfailException(String msg) {
		super(msg);
	}

}

