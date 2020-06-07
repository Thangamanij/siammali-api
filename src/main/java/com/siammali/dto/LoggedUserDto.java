package com.siammali.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LoggedUserDto {
	private Long userId;
	private String language;
	private String country;
	private String token;
	private List<String> roles = new ArrayList<>();

}
