package com.siammali.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siammali.domain.Role;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleService roleService;

	@PostMapping(value = "/roles")
	public ResponseEntity<Role> addRole(@Valid @RequestBody Role role) throws URISyntaxException {
		try {
			Role newRole = roleService.save(role);
			return ResponseEntity.created(new URI("/api/roles/" + newRole.getId())).body(role);
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

	@PutMapping(value = "/roles/{id}")
	public ResponseEntity<Role> updateRole(@Valid @RequestBody Role role, @PathVariable long id) {
		try {
			role.setId(id);
			roleService.update(role);
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

	@DeleteMapping(path = "/roles/{id}")
	public ResponseEntity<Void> deleteRoleById(@PathVariable long id) {
		try {
			roleService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
}