package com.ecom.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.ContactUs;

@Repository
public interface ContactUsRepository extends PagingAndSortingRepository<ContactUs, Long> {

}
