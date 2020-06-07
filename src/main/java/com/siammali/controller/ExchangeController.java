package com.siammali.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siammali.domain.ExchangeDetails;
import com.siammali.dto.ApprovalDto;
import com.siammali.dto.ExchangeDTO;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.service.ExchangeService;

@RestController
@RequestMapping("/api")
public class ExchangeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeService exchangeService;

	@PostMapping(value = "/exchanges")
	public ResponseEntity<ExchangeDetails> saveExchangeInformation(@Valid @RequestBody ExchangeDTO exchangeDTO)
			throws URISyntaxException {
		ExchangeDetails newExchange = exchangeService.saveExchange(exchangeDTO);
		return ResponseEntity.created(new URI("/api/exchanges/" + newExchange.getId())).body(newExchange);

	}

	@GetMapping(value = "/exchanges", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ExchangeDetails>> findAll() {
		return ResponseEntity.ok(exchangeService.findAll());
	}

	@GetMapping(value = "/exchanges/{exchangeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExchangeDetails> findExchangeById(@PathVariable Long exchangeId) {
		try {
			ExchangeDetails exchangeDetails = exchangeService.findExchangeById(exchangeId);
			return ResponseEntity.ok(exchangeDetails); // return 200, with json
														// body
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping(value = "/validate")
	public ResponseEntity<ExchangeDetails> validate(@Valid @RequestBody ApprovalDto approvalDto) {
		try {
			ExchangeDetails exchangeDetails = exchangeService.validateStatus(approvalDto);
			return ResponseEntity.ok(exchangeDetails);
		} catch (ResourceNotFoundException ex) {
			// log exception first, then return Not Found (404)
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

}