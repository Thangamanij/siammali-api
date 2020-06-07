package com.siammali.controller;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.siammali.dto.GenericOutput;
import com.siammali.dto.LoggedUserDto;
import com.siammali.dto.SignUpDto;
import com.siammali.dto.UserDto;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.service.CustomerService;
import com.siammali.service.LoginService;

@RestController
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoginService loginService;

	@Autowired
	private CustomerService customerService;

	@PostMapping(value = "/token/signup")
	public ResponseEntity<GenericOutput> registerUser(@Valid @RequestBody SignUpDto signUpDto)
			throws URISyntaxException {
		return ResponseEntity.ok(new GenericOutput(loginService.registerUser(signUpDto)));
	}

	@PostMapping("/token/login")
	public ResponseEntity<LoggedUserDto> getToken(@RequestParam("username") final String username,
			@RequestParam("password") final String password) throws ResourceNotFoundException {
		return ResponseEntity.ok(customerService.login(username, password));
	}

	@PostMapping(value = "/api/update/user-info")
	public ResponseEntity<GenericOutput> updateUserInfo(@Valid @RequestBody UserDto userDto) throws URISyntaxException {
		try {
			return ResponseEntity.ok(new GenericOutput(loginService.updateUserInfo(userDto)));
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

	@PostMapping(value = "/api/verify-user")
	public ResponseEntity<GenericOutput> verifyUser(@RequestParam("userId") final Long userId ) {
		return ResponseEntity.ok(new GenericOutput(loginService.verifyUser(userId)));
	}

}