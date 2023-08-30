package com.example.authservice.model;

import java.sql.Timestamp;
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

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "healthSecureOtp")
public class PasswordOtp {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(unique = true)
	private int otp;

	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp timeStamp;

	@Column(updatable = false)
	@Basic(optional = false)
	private LocalDateTime expireAt;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JsonIgnore
	@JoinColumn(name = "user_id", referencedColumnName ="userId",nullable = false)
	private User user;

	@Transient
	private boolean isExpired;



	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(LocalDateTime expireAt) {
		this.expireAt = expireAt;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public boolean isExpired() {

		return getExpireAt().isBefore(LocalDateTime.now()); // this is generic implementation, you can always make it timezone specific
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
