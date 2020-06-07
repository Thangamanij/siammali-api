package com.siammali.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siammali.domain.BrokerDetails;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.repository.BrokerDetailsRepository;

@Service
public class BrokerDetailService {

	@Autowired
	private BrokerDetailsRepository brokerDetailRepo;

	private boolean existsById(Long id) {
		return brokerDetailRepo.existsById(id);
	}

	public BrokerDetails save(BrokerDetails brokerDetail) throws BadResourceException, ResourceAlreadyExistsException {
		if (brokerDetail != null) {
			if (brokerDetail.getId() != null && existsById(brokerDetail.getId())) {
				throw new ResourceAlreadyExistsException(
						"BrokerDetails with id: " + brokerDetail.getId() + " already exists");
			}
			return brokerDetailRepo.save(brokerDetail);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save BrokerDetail");
			exc.addErrorMessage("BrokerDetail is null or empty");
			throw exc;
		}

	}

	public void update(BrokerDetails brokerDetail) throws BadResourceException, ResourceNotFoundException {
		if (brokerDetail != null) {
			if (!existsById(brokerDetail.getId())) {
				throw new ResourceNotFoundException("Cannot find BrokerDetail with id: " + brokerDetail.getId());
			}
			brokerDetailRepo.save(brokerDetail);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save brokerDetail");
			exc.addErrorMessage("BrokerDetail is null or empty");
			throw exc;
		}
	}

	public void deleteById(Long id) throws ResourceNotFoundException {
		if (!existsById(id)) {
			throw new ResourceNotFoundException("Cannot find brokerDetail with id: " + id);
		} else {
			brokerDetailRepo.deleteById(id);
		}
	}

	public List<BrokerDetails> findAll() {
		List<BrokerDetails> countries = new ArrayList<>();
		brokerDetailRepo.findAll().forEach(countries::add);
		return countries;
	}

	public BrokerDetails findById(Long id) throws ResourceNotFoundException {
		BrokerDetails brokerDetail = brokerDetailRepo.findById(id).orElse(null);
		if (brokerDetail == null) {
			throw new ResourceNotFoundException("Cannot find BrokerDetail with id: " + id);
		} else
			return brokerDetail;
	}
}