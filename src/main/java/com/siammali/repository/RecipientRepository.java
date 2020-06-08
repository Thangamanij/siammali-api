package com.siammali.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siammali.domain.Recipient;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
	Optional<Recipient> findByEmail(String email);

	List<Recipient> findByFirstNameIsLike(String firstName);
}