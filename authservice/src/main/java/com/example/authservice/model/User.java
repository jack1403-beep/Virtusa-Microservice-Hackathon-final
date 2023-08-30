package com.example.authservice.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "UserProfile")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int userId;

	@Enumerated(EnumType.STRING)
	private Role userRole;

	@Column(unique = true)
	private String email;

	private String userName;
	
	private String firstName;
	
	private String lastName;

	private String mobileNumber;

	private String password;
	
	@Transient
	private Patient patient;
	
	private boolean accountVerified;
	
	@Transient
	private HealthCareProvider healthCareProvider; 
	
	@Transient
	private List<Adherence> adherences = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE , orphanRemoval = true)
	@JsonIgnore//so that only this table data will be fetch not the list of loan will be there in the result
	private Set<SecureToken> tokens;
	
	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonIgnore
	private PasswordOtp forgotpasswordOtp;
	
	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonIgnore
	private RefreshToken refreshToken;
	

	public User(String email, String userName, String firstName, String lastName, String mobileNumber,
			String password) {
		super();
		this.email = email;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.password = password;
	}


	public User(int userId) {
		this.userId = userId;
	}//for foreign key
	
	
	
	
	
	

}
