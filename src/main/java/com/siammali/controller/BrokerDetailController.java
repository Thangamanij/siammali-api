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

import com.siammali.domain.BrokerDetails;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.service.BrokerDetailService;

@RestController
@RequestMapping("/api")
public class BrokerDetailController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BrokerDetailService brokerDetailService;

	@PostMapping(value = "/brokerDetail")
	public ResponseEntity<BrokerDetails> addBrokerDetails(@Valid @RequestBody BrokerDetails brokerDetail) throws URISyntaxException {
		try {
			BrokerDetails newBrokerDetails = brokerDetailService.save(brokerDetail);
			return ResponseEntity.created(new URI("/api/brokerDetail/" + newBrokerDetails.getId())).body(brokerDetail);
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

	@PutMapping(value = "/brokerDetail/{brokerDetailId}")
	public ResponseEntity<BrokerDetails> updateContact(@Valid @RequestBody BrokerDetails brokerDetail, @PathVariable Long brokerDetailId) {
		try {
			brokerDetail.setId(brokerDetailId);
			brokerDetailService.update(brokerDetail);
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

	@DeleteMapping(path = "/brokerDetail/{brokerDetailId}")
	public ResponseEntity<Void> deleteBrokerDetailsById(@PathVariable Long brokerDetailId) {
		try {
			brokerDetailService.deleteById(brokerDetailId);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/brokerDetail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BrokerDetails>> findAll() {
		return ResponseEntity.ok(brokerDetailService.findAll());

	}

	@GetMapping(value = "/brokerDetail/{brokerDetailId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrokerDetails> findBrokerDetailsById(@PathVariable Long brokerDetailId) {
		try {
			BrokerDetails brokerDetail = brokerDetailService.findById(brokerDetailId);
			return ResponseEntity.ok(brokerDetail); // return 200, with json body
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}