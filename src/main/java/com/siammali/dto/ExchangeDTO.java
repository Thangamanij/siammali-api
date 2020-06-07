package com.siammali.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ExchangeDTO {
	private Long user;
	private Long status;
	private Long recipient;
	private BigDecimal sourceAmount;
	private String sourceCurrency;
	private String targetCurrency;
	private BigDecimal targetAmount;
	private BigDecimal commisionAmount;
	private String transferMode;
	private String bankName;
	private String accountName;
	private Long accountNumber;
	private String ifsc;
	private String placeOfCollect;
	private String comments;

}
