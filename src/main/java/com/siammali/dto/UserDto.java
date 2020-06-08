package com.siammali.dto;

import lombok.Data;

@Data
public class UserDto {
	private Long userId;
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

}
