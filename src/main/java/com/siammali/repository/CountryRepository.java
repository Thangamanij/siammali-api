package com.siammali.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siammali.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	Optional<Country> findByCode(String code);
}