package com.siammali.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siammali.domain.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}