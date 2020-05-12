package com.siammali.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siammali.domain.Currency;
import com.siammali.service.CurrencyService;

@RestController
@RequestMapping("/api")
public class CurrencyController {

	@Autowired
	private CurrencyService currencyService;

	@GetMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Currency>> findAll() {
		return ResponseEntity.ok(currencyService.findAll());

	}
}