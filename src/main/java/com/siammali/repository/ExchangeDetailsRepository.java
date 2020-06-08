package com.siammali.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siammali.domain.ExchangeDetails;

@Repository
public interface ExchangeDetailsRepository extends JpaRepository<ExchangeDetails, Long> {
}