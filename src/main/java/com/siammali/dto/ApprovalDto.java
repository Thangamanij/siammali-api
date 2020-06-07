package com.siammali.dto;

import lombok.Data;

@Data
public class ApprovalDto {
	private Long exchangeId;
	private Long userId;
	private Long status;
	private String comments;

}
