package com.siammali.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.siammali.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	@Query("Select r FROM Role r Where r.code = :code")
	Optional<Role> findByCode(@Param("code") String code);
}