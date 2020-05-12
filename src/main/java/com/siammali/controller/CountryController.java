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
			return ResponseEntity.created(new URI("/api/countries/" + newCountry.getId())).body(country);
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

	@PutMapping(value = "/countries/{id}")
	public ResponseEntity<Country> updateContact(@Valid @RequestBody Country country, @PathVariable long id) {
		try {
			country.setId(id);
			countryService.update(country);
			return ResponseEntity.ok().build();
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

	@DeleteMapping(path = "/countries/{id}")
	public ResponseEntity<Void> deleteCountryById(@PathVariable long id) {
		try {
			countryService.deleteById(id);
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
}