package com.siammali.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siammali.domain.UserDocument;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, Long> {
}