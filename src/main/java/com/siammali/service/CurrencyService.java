package com.siammali.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siammali.domain.Country;
import com.siammali.domain.Currency;
import com.siammali.exception.BadResourceException;
import com.siammali.exception.ResourceAlreadyExistsException;
import com.siammali.exception.ResourceNotFoundException;
import com.siammali.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepo;

	private boolean existsById(Long id) {
		return currencyRepo.existsById(id);
	}

	public Currency save(Currency currency) throws BadResourceException, ResourceAlreadyExistsException {
		if (currency != null) {
			if (currency.getId() != null && existsById(currency.getId())) {
				throw new ResourceAlreadyExistsException("Currency with id: " + currency.getId() + " already exists");
			}
			return currencyRepo.save(currency);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save currency");
			exc.addErrorMessage("currency is null or empty");
			throw exc;
		}
	}

	public Currency update(Currency currency) throws BadResourceException, ResourceNotFoundException {
		if (currency != null) {
			if (!existsById(currency.getId())) {
				throw new ResourceNotFoundException("Cannot find Currency with id: " + currency.getId());
			}
			return currencyRepo.save(currency);
		} else {
			BadResourceException exc = new BadResourceException("Failed to save currency");
			exc.addErrorMessage("Currency is null or empty");
			throw exc;
		}
	}

	public void deleteById(Long id) throws ResourceNotFoundException {
		if (!existsById(id)) {
			throw new ResourceNotFoundException("Cannot find currency with id: " + id);
		} else {
			currencyRepo.deleteById(id);
		}
	}

	public List<Currency> findAll() {
		List<Currency> countries = new ArrayList<>();
		currencyRepo.findAll().forEach(countries::add);
		return countries;
	}

	public Currency findById(Long id) throws ResourceNotFoundException {
		Currency currency = currencyRepo.findById(id).orElse(null);
		if (currency == null) {
			throw new ResourceNotFoundException("Cannot find Currency with id: " + id);
		} else
			return currency;
	}
}