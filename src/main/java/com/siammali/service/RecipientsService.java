package com.siammali.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.siammali.domain.Recipient;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.repository.RecipientRepository;

@Service
public class RecipientsService {

	@Autowired
	private RecipientRepository recipientRepository;

	private boolean existsByEmail(String email) {
		return recipientRepository.findByEmail(email).isPresent();
	}

	private boolean existsById(Long id) {
		return recipientRepository.existsById(id);
	}

	public Recipient save(Recipient recipient) throws BadResourceException, ResourceAlreadyExistsException {
		if (!StringUtils.isEmpty(recipient.getEmail())) {
			if (recipient.getEmail() != null && existsByEmail(recipient.getEmail())) {
				throw new ResourceAlreadyExistsException(
						"Recipient with Email: " + recipient.getEmail() + " already exists");
			}
			return recipientRepository.save(recipient);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save Recipient");
			exc.addErrorMessage("Recipient is null or empty");
			throw exc;
		}
	}

	public Recipient update(Recipient recipient) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(recipient.getId())) {
			if (!existsById(recipient.getId())) {
				throw new ResourceNotFoundException("Cannot find Recipient with id: " + recipient.getId());
			}
			return recipientRepository.save(recipient);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save Recipient");
			exc.addErrorMessage("Recipient is null or empty");
			throw exc;
		}
	}

	public void deleteById(Long id) throws ResourceNotFoundException {
		if (!existsById(id)) {
			throw new ResourceNotFoundException("Cannot find Recipient with id: " + id);
		} else {
			recipientRepository.deleteById(id);
		}
	}

	public Recipient findById(Long id) throws ResourceNotFoundException {
		Recipient recipient = recipientRepository.findById(id).orElse(null);
		if (recipient == null) {
			throw new ResourceNotFoundException("Cannot find Recipient with id: " + id);
		} else
			return recipient;
	}

	public Recipient getRecipientById(Long id) {
		Recipient recipient = null;
		if (id != null) {
			recipient = recipientRepository.findById(id).orElse(null);
		}
		return recipient;
	}

	public List<Recipient> findAll() {
		return recipientRepository.findAll();
	}

	public List<Recipient> findAllByName(String name) {
		return recipientRepository.findByFirstNameIsLike("%" + name + "%");
	}

}