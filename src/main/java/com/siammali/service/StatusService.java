package com.siammali.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.siammali.domain.Contact;
import com.siammali.domain.Role;
import com.siammali.domain.Status;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.repository.RoleRepository;
import com.siammali.repository.StatusRepository;

@Service
public class StatusService {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private StatusRepository statusRepo;

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

	public Status findById(Long id) {
		Status status = null;
		if (id != null) {
			status = statusRepo.findById(id).orElse(null);
		}
		return status;
	}

}