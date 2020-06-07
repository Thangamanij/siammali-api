package com.siammali.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siammali.domain.BrokerDetails;
import com.siammali.domain.Country;

@Repository
public interface BrokerDetailsRepository extends JpaRepository<BrokerDetails, Long> {
}