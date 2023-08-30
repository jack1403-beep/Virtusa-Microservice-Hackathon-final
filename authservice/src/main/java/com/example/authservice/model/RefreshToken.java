package com.example.authservice.model;

import java.time.Instant;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//to refresh the jwttoken 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "healthrefreshTokens")
public class RefreshToken {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String token;

	private Instant createdDate;

	@Column(updatable = false)
	@Basic(optional = false)
	private LocalDateTime expireAt;

	@Transient
	private boolean isExpired;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JsonIgnore
	@JoinColumn(name = "user_id", referencedColumnName ="userId",nullable = false)
	private User user;

	public boolean isExpired() {
		return getExpireAt().isBefore(LocalDateTime.now()); // this is generic implementation, you can always make it timezone specific
	}


}
