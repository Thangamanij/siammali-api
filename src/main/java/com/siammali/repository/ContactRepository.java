package com.siammali.repository;
 
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.siammali.domain.Contact;
 
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long>, 
        JpaSpecificationExecutor<Contact> {
}