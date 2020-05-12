package com.siammali.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siammali.domain.Role;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;

	private boolean existsById(Long id) {
		return roleRepo.existsById(id);
	}

	public Role save(Role role) throws BadResourceException, ResourceAlreadyExistsException {
		if (role != null) {
			return roleRepo.save(role);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save Role");
			exc.addErrorMessage("Role is null or empty");
			throw exc;
		}
	}

	public void update(Role role) throws BadResourceException, ResourceNotFoundException {
		if (role != null) {
			if (!existsById(role.getId())) {
				throw new ResourceNotFoundException("Cannot find Role with id: " + role.getId());
			}
			roleRepo.save(role);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save role");
			exc.addErrorMessage("Role is null or empty");
			throw exc;
		}
	}

	public void deleteById(Long id) throws ResourceNotFoundException {
		if (!existsById(id)) {
			throw new ResourceNotFoundException("Cannot find country with id: " + id);
		} else {
			roleRepo.deleteById(id);
		}
	}
}