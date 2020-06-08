package com.siammali.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserRoleDto {
	Long userId;
	List<Long> roles = new ArrayList<>();
}
