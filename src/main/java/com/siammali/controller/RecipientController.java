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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.siammali.domain.Recipient;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.service.RecipientsService;

@RestController
@RequestMapping("/api")
public class RecipientController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RecipientsService recipientsService;

	@PostMapping(value = "/recipient")
	public ResponseEntity<Recipient> addRecipient(@Valid @RequestBody Recipient recipient) throws URISyntaxException {
		try {
			Recipient newRecipient = recipientsService.save(recipient);
			return ResponseEntity.ok(newRecipient);
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

	@GetMapping(value = "/recipients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Recipient>> findAll(@RequestParam(required = false) String name) {
		if (StringUtils.isEmpty(name)) {
			return ResponseEntity.ok(recipientsService.findAll());
		} else {
			return ResponseEntity.ok(recipientsService.findAllByName(name));
		}
	}

	@GetMapping(value = "/recipients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Recipient> findRecipientById(@PathVariable long id) {
		try {
			Recipient recipient = recipientsService.findById(id);
			return ResponseEntity.ok(recipient); // return 200, with json body
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping(value = "/recipients/{id}")
	public ResponseEntity<Recipient> updateRecipient(@Valid @RequestBody Recipient recipient, @PathVariable long id) {
		try {
			recipient.setId(id);
			return ResponseEntity.ok(recipientsService.update(recipient));
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

	@DeleteMapping(path = "/recipients/{id}")
	public ResponseEntity<Void> deleteRecipientById(@PathVariable long id) {
		try {
			recipientsService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

}