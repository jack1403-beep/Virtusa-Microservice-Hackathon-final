package com.example.authservice.modeldto;

import java.time.Instant;

import com.example.authservice.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

	private User user;
	private String jwtToken;
	private String refreshToken;
	private Instant expireAt;

	public JwtResponse(User user, String jwtToken) {
		this.user = user;
		this.jwtToken = jwtToken;
	}



	public JwtResponse(User user, String jwtToken, String refreshToken, Instant expireAt) {
		super();
		this.user = user;
		this.jwtToken = jwtToken;
		this.refreshToken = refreshToken;
		this.expireAt = expireAt;
	}
	
	



	public User getUser() {
		return user;
	}

	public JwtResponse(User user, String jwtToken, Instant expireAt) {
	super();
	this.user = user;
	this.jwtToken = jwtToken;
	this.expireAt = expireAt;
}



	public void setUser(User user) {
		this.user = user;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Instant getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Instant expireAt) {
		this.expireAt = expireAt;
	}


}
