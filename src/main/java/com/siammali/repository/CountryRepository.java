package com.siammali.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.siammali.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	@Query("Select cntry FROM Country cntry Where code = :code")
	Optional<Country> findByCode(@Param("code") String code);
}