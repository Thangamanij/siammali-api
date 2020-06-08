package com.siammali.controller;

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

import com.siammali.domain.UserRole;
import com.siammali.dto.UserRoleDto;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.service.UserRoleService;

@RestController
@RequestMapping("/api")
public class UserRoleController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRoleService userRoleService;

	@PostMapping(value = "/user-roles")
	public ResponseEntity<List<UserRole>> addUserRole(@Valid @RequestBody UserRoleDto userRole)
			throws URISyntaxException {
		try {
			List<UserRole> uRoles = userRoleService.saveUserRole(userRole);
			return ResponseEntity.ok(uRoles);
		} catch (BadResourceException ex) {
			// log exception first, then return Bad Request (400)
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping(value = "/user-roles/{userId}")
	public ResponseEntity<List<UserRole>> updateUserRole(@Valid @RequestBody UserRoleDto userRoleDto,
			@PathVariable Long userId) {
		try {
			List<UserRole> uRoles = userRoleService.updateUserRole(userRoleDto, userId);
			return ResponseEntity.ok(uRoles);
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

	@DeleteMapping(path = "/user-roles/{userId}")
	public ResponseEntity<Void> deleteUserRoleByUserId(@PathVariable Long userId) {
		try {
			userRoleService.deleteByUsersId(userId);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/user-roles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserRole>> findAll() {
		return ResponseEntity.ok(userRoleService.findAll());

	}

	@GetMapping(value = "/user-roles/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserRole>> findUserRoleByUserId(@PathVariable Long userId) {
		try {
			List<UserRole> userRoles = userRoleService.findByUserId(userId);
			return ResponseEntity.ok(userRoles); // return 200, with json body
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}