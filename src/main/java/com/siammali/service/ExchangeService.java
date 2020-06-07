package com.siammali.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siammali.domain.Customer;
import com.siammali.domain.ExchangeDetails;
import com.siammali.domain.Recipient;
import com.siammali.domain.Status;
import com.siammali.domain.TransferDetails;
import com.siammali.dto.ApprovalDto;
import com.siammali.dto.ExchangeDTO;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.repository.ExchangeDetailsRepository;

@Service
public class ExchangeService {
	@Autowired
	StatusService statusService;

	@Autowired
	CustomerService userService;

	@Autowired
	RecipientsService recipientService;

	@Autowired
	ExchangeDetailsRepository detailsRepository;

	public ExchangeDetails saveExchange(ExchangeDTO exchangeDto) {
		ExchangeDetails details = convertDomain(exchangeDto);
		return save(details);

	}

	public ExchangeDetails save(ExchangeDetails details) {
		return detailsRepository.save(details);
	}

	private ExchangeDetails convertDomain(ExchangeDTO exchangeDto) {
		ExchangeDetails details = getExchangeDetails(exchangeDto);
		Customer appUser = userService.findById(exchangeDto.getUser());
		Recipient recipient = recipientService.getRecipientById(exchangeDto.getRecipient());
		Status status = statusService.findById(exchangeDto.getStatus());
		TransferDetails transferDetails = convertTransferDomain(exchangeDto);
		details.setUser(appUser);
		details.setStatus(status);
		details.setRecipient(recipient);
		details.setTransferDetails(transferDetails);
		return details;

	}

	private TransferDetails convertTransferDomain(ExchangeDTO exchangeDto) {
		TransferDetails transferDetails = new TransferDetails();
		transferDetails.setTransferMode(exchangeDto.getTransferMode());
		transferDetails.setBankName(exchangeDto.getBankName());
		transferDetails.setAccountName(exchangeDto.getAccountName());
		transferDetails.setAccountNumber(exchangeDto.getAccountNumber());
		transferDetails.setIfsc(exchangeDto.getIfsc());
		transferDetails.setPlaceOfCollect(exchangeDto.getPlaceOfCollect());
		return transferDetails;

	}

	private ExchangeDetails getExchangeDetails(ExchangeDTO exchangeDto) {
		ExchangeDetails details = new ExchangeDetails();
		details.setSourceAmount(exchangeDto.getSourceAmount());
		details.setSourceCurrency(exchangeDto.getSourceCurrency());
		details.setTargetCurrency(exchangeDto.getTargetCurrency());
		details.setTargetAmount(exchangeDto.getTargetAmount());
		details.setCreatedBy(exchangeDto.getUser() != null ? exchangeDto.getUser().toString() : null);
		details.setCreatedDate(new Date());
		return details;
	}

	public ExchangeDetails findExchangeById(Long id) throws ResourceNotFoundException {
		ExchangeDetails details = detailsRepository.findById(id).orElse(null);
		if (details == null) {
			throw new ResourceNotFoundException("Cannot find Recipient with id: " + id);
		} else
			return details;
	}

	public List<ExchangeDetails> findAll() {
		return detailsRepository.findAll();
	}

	public ExchangeDetails validateStatus(@Valid ApprovalDto approvalDto) throws ResourceNotFoundException {
		ExchangeDetails savedEntity = null;
		if (approvalDto.getExchangeId() != null) {
			Optional<ExchangeDetails> optExchange = detailsRepository.findById(approvalDto.getExchangeId());
			if (optExchange.isPresent()) {
				ExchangeDetails exchangeDetails = optExchange.get();
				Status status = statusService.findById(approvalDto.getStatus());
				Customer user = userService.findById(exchangeDetails.getUser().getId());
				exchangeDetails.setValidatedBy(user);
				exchangeDetails.setValidatedDate(new Date());
				exchangeDetails.setStatus(status);
				exchangeDetails.setApproverComments(approvalDto.getComments());
				savedEntity = detailsRepository.save(exchangeDetails);
			} else {
				throw new ResourceNotFoundException("Cannot find Recipient with id: " + approvalDto.getExchangeId());
			}
		}
		return savedEntity;
	}

}