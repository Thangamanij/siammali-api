package com.siammali.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siammali.domain.ExchangeDocument;

@Repository
public interface ExchangeDocumentRepository extends JpaRepository<ExchangeDocument, Long> {
}