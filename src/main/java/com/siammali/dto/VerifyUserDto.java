package com.siammali.dto;

import lombok.Data;

@Data
public class VerifyUserDto {
	private String email;
	private String firstName;
	private String lastName;
	private Long mobileNumber;

}
