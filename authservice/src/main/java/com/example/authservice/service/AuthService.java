package com.example.authservice.service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.authservice.execption.CustomException;
import com.example.authservice.model.User;
import com.example.authservice.modeldto.AuthEmailDto;
import com.example.authservice.modeldto.CheckValidTokenDto;
import com.example.authservice.modeldto.JwtRequest;
import com.example.authservice.modeldto.JwtResponse;
import com.example.authservice.modeldto.RefreshTokenRequest;
import com.example.authservice.util.JwtUtil;


@Service
public class AuthService implements UserDetailsService {


	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	RefreshTokenService refreshTokenService;
	
	@Autowired
	UserService userService;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest)  throws Exception {
		String email = jwtRequest.getEmail();
		String password = jwtRequest.getPassword();
		authenticate(email, password);

		UserDetails userDetails = loadUserByUsername(email);
		System.out.print(userDetails);
		String newGeneratedToken = jwtUtil.generateToken(userDetails);

		User user = userService.getMail(email);
		return JwtResponse.builder()
				.user(user)
				.jwtToken(newGeneratedToken)
				.refreshToken(refreshTokenService.gerenrateRefreshToken(user.getEmail()).getToken())
				.expireAt(Instant.now().plusMillis(jwtUtil.getJwtExpirationInMills()))
				.build();
		
		
	}
	
	private void authenticate(String email, String password) throws Exception {
		User user = userService.getMail(email);
		if(!Objects.isNull(user) && !user.isAccountVerified()) {
			throw new CustomException("USER_DISABLED");
		}
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (BadCredentialsException e) {
			throw new CustomException("INVALID_CREDENTIALS");//encode jis algo se kiya h usi se yaha pr kre Bcrypt use ki h algo
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.getMail(email);

		if(user != null && user.isAccountVerified()) {//here we are checking if user is enabled or not
			System.out.println("User is valid");
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				getAuthorities(user)	
				);

		}else {
			throw new UsernameNotFoundException("User is not valid");
		}
	}
	
	private Set<SimpleGrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
		System.out.println(authorities);
		return authorities;
	}
	
//  refresh token logic
	public JwtResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
		if(refreshTokenService.validateRefreshToken(refreshTokenRequest)) {
			String token = jwtUtil.generateTokenWithEmailId(refreshTokenRequest.getEmail());
			
			User user = userService.getMail(refreshTokenRequest.getEmail());
			
			return JwtResponse.builder()
					.user(user)
					.jwtToken(token)
					.refreshToken(refreshTokenRequest.getRefreshToken())
					.expireAt(Instant.now().plusMillis(jwtUtil.getJwtExpirationInMills()))
					.build();
		}else {
			throw new CustomException("Refresh Token Invalid");
		}

	}

	public String getUserName(RefreshTokenRequest refreshTokenRequest) {
		return jwtUtil.getUsernameFromToken(refreshTokenRequest.getRefreshToken());
	}

	public boolean checkValidToken(CheckValidTokenDto checkValidTokenDto) {
		UserDetails userDetails = loadUserByUsername(checkValidTokenDto.getEmail());
		System.out.println("hi aman");
		return jwtUtil.validateToken(checkValidTokenDto.getJwtToken(), userDetails);
	}

}
