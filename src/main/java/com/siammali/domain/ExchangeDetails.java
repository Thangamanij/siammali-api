package com.siammali.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class ExchangeDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	private Status status;

	@ManyToOne(cascade = CascadeType.ALL)
	private Customer user;

	@ManyToOne(cascade = CascadeType.ALL)
	private Recipient recipient;
	private BigDecimal sourceAmount;
	private String sourceCurrency;
	private String targetCurrency;
	private BigDecimal targetAmount;
	private BigDecimal commisionAmount;

	@OneToOne(cascade = CascadeType.ALL)
	private TransferDetails transferDetails;

	@OneToMany(cascade = CascadeType.ALL)
	List<ExchangeDocument> attachment;
	String comments;
	String createdBy;
	Date createdDate;
	String updatedBy;
	Date updatedDate;
	String approverComments;
	@ManyToOne(cascade = CascadeType.ALL)
	private Customer validatedBy;
	Date validatedDate;
}