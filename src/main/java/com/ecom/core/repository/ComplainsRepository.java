package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Complains;


@Repository
public interface ComplainsRepository extends PagingAndSortingRepository<Complains, Long> {

	Page<Complains> findById(Long id, Pageable pageable);

	Complains findById(Long id);
	
	void deleteById(Long id);

	List<Complains> findAll();
	
	Page<Complains> findAll(Pageable pageable);
	
}
