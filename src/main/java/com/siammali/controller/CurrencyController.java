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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siammali.domain.Currency;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.service.CurrencyService;

@RestController
@RequestMapping("/api")
public class CurrencyController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencyService currencyService;

	@PostMapping(value = "/currencies")
	public ResponseEntity<Currency> addCurrency(@Valid @RequestBody Currency currency) throws URISyntaxException {
		try {
			Currency newCurrency = currencyService.save(currency);
			return ResponseEntity.ok(newCurrency);
		} catch (ResourceAlreadyExistsException ex) {
			// log exception first, then return Conflict (409)
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			// log exception first, then return Bad Request (400)
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping(value = "/currencies/{currencyId}")
	public ResponseEntity<Currency> updateContact(@Valid @RequestBody Currency currency,
			@PathVariable Long currencyId) {
		try {
			currency.setId(currencyId);
			return ResponseEntity.ok(currencyService.update(currency));
		} catch (ResourceNotFoundException ex) {
			// log exception first, then return Not Found (404)
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			// log exception first, then return Bad Request (400)
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping(path = "/currencies/{currencyId}")
	public ResponseEntity<Void> deleteCurrencyById(@PathVariable Long currencyId) {
		try {
			currencyService.deleteById(currencyId);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/currencies/{currencyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Currency> findCurrencyById(@PathVariable Long currencyId) {
		try {
			Currency currency = currencyService.findById(currencyId);
			return ResponseEntity.ok(currency); // return 200, with json body
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Currency>> findAll() {
		return ResponseEntity.ok(currencyService.findAll());

	}
}