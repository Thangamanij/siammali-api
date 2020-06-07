package com.siammali.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String userName;
	private String password;
	private String token;
	private String email;
	private String firstName;
	private String lastName;
	private Long mobileNumber;
	private String countryCode;
	private String place;
	private String address1;
	private String address2;
	private String address3;
	private Boolean isVerfied;
	private String language;
	@OneToOne(cascade = CascadeType.ALL)
	private UserDocument proof;

}