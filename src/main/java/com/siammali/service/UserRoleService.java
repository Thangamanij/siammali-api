package com.siammali.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siammali.domain.AppUser;
import com.siammali.domain.Customer;
import com.siammali.domain.Role;
import com.siammali.domain.UserRole;
import com.siammali.dto.UserRoleDto;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.repository.CustomerRepository;
import com.siammali.repository.RoleRepository;
import com.siammali.repository.UserRoleRepository;

@Service
public class UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private CustomerRepository userRepo;

	private boolean existsById(Long id) {
		return userRoleRepo.existsById(id);
	}

	public List<UserRole> saveUserRole(UserRoleDto userRoleDto) throws BadResourceException {
		List<UserRole> userRoles = null;
		if (!userRoleDto.getRoles().isEmpty() && userRoleDto.getUserId() != null) {
			userRoles = convertToDomain(userRoleDto);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save UserRole");
			exc.addErrorMessage("UserRole is null or empty");
			throw exc;
		}
		return saveAll(userRoles);
	}

	private List<UserRole> convertToDomain(UserRoleDto userRoleDto) {
		List<Role> roles = roleRepo.findByIdIn(userRoleDto.getRoles());
		Customer user = userRepo.findById(userRoleDto.getUserId()).get();
		List<UserRole> userRoles = roles.stream().map(role -> {
			UserRole uRole = new UserRole();
			uRole.setUser(user);
			uRole.setRole(role);
			return uRole;
		}).collect(Collectors.toList());
		return userRoles;
	}

	public UserRole save(UserRole userRole) throws BadResourceException, ResourceAlreadyExistsException {
		if (userRole.getId() != null && existsById(userRole.getId())) {
			throw new ResourceAlreadyExistsException("UserRole with id: " + userRole.getId() + " already exists");
		} else {
			BadResourceException exc = new BadResourceException("Failed to save UserRole");
			exc.addErrorMessage("UserRole is null or empty");
			throw exc;
		}
	}

	public List<UserRole> saveAll(List<UserRole> userRoles) {
		return userRoleRepo.saveAll(userRoles);
	}

	public List<UserRole> updateUserRole(UserRoleDto userRoleDto, Long userId)
			throws BadResourceException, ResourceNotFoundException {
		List<UserRole> userRoles = null;
		if (userRoleDto != null && userId != null) {
			Boolean result = deleteByUsersId(userId);
			if (result) {
				userRoles = saveUserRole(userRoleDto);
			}
		} else {
			BadResourceException exc = new BadResourceException("Failed to save userRole");
			exc.addErrorMessage("UserRole is null or empty");
			throw exc;
		}
		return userRoles;
	}

	public void deleteById(Long id) throws ResourceNotFoundException {
		if (!existsById(id)) {
			throw new ResourceNotFoundException("Cannot find userRole with id: " + id);
		} else {
			userRoleRepo.deleteById(id);
		}
	}

	public Boolean deleteByUsersId(Long id) throws ResourceNotFoundException {
		List<UserRole> uRoles = userRoleRepo.findByUserId(id);
		Boolean result = Boolean.FALSE;
		if (!uRoles.isEmpty()) {
			userRoleRepo.deleteAll();
			result = Boolean.TRUE;
		}
		return result;
	}

	public List<UserRole> findAll() {
		List<UserRole> userRoles = new ArrayList<>();
		userRoleRepo.findAll().forEach(userRoles::add);
		return userRoles;
	}

	public UserRole findById(Long id) throws ResourceNotFoundException {
		UserRole userRole = userRoleRepo.findById(id).orElse(null);
		if (userRole == null) {
			throw new ResourceNotFoundException("Cannot find UserRole with id: " + id);
		} else
			return userRole;
	}

	public List<UserRole> findByUserId(Long id) throws ResourceNotFoundException {
		List<UserRole> userRoles = userRoleRepo.findByUserId(id);
		if (userRoles == null) {
			throw new ResourceNotFoundException("Cannot find UserRole with id: " + id);
		} else
			return userRoles;
	}
}