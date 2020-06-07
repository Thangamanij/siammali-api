package com.siammali.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.siammali.domain.Contact;
import com.siammali.domain.Country;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepo;

	private boolean existsById(Long id) {
		return countryRepo.existsById(id);
	}

	public Country save(Country country) throws BadResourceException, ResourceAlreadyExistsException {
		if (country != null) {
			if (country.getId() != null && existsById(country.getId())) {
				throw new ResourceAlreadyExistsException("Country with id: " + country.getId() + " already exists");
			}
			return countryRepo.save(country);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save Country");
			exc.addErrorMessage("Country is null or empty");
			throw exc;
		}
	}

	public Country update(Country country) throws BadResourceException, ResourceNotFoundException {
		if (country != null) {
			if (!existsById(country.getId())) {
				throw new ResourceNotFoundException("Cannot find Country with id: " + country.getId());
			}
			return countryRepo.save(country);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save country");
			exc.addErrorMessage("Country is null or empty");
			throw exc;
		}
	}

	public void deleteById(Long id) throws ResourceNotFoundException {
		if (!existsById(id)) {
			throw new ResourceNotFoundException("Cannot find country with id: " + id);
		} else {
			countryRepo.deleteById(id);
		}
	}

	public List<Country> findAll() {
		List<Country> countries = new ArrayList<>();
		countryRepo.findAll().forEach(countries::add);
		return countries;
	}

	public Country findByCode(String code) {
		return countryRepo.findByCode(code).orElse(null);
	}

	public Country findById(Long id) throws ResourceNotFoundException {
		Country country = countryRepo.findById(id).orElse(null);
		if (country == null) {
			throw new ResourceNotFoundException("Cannot find Country with id: " + id);
		} else
			return country;
	}
}