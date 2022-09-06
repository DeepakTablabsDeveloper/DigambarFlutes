package com.ecom.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecom.core.dto.Discount;


@Repository
public interface DiscountRepository extends PagingAndSortingRepository<Discount, Long> {

	Page<Discount> findById(Long id, Pageable pageable);

	Discount findById(Long id);
	
	void deleteById(Long id);

	List<Discount> findAll();
	
	Page<Discount> findAll(Pageable pageable);
	
}
