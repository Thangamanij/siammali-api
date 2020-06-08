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

import com.siammali.domain.Country;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.service.CountryService;

@RestController
@RequestMapping("/api")
public class CountryController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CountryService countryService;

	@PostMapping(value = "/countries")
	public ResponseEntity<Country> addCountry(@Valid @RequestBody Country country) throws URISyntaxException {
		try {
			Country newCountry = countryService.save(country);
			return ResponseEntity.ok(newCountry);
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

	@PutMapping(value = "/countries/{countryId}")
	public ResponseEntity<Country> updateContact(@Valid @RequestBody Country country, @PathVariable Long countryId) {
		try {
			country.setId(countryId);
			return ResponseEntity.ok(countryService.update(country));
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

	@DeleteMapping(path = "/countries/{countryId}")
	public ResponseEntity<Void> deleteCountryById(@PathVariable Long countryId) {
		try {
			countryService.deleteById(countryId);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Country>> findAll() {
		return ResponseEntity.ok(countryService.findAll());

	}

	@GetMapping(value = "/countries/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Country> findCountryById(@PathVariable Long countryId) {
		try {
			Country country = countryService.findById(countryId);
			return ResponseEntity.ok(country); // return 200, with json body
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}