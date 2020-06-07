package com.siammali.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.siammali.domain.Customer;
import com.siammali.domain.UserRole;
import com.siammali.dto.LoggedUserDto;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	LoginService loginService;

	@Autowired
	UserRoleService userRoleService;

	public LoggedUserDto login(String username, String password) throws ResourceNotFoundException {
		Optional<Customer> customer = customerRepository.login(username, password);
		LoggedUserDto loggeduser = null;
		if (customer.isPresent()) {
			loggeduser = new LoggedUserDto();
			Customer user = customer.get();
			List<UserRole> roles = userRoleService.findByUserId(user.getId());
			List<String> uRoles = populateOutputData(roles);
			loggeduser.setUserId(user.getId());
			loggeduser.setCountry(user.getCountryCode());
			loggeduser.setLanguage(user.getLanguage());
			loggeduser.setToken(user.getToken());
			loggeduser.setRoles(uRoles);
		}
		return loggeduser;
	}

	private List<String> populateOutputData(List<UserRole> userRole) {
		List<String> roles = userRole.stream().map(role -> role.getRole().getCode()).collect(Collectors.toList());
		return roles;
	}

	public Optional<User> findByToken(String token) {
		Optional<Customer> customer = customerRepository.findByToken(token);
		if (customer.isPresent()) {
			Customer customer1 = customer.get();
			User user = new User(customer1.getUserName(), customer1.getPassword(), true, true, true, true,
					AuthorityUtils.createAuthorityList("USER"));
			return Optional.of(user);
		}
		return Optional.empty();
	}

	public Customer findById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return customer.orElse(null);
	}
}
