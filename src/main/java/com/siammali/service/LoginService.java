package com.siammali.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siammali.constants.SiammaliConstants;
import com.siammali.domain.Customer;
import com.siammali.domain.Role;
import com.siammali.domain.UserRole;
import com.siammali.dto.SignUpDto;
import com.siammali.dto.UserDto;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.repository.CustomerRepository;
import com.siammali.repository.RoleRepository;
import com.siammali.repository.UserRoleRepository;

@Service
public class LoginService {

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	RoleService roleService;

	private Customer saveUser(UserDto userDto) {
		Customer user = convertDomain(userDto);
		return SaveCustomer(user);
	}

	private Customer SaveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	private UserRole saveUserRole(Customer user) {
		UserRole uRole = new UserRole();
		Role role = roleService.findByCode(SiammaliConstants.USER);
		uRole.setUser(user);
		uRole.setRole(role);
		uRole.setCreatedDate(new Date());
		return userRoleRepository.save(uRole);
	}

	private Boolean existsByUserName(String userName) {
		Optional<Customer> user = customerRepository.findByUserName(userName);
		return user.isPresent();
	}

	public boolean registerUser(SignUpDto signUpDto) {
		boolean result = false;
		if (signUpDto.getUserName() != null) {
			Customer customer = new Customer();
			customer.setUserName(signUpDto.getUserName());
			customer.setPassword(signUpDto.getPassword());
			customer.setEmail(signUpDto.getEmail());
			customer.setToken(UUID.randomUUID().toString());
			Customer savedCustomer = SaveCustomer(customer);
			if (savedCustomer != null && savedCustomer.getId() != null) {
				saveUserRole(savedCustomer);
				if (savedCustomer != null) {
					UserRole userRole = saveUserRole(savedCustomer);
					if (userRole != null) {
						result = true;
					}
				}
			}
		}
		return result;
	}

	public boolean updateUserInfo(UserDto userDto) throws BadResourceException, ResourceAlreadyExistsException {
		boolean result = false;
		if (userDto.getUserId() != null) {
			Optional<Customer> optCustomer = customerRepository.findById(userDto.getUserId());
			if (optCustomer.isPresent()) {
				Customer customer = optCustomer.get();
				customer.setEmail(userDto.getEmail());
				customer.setFirstName(userDto.getFirstName());
				customer.setLastName(userDto.getLastName());
				customer.setAddress1(userDto.getAddress1());
				customer.setAddress2(userDto.getAddress2());
				customer.setAddress3(userDto.getAddress3());
				customer.setIsVerfied(Boolean.FALSE);
				Customer savedCust = SaveCustomer(customer);
				if (savedCust != null)
					result = true;
			}
		}
		return result;
	}

	public boolean verifyUser(Long userId) {
		boolean result = false;
		if (userId != null) {
			Optional<Customer> user = customerRepository.findById(userId);
			if (user.isPresent()) {
				Customer existUser = user.get();
				existUser.setIsVerfied(Boolean.TRUE);
				customerRepository.save(existUser);
				result = true;
			}
		}
		return result;
	}

	private Customer convertDomain(UserDto userDto) {
		Customer user = new Customer();
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setIsVerfied(Boolean.FALSE);
		return user;
	}

}
