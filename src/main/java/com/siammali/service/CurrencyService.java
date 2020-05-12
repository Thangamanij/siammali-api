package com.siammali.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siammali.domain.Currency;
import com.siammali.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepo;

	public List<Currency> findAll() {
		return currencyRepo.findAll();
	}
}